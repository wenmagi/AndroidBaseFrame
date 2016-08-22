package com.wen.magi.baseframe.views.images;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by MVEN on 16/8/11.
 * <p/>
 * email: magiwen@126.com.
 */


public class GestureImageView extends ImageView {

    //两点触屏后之间的长度
    private float beforeLength;
    private float afterLength;

    //单点移动的前后坐标值
    private float afterX, afterY;
    private float beforeX, beforeY;

    public GestureImageView(Context context) {
        super(context);
    }

    public GestureImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GestureImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void setLocation(int x, int y) {
        setFrame(getLeft() + x, getTop() + y, getRight() + x, getBottom() + y);
    }

    /**
     * flag==0:放大，否则缩小
     *
     * @param scale
     * @param flag
     */
    private void setScale(float scale, int flag) {
        int lRdis = (int) scale * getWidth();
        int tBdis = (int) scale * getHeight();
        if (flag == 0) {
            setFrame(getLeft() - lRdis, getTop() - tBdis, getRight() + lRdis, getBottom() + tBdis);
        } else {
            setFrame(getLeft() + lRdis, getTop() + tBdis, getRight() - lRdis, getBottom() - tBdis);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Rect rec = canvas.getClipBounds();
        rec.left++;
        rec.top++;
        rec.bottom--;
        rec.right--;
        Paint paint=new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(rec, paint);
    }
}
