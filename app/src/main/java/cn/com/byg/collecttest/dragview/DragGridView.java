package cn.com.byg.collecttest.dragview;

import java.util.HashMap;

import cn.com.byg.collecttest.R;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;

/**
 * 拖拽GridView
 * @author Chenqi
 * @date 2014-02-28
 *
 */
@SuppressLint("NewApi") public class DragGridView extends GridView {

    private final int SMOOTH_SCROLL_AMOUNT_AT_EDGE = 15;
    private final int MOVE_DURATION = 150;
    private final int LINE_THICKNESS = 15;
    /**
     * 长按后阴影的偏移
     */
    private final int EXCURSION = getResources().getDimensionPixelSize(R.dimen.margin6);

    private int mLastEventX = -1;
    private int mLastEventY = -1;

    private int mDownY = -1;
    private int mDownX = -1;

    private int mTotalOffsetX = 0;
    private int mTotalOffsetY = 0;

    private boolean mCellIsMobile = false;
    private boolean mIsMobileScrolling = false;
    private int mSmoothScrollAmountAtEdge = 0;

    private final int INVALID_ID = -1;
    private long mMobileItemId = INVALID_ID;
    private int mNotMovePosition = -1;
    

    private BitmapDrawable mHoverCell;
    private Rect mHoverCellCurrentBounds;
    private Rect mHoverCellOriginalBounds;

    private final int INVALID_POINTER_ID = -1;
    private int mActivePointerId = INVALID_POINTER_ID;

    private boolean mIsWaitingForScrollFinish = false;
    private int mScrollState = OnScrollListener.SCROLL_STATE_IDLE;
    
    private OnDragListener onDragListener;
    private int gridNumColumns;
    private boolean isAboveAPI11;
    private static TypeEvaluator<Rect> sBoundEvaluator = null;

    public DragGridView(Context context) {
        super(context);
        init(context);
    }

