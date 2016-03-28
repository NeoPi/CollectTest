package cn.com.byg.collecttest.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;

import cn.com.byg.collecttest.R;

/**
 * @author NeoPi.
 * @date 2015/8/25
 */
public class LuckyDogView extends View implements ViewTreeObserver.OnGlobalLayoutListener {

    public Paint paint = null;       // 图形画笔工具
    private Paint painttext = null;  // 文字画笔工具

    private int width; // 转盘宽度
    private int padding; // 转盘内边距
    private int radius;// 半径
    private RectF rect; // 圆形矩形区域

    private int count = 8;           // 扇形个数
    private float shanxingangle ;    // 每个扇形的角度

    private Bitmap backbitmap= BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);

    public LuckyDogView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        getViewTreeObserver().addOnGlobalLayoutListener(this);
        padding = getPaddingLeft();
        // 图形画笔
        paint = new Paint();
        paint.setAntiAlias(true); // 抗锯齿
        paint.setStyle(Paint.Style.FILL);
        paint.setARGB(255, 14 * 16 + 9, 14 * 16 + 9, 14 * 16 + 9);
        paint.setStrokeWidth(4);

        // 文字画笔
        painttext = new Paint();
        painttext.setAntiAlias(true); // 抗锯齿
        painttext.setTextSize(30);
        painttext.setStyle(Paint.Style.FILL);
        painttext.setARGB(255, 15 * 16 + 4, 6 * 16 + 12, 10);
        shanxingangle = 360.0f / count;
    }


    public LuckyDogView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LuckyDogView(Context context) {
        this(context,null);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = Math.min(getMeasuredHeight(), getMeasuredWidth());
        radius = (width - padding * 2) / 2;
        rect = new RectF(padding, padding, width - padding, width - padding);
        setMeasuredDimension(width, width);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        //绘制背景
        paint.setARGB(80, 13 * 16 + 12, 13 * 16 + 12, 13 * 16 + 12);

        //展示在屏幕上的区域
        canvas.drawBitmap(backbitmap, null, new Rect(padding / 2,
                padding / 2, getMeasuredWidth() - padding / 2,
                getMeasuredWidth() - padding / 2), null);
        canvas.drawCircle(radius + padding, radius + padding, radius, paint);
        // 绘制扇形
        paint.setARGB(255, 13 * 16 + 12, 13 * 16 + 12, 13 * 16 + 12);
        paint.setStyle(Paint.Style.STROKE);
        float vOffset = 0;
        float hOffset = 0;
        float textwidth = 0;
        for (int i = 0; i < count; i++) {
            float startangle = i * shanxingangle;
            canvas.drawArc(rect, startangle, shanxingangle, true, paint);
            // 绘制文本
            Path path = new Path();
            path.addArc(rect, startangle, shanxingangle);
            // 计算文字
            painttext.setTextSize(35);
            painttext.setStyle(Paint.Style.FILL);
            painttext.setARGB(255, 15 * 16 + 4, 6 * 16 + 12, 10);
//            textwidth = painttext.measureText(LuckyPrecent.jianzhi[i]);
            vOffset = radius / 4;
            hOffset = (float) (radius * Math.PI / count - textwidth / 2);
//            canvas.drawTextOnPath(LuckyPrecent.jianzhi[i], path, hOffset, vOffset, painttext);
            painttext.setTextSize(30);
            painttext.setStyle(Paint.Style.FILL);
            painttext.setARGB(255, 9 * 16 + 12, 9 * 16 + 12, 9 * 16 + 12);
//            textwidth = painttext.measureText(LuckyPrecent.jianzhimc[i]);
            hOffset = (float) (radius * Math.PI / count - textwidth / 2);
//            canvas.drawTextOnPath(LuckyPrecent.jianzhimc[i], path, hOffset, vOffset + 30, painttext);
        }
    }

    /**
     * Callback method to be invoked when the global layout state or the visibility of views
     * within the view tree changes
     */
    @Override
    public void onGlobalLayout() {

    }
}
