package com.wen.magi.baseframe.views.fortest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


/**
 * Created by MVEN on 16/8/1.
 * <p/>
 * email: magiwen@126.com.
 */


public class TestSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private LoopThread thread;
    private Context con;

    public TestSurfaceView(Context context) {
        super(context);
        con = context;
        init();
    }

    public TestSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        con = context;
        init();
    }

    public TestSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        con = context;
        init();
    }

    private void init() {
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        thread = new LoopThread(con, holder);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.isRunning = true;
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        thread.isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class LoopThread extends Thread {
        private Context context;
        private SurfaceHolder holder;
        private Paint paint;
        boolean isRunning;
        float radius = 10f;

        public LoopThread(Context context, SurfaceHolder holder) {
            this.context = context;
            this.holder = holder;
            paint = new Paint();
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.BLUE);
        }

        @Override
        public void run() {
            Canvas canvas = null;
            while (isRunning) {
                try {
                    synchronized (holder) {
                        canvas = holder.lockCanvas();
                        doDraw(canvas);
                        Thread.sleep(5);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    holder.unlockCanvasAndPost(canvas);
                }

            }
        }

        private void doDraw(Canvas canvas) {
            canvas.drawColor(Color.BLACK);

            canvas.translate(200, 200);
            canvas.drawCircle(0, 0, radius++, paint);

            if (radius > 100) {
                radius = 10f;
            }
        }
    }
}