    public DragGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public DragGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);  
        
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        if (!(adapter instanceof DraggableAdapter)) {
            throw new IllegalArgumentException("Adapter have to implement DraggableAdapter");
        }
        super.setAdapter(adapter);
    }

    public void init(Context context) {
    	//判断手机设备系统版本是否在API11以上（包含11）
        isAboveAPI11 = true;
        
        setOnItemLongClickListener(mOnItemLongClickListener);
        setOnScrollListener(mScrollListener);
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        mSmoothScrollAmountAtEdge = (int)(SMOOTH_SCROLL_AMOUNT_AT_EDGE / metrics.density);
        
        
        /**
         * This TypeEvaluator is used to animate the BitmapDrawable back to its
         * final location when the user lifts his finger by modifying the
         * BitmapDrawable's bounds.
         */
        if (isAboveAPI11) {
        	sBoundEvaluator = new TypeEvaluator<Rect>() {
                @Override
        		public Rect evaluate(float fraction, Rect startValue, Rect endValue) {
                    return new Rect(interpolate(startValue.left, endValue.left, fraction),
                            interpolate(startValue.top, endValue.top, fraction),
                            interpolate(startValue.right, endValue.right, fraction),
                            interpolate(startValue.bottom, endValue.bottom, fraction));
                }

                public int interpolate(int start, int end, float fraction) {
                    return (int)(start + fraction * (end - start));
                }
            };
		}
        
    }

    /**
     * Listens for long clicks on any items in the listview. When a cell has
     * been selected, the hover cell is created and set up.
     */
    private OnItemLongClickListener mOnItemLongClickListener =
            new OnItemLongClickListener() {
                @Override
				public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {
                    mTotalOffsetY = 0;

                    int position = pointToPosition(mDownX, mDownY);
                    int itemNum = position - getFirstVisiblePosition();

                    if (itemNum != mNotMovePosition) {
                    	 View selectedView = getChildAt(itemNum);
                         mMobileItemId = getAdapter().getItemId(position);
                         mHoverCell = getAndAddHoverView(selectedView);
                         selectedView.setVisibility(INVISIBLE);
//                         selectedView.setAlpha(0);
                         mCellIsMobile = true;
                         if (onDragListener != null) {
                        	 onDragListener.onDragStart();
						}                        
					}
                   

                    return true;
                }
            };

    /**
     * Creates the hover cell with the appropriate bitmap and of appropriate
     * size. The hover cell's BitmapDrawable is drawn on top of the bitmap every
     * single time an invalidate call is made.
     */
    private BitmapDrawable getAndAddHoverView(View v) {

        int w = v.getWidth();
        int h = v.getHeight();
        int top = v.getTop()+EXCURSION;
        int left = v.getLeft()+EXCURSION;

        Bitmap b = getBitmapWithBorder(v);

        BitmapDrawable drawable = new BitmapDrawable(getResources(), b);

        mHoverCellOriginalBounds = new Rect(left, top, left + w, top + h);
        mHoverCellCurrentBounds = new Rect(mHoverCellOriginalBounds);

        drawable.setBounds(mHoverCellCurrentBounds);

        return drawable;
    }

    /** Draws a black border over the screenshot of the view passed in. */
    private Bitmap getBitmapWithBorder(View v) {
        Bitmap bitmap = getBitmapFromView(v);
        Canvas can = new Canvas(bitmap);

        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(LINE_THICKNESS);
        paint.setColor(Color.TRANSPARENT);
        
        can.drawBitmap(bitmap, 0, 0, null);
        can.drawRect(rect, paint);

        return bitmap;
    }

    /** Returns a bitmap showing a screenshot of the view passed in. */
    private Bitmap getBitmapFromView(View v) {
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas (bitmap);
        v.draw(canvas);
        return bitmap;
    }

    public View getViewForPosition (int position) {
        if (position < 0) return null;
        if (position >= getCount()) return null;
        final long itemId = getAdapter().getItemId(position);

        return getViewForID(itemId);
    };

    /** Retrieves the view in the list corresponding to itemID */
    public View getViewForID (long itemID) {
        int firstVisiblePosition = getFirstVisiblePosition();
        for(int i = 0; i < getChildCount(); i++) {
            View v = getChildAt(i);
            int position = firstVisiblePosition + i;
            long id = getAdapter().getItemId(position);
            if (id == itemID) {
                return v;
            }
        }
        return null;
    }

    /** Retrieves the position in the list corresponding to itemID */
    public int getPositionForID (long itemID) {
        View v = getViewForID(itemID);
        if (v == null) {
            return -1;
        } else {
            return getPositionForView(v);
        }
    }

    /**
     *  dispatchDraw gets invoked when all the child views are about to be drawn.
     *  By overriding this method, the hover cell (BitmapDrawable) can be drawn
     *  over the gridview's items whenever the gridview is redrawn.
     */
    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (mHoverCell != null) {
            mHoverCell.draw(canvas);
        }
    }

    @Override
    public boolean onTouchEvent (MotionEvent event) {

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                mDownX = (int)event.getX();
                mDownY = (int)event.getY();
                mActivePointerId = event.getPointerId(0);
                break;
            case MotionEvent.ACTION_MOVE:           	
                if (mActivePointerId == INVALID_POINTER_ID ) {
                    break;
                }

                int pointerIndex = event.findPointerIndex(mActivePointerId);
                mLastEventX = (int) event.getX(pointerIndex);
                mLastEventY = (int) event.getY(pointerIndex);
                int deltaX = mLastEventX - mDownX;
                int deltaY = mLastEventY - mDownY;

                if (mCellIsMobile) {
                	
                    mHoverCellCurrentBounds.offsetTo(mHoverCellOriginalBounds.left + deltaX + mTotalOffsetX,
                            mHoverCellOriginalBounds.top + deltaY + mTotalOffsetY);
                    mHoverCell.setBounds(mHoverCellCurrentBounds);
                    invalidate();
                    
                    handleCellSwitch();

                    mIsMobileScrolling = false;
                    handleMobileCellScroll();

                    return false;
                }
                break;
            case MotionEvent.ACTION_UP:
                touchEventsEnded();
                break;
            case MotionEvent.ACTION_CANCEL:
                touchEventsCancelled();
                break;
            case MotionEvent.ACTION_POINTER_UP:
                /* If a multitouch event took place and the original touch dictating
                 * the movement of the hover cell has ended, then the dragging event
                 * ends and the hover cell is animated to its corresponding position
                 * in the gridview. */
                pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >>
                        MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                final int pointerId = event.getPointerId(pointerIndex);
                if (pointerId == mActivePointerId) {
                    touchEventsEnded();
                }
                break;
            default:
                break;
        }

        return super.onTouchEvent(event);
    }

    private HashMap<Long, Integer> mItemIdTops = new HashMap<Long, Integer>();
    private HashMap<Long, Integer> mItemIdLefts = new HashMap<Long, Integer>();

    /**
     * This method determines whether the hover cell has been shifted far enough
     * to invoke a cell swap. If so, then the respective cell swap candidate is
     * determined and the data set is changed. Upon posting a notification of the
     * data set change, a layout is invoked to place the cells in the right place.
     * Using a ViewTreeObserver and a corresponding OnPreDrawListener, we can
     * offset the cell being swapped to where it previously was and then animate it to
     * its new position.
     */
    private void handleCellSwitch() {
        final int deltaY = mLastEventY - mDownY;
        final int deltaX = mLastEventX - mDownX;

        int deltaYTotal = mHoverCellOriginalBounds.top + mTotalOffsetY + deltaY;
        int deltaXTotal = mHoverCellOriginalBounds.left + mTotalOffsetX + deltaX;
        
        final int numColumns = gridNumColumns;
        
        final int position = getPositionForID(mMobileItemId);

        final int abovePosition = position - numColumns;
        final int belowPosition = position + numColumns;
        final int toLeftPosition = position - 1;
        final int toRightPosition = position + 1;

        View aboveView = getViewForPosition(abovePosition);
        View belowView = getViewForPosition(belowPosition);
        View toLeftView = getViewForPosition(toLeftPosition);
        View toRightView = getViewForPosition(toRightPosition);
        View mobileView = getViewForID(mMobileItemId);

        if ((toRightView != null) && (mobileView.getLeft() > toRightView.getLeft())) {
            // mobile view is far right
            toRightView = null;
        }
        if ((toLeftView != null) && (mobileView.getLeft() < toLeftView.getLeft())) {
            // mobile view is far left
            toLeftView = null;
        }

        boolean isBelow = (belowView != null) && (deltaYTotal > belowView.getTop());
        boolean isAbove = (aboveView != null) && (deltaYTotal < aboveView.getTop());
        boolean isToRight = (toRightView != null) && (deltaXTotal > toRightView.getLeft());
        boolean isToLeft = (toLeftView != null) && (deltaXTotal < toLeftView.getLeft());
        int newPosition;
        if (isBelow) {
            newPosition = belowPosition;
        } else if (isAbove) {
            newPosition = abovePosition;
        } else if (isToLeft) {
            newPosition = toLeftPosition;
        } else if (isToRight) {
            newPosition = toRightPosition;
        } else {
            newPosition = position;
        }

        if (newPosition == position || newPosition == mNotMovePosition) {
            return;
        }

        final ListAdapter adapter = getAdapter();
        final int fromPosition = Math.min(newPosition, position);
        final int toPosition = Math.max(newPosition, position);

        for (int cellPosition = fromPosition; cellPosition <= toPosition; cellPosition ++) {
            getViewForPosition(cellPosition).setVisibility(View.VISIBLE);
//        	getViewForPosition(cellPosition).setAlpha(1);
            
        }
        mItemIdLefts.clear();
        mItemIdTops.clear();

        final int firstVisiblePosition = getFirstVisiblePosition();
        final int childCount = getChildCount();
        for (int childAt = 0; childAt < childCount; childAt++) {
            final View child = getChildAt(childAt);
            assert child != null;
            int pos = firstVisiblePosition + childAt;
            final long itemId = adapter.getItemId(pos);
            mItemIdLefts.put(itemId, child.getLeft());
            mItemIdTops.put(itemId, child.getTop());
        }

        final ViewTreeObserver observer = getViewTreeObserver();
        assert observer != null;
        observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
			public boolean onPreDraw() {
                observer.removeOnPreDrawListener(this);
                final int firstVisiblePosition = getFirstVisiblePosition();
                final int childCount = getChildCount();
                for (int childAt = 0; childAt < childCount; childAt++) {
                    final View child = getChildAt(childAt);
                    assert child != null;
                    int pos = firstVisiblePosition + childAt;
                    final long itemId = adapter.getItemId(pos);
                    if (itemId == mMobileItemId) {
                        child.setVisibility(View.GONE);
//                    	child.setAlpha(0);
                        continue;
                    }
                    final Integer oldLeft = mItemIdLefts.get(itemId);
                    final Integer oldTop = mItemIdTops.get(itemId);
                    if (oldLeft == null) {
                        continue;
                    }
                    mItemIdLefts.put(itemId, child.getLeft());
                    mItemIdTops.put(itemId, child.getTop());

                    final int newLeft = child.getLeft();
                    final int newTop = child.getTop();

                    int deltaX = oldLeft - newLeft;
                    int deltaY = oldTop - newTop;

                    if (deltaX == 0 && deltaY == 0) {
                        continue;
                    }
                    //手机系统版本在API11之上才调用以下方法
                    if (isAboveAPI11) {
                    	child.setTranslationX(deltaX);
                        child.setTranslationY(deltaY);

                        final AnimatorSet animator = new AnimatorSet();
                        animator.playTogether(
                                ObjectAnimator.ofFloat(child, View.TRANSLATION_X, 0),
                                ObjectAnimator.ofFloat(child, View.TRANSLATION_Y, 0)
                        );
                        animator.setDuration(MOVE_DURATION).start();
					}
                    
                }
                return true;
            }
        });

        ((DraggableAdapter) adapter).reorderElements(position, newPosition);
    }

    /**
     * Resets all the appropriate fields to a default state while also animating
     * the hover cell back to its correct location.
     */
    private void touchEventsEnded () {
        final View mobileView = getViewForID(mMobileItemId);
        if (mCellIsMobile|| mIsWaitingForScrollFinish) {
            mCellIsMobile = false;
            mIsWaitingForScrollFinish = false;
            mIsMobileScrolling = false;
            mActivePointerId = INVALID_POINTER_ID;

            // If the autoscroller has not completed scrolling, we need to wait for it to
            // finish in order to determine the final location of where the hover cell
            // should be animated to.
            if (mScrollState != OnScrollListener.SCROLL_STATE_IDLE) {
                mIsWaitingForScrollFinish = true;
                return;
            }

            mHoverCellCurrentBounds.offsetTo(mobileView.getLeft(), mobileView.getTop());
            //手机系统在API11以上才调用这个动画类
            if (isAboveAPI11) {
            	ObjectAnimator hoverViewAnimator = ObjectAnimator.ofObject(mHoverCell, "bounds",
                        sBoundEvaluator, mHoverCellCurrentBounds);
                hoverViewAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        invalidate();
                    }
                });
                hoverViewAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        setEnabled(false);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mMobileItemId = INVALID_ID;
                        mobileView.setVisibility(VISIBLE);
                        getChildAt(0).setVisibility(VISIBLE);
