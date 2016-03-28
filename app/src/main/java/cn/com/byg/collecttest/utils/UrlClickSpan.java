package cn.com.byg.collecttest.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import cn.com.byg.collecttest.web.WebViewTestActivity;

/**
 * Created by ypy on 2015/9/6.
 */
public class UrlClickSpan extends ClickableSpan{



    private String url ;
    private Context context;
    public UrlClickSpan (Context context,String url){
        this.url = url;
        this.context = context;
    }


    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(Color.parseColor("#AAFFFF00"));
        ds.setUnderlineText(false);
    }

    @Override
    public void onClick(View widget) {

        Intent intent = new Intent(context, WebViewTestActivity.class);
        context.startActivity(intent);
    }
}
