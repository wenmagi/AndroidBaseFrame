package com.wen.magi.baseframe.views.fortest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by MVEN on 16/8/1.
 * <p/>
 * email: magiwen@126.com.
 */


public class CircleAnimateView extends View {

    float radius = 10;
    Paint paint;

    public CircleAnimateView(Context context) {
        super(context);
        init(context);
    }

    public CircleAnimateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CircleAnimateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        paint = new Paint();
        paint.setColor(Color.YELLOW);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(200, 200);
        canvas.drawCircle(0, 0, radius++, paint);

        if (radius > 100) {
            radius = 10;
        }

        invalidate();//通过调用这个方法让系统自动刷新视图
    }
}