//                        getChildAt(0).setAlpha(1);
                       
//                        mobileView.setAlpha(1);
//                        for (int i = 0; i < getCount(); i++) {
//    						getChildAt(i).setAlpha(1);
//    					}
                        mHoverCell = null;
                        setEnabled(true);
                        invalidate();
    					if (onDragListener != null) {
    						onDragListener.onDragComplete();
    					}
    				}
                });
                hoverViewAnimator.start();
			}else {
				mMobileItemId = INVALID_ID;
                mobileView.setVisibility(VISIBLE);
                getChildAt(0).setVisibility(VISIBLE);
				mHoverCell = null;
                setEnabled(true);
                invalidate();
				if (onDragListener != null) {
					onDragListener.onDragComplete();
				}
			}
            
        } else {
            touchEventsCancelled();
        }
    }

    /**
     * Resets all the appropriate fields to a default state.
     */
    private void touchEventsCancelled () {
        View mobileView = getViewForID(mMobileItemId);
        if (mCellIsMobile) {
            mMobileItemId = INVALID_ID;
            mobileView.setVisibility(VISIBLE);
//            mobileView.setAlpha(1);
            mHoverCell = null;
            invalidate();
        }
        mCellIsMobile = false;
        mIsMobileScrolling = false;
        mActivePointerId = INVALID_POINTER_ID;
    }

    

    /**
     *  Determines whether this gridview is in a scrolling state invoked
     *  by the fact that the hover cell is out of the bounds of the gridview;
     */
    private void handleMobileCellScroll() {
        mIsMobileScrolling = handleMobileCellScroll(mHoverCellCurrentBounds);
    }

    /**
     * This method is in charge of determining if the hover cell is above
     * or below the bounds of the gridview. If so, the gridview does an appropriate
     * upward or downward smooth scroll so as to reveal new items.
     */
    public boolean handleMobileCellScroll(Rect r) {
        int offset = computeVerticalScrollOffset();
        int height = getHeight();
        int extent = computeVerticalScrollExtent();
        int range = computeVerticalScrollRange();
        int hoverViewTop = r.top;
        int hoverHeight = r.height();

        if (hoverViewTop <= 0 && offset > 0) {
            smoothScrollBy(-mSmoothScrollAmountAtEdge, 0);
            return true;
        }

        if (hoverViewTop + hoverHeight >= height && (offset + extent) < range) {
            smoothScrollBy(mSmoothScrollAmountAtEdge, 0);
            return true;
        }

        return false;
    }

    /**
     * This scroll listener is added to the gridview in order to handle cell swapping
     * when the cell is either at the top or bottom edge of the gridview. If the hover
     * cell is at either edge of the gridview, the gridview will begin scrolling. As
     * scrolling takes place, the gridview continuously checks if new cells became visible
     * and determines whether they are potential candidates for a cell swap.
     */
    private OnScrollListener mScrollListener = new OnScrollListener () {

        private int mPreviousFirstVisibleItem = -1;
        private int mPreviousVisibleItemCount = -1;
        private int mCurrentFirstVisibleItem;
        private int mCurrentVisibleItemCount;
        private int mCurrentScrollState;

        @Override
		public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                             int totalItemCount) {
            mCurrentFirstVisibleItem = firstVisibleItem;
            mCurrentVisibleItemCount = visibleItemCount;

            mPreviousFirstVisibleItem = (mPreviousFirstVisibleItem == -1) ? mCurrentFirstVisibleItem
                    : mPreviousFirstVisibleItem;
            mPreviousVisibleItemCount = (mPreviousVisibleItemCount == -1) ? mCurrentVisibleItemCount
                    : mPreviousVisibleItemCount;

            checkAndHandleFirstVisibleCellChange();
            checkAndHandleLastVisibleCellChange();

            mPreviousFirstVisibleItem = mCurrentFirstVisibleItem;
            mPreviousVisibleItemCount = mCurrentVisibleItemCount;
        }

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            mCurrentScrollState = scrollState;
            mScrollState = scrollState;
            isScrollCompleted();
        }

        /**
         * This method is in charge of invoking 1 of 2 actions. Firstly, if the gridview
         * is in a state of scrolling invoked by the hover cell being outside the bounds
         * of the gridview, then this scrolling event is continued. Secondly, if the hover
         * cell has already been released, this invokes the animation for the hover cell
         * to return to its correct position after the gridview has entered an idle scroll
         * state.
         */
        private void isScrollCompleted() {
            if (mCurrentVisibleItemCount > 0 && mCurrentScrollState == SCROLL_STATE_IDLE) {
                if (mCellIsMobile && mIsMobileScrolling) {
                    handleMobileCellScroll();
                } else if (mIsWaitingForScrollFinish) {
                    touchEventsEnded();
                }
            }
        }

        /**
         * Determines if the gridview scrolled up enough to reveal a new cell at the
         * top of the list. If so, then the appropriate parameters are updated.
         */
        public void checkAndHandleFirstVisibleCellChange() {
            if (mCurrentFirstVisibleItem != mPreviousFirstVisibleItem) {
                if (mCellIsMobile && mMobileItemId != INVALID_ID) {
                    handleCellSwitch();
                }
            }
        }

        /**
         * Determines if the gridview scrolled down enough to reveal a new cell at the
         * bottom of the list. If so, then the appropriate parameters are updated.
         */
        public void checkAndHandleLastVisibleCellChange() {
            int currentLastVisibleItem = mCurrentFirstVisibleItem + mCurrentVisibleItemCount;
            int previousLastVisibleItem = mPreviousFirstVisibleItem + mPreviousVisibleItemCount;
            if (currentLastVisibleItem != previousLastVisibleItem) {
                if (mCellIsMobile && mMobileItemId != INVALID_ID) {
                    handleCellSwitch();
                }
            }
        }
    };
    
    /**
     * 拖拽监听接口
     *
     */
    public interface OnDragListener{
    	public void onDragStart();
    	public void onDragComplete();
    }
    
    public void setOnDragListener(OnDragListener onDragListener) {
		this.onDragListener = onDragListener;
	}
    
    /**
     * 设置第一项不可移动
     */
    public void setFirstItemNotMove() {
    	mNotMovePosition = 0;
	}
    
    /**
     * 设置最后一项不可移动
     */
    public void setLastItemNotMove() {
    	mNotMovePosition = getCount()-1;
	}

	public void setGridNumColumns(int gridNumColumns) {
		this.gridNumColumns = gridNumColumns;
	}
    
}
