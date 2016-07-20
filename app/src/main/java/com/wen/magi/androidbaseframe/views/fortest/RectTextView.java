package com.wen.magi.androidbaseframe.views.fortest;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.wen.magi.androidbaseframe.R;
import com.wen.magi.androidbaseframe.utils.SysUtils;

/**
 * Created by MVEN on 16/7/13.
 * <p/>
 * email: magiwen@126.com.
 * <p/>
 * <p/>
 * ********
 * *  b   *   topText
 * *  m   *
 * *  p   *   bottomText
 * ********
 */


public class RectTextView extends View {

    private Drawable drawable;
    private float drawableHeight;
    private float drawableWidth;
    private float drawableMarginLeft;
    private float drawableMarginTop;
    private float scale;

    private float textMargin;
    private String rToptext;
    private float rTopTextSize;
    private int rTopTextColor;

    private String rBottomtext;
    private float rBottomTextSize;
    private float rBottomTextMarginTop;
    private int rBottomTextColor;

    /**
     * attrs of Text
     */
    private TextPaint topPaint, bottomPaint;
    private int bottomTextHeight, topTextHeight, textWidth;

    /**
     * attrs of View
     */
    int height, width;
    int heightMode, widthMode;
    Matrix matrix;

    private StaticLayout topStaticLayout, bottomStaticLayout;

    public RectTextView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public RectTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public RectTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RectTextView, defStyleAttr, 0);
        drawable = typedArray.getDrawable(R.styleable.RectTextView_bitmap_src);
        drawableWidth = typedArray.getDimension(R.styleable.RectTextView_bitmap_width, context.getResources().getDimension(R.dimen.bitmap_default_edge));
        drawableHeight = typedArray.getDimension(R.styleable.RectTextView_bitmap_height, context.getResources().getDimension(R.dimen.bitmap_default_edge));
        drawableMarginLeft = typedArray.getDimension(R.styleable.RectTextView_bitmap_marginLeft, context.getResources().getDimension(R.dimen.bitmap_default_margin));
        drawableMarginTop = typedArray.getDimension(R.styleable.RectTextView_bitmap_margintTop, context.getResources().getDimension(R.dimen.bitmap_default_margin));

        textMargin = typedArray.getDimension(R.styleable.RectTextView_text_margin, context.getResources().getDimension(R.dimen.bitmap_default_margin));

        rToptext = typedArray.getString(R.styleable.RectTextView_right_top_text);
        rTopTextColor = typedArray.getColor(R.styleable.RectTextView_right_top_text_color, context.getResources().getColor(R.color.blue_btn_bg_color));
        rTopTextSize = typedArray.getDimension(R.styleable.RectTextView_right_top_text_size, context.getResources().getDimension(R.dimen.default_textSize));

        rBottomtext = typedArray.getString(R.styleable.RectTextView_right_bottom_text);
        rBottomTextColor = typedArray.getColor(R.styleable.RectTextView_right_bottom_text_color, context.getResources().getColor(R.color.blue_btn_bg_color));
        rBottomTextSize = typedArray.getDimension(R.styleable.RectTextView_right_bottom_text_size, context.getResources().getDimension(R.dimen.default_textSize));
        rBottomTextMarginTop = typedArray.getDimension(R.styleable.RectTextView_right_bottom_text_marginTop, context.getResources().getDimension(R.dimen.bitmap_default_margin));
        typedArray.recycle();

        initTools();

    }

    private void initTools() {
        initPaint();
        initStaticLayout();
        matrix = new Matrix();
    }

    private void initStaticLayout() {
        textWidth = SysUtils.WIDTH - (int) (drawableMarginLeft + drawableWidth + textMargin * 2);

        topStaticLayout = new StaticLayout(rToptext, topPaint, textWidth, Layout.Alignment.ALIGN_NORMAL, 1, 0, false);
        topTextHeight = topStaticLayout.getHeight();

        bottomStaticLayout = new StaticLayout(rBottomtext, bottomPaint, textWidth, Layout.Alignment.ALIGN_NORMAL, 1, 0, false);
        bottomTextHeight = bottomStaticLayout.getHeight();
    }

    private void initPaint() {

        topPaint = new TextPaint();
        topPaint.setColor(rTopTextColor);
        topPaint.setAntiAlias(true);
        topPaint.setStyle(Paint.Style.FILL);
        topPaint.setTextSize(rTopTextSize);

        bottomPaint = new TextPaint();
        bottomPaint.setColor(rBottomTextColor);
        bottomPaint.setAntiAlias(true);
        bottomPaint.setStyle(Paint.Style.FILL);
        bottomPaint.setTextSize(rBottomTextSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        BitmapDrawable bm = (BitmapDrawable) drawable;
        canvas.drawBitmap(bm.getBitmap(), matrix, null);
        canvas.translate(drawableMarginLeft + drawableHeight + textMargin, drawableMarginTop);
        topStaticLayout.draw(canvas);
        canvas.translate(0, topTextHeight + textMargin);
        bottomStaticLayout.draw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
        widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
        height = View.MeasureSpec.getSize(heightMeasureSpec);
        width = View.MeasureSpec.getSize(widthMeasureSpec);

        if (heightMode == MeasureSpec.AT_MOST) {
            float bitmapHeight = drawableMarginTop * 2 + drawableHeight;
            float textHeight = bottomTextHeight + topTextHeight + rBottomTextMarginTop;
            boolean baseBitmap = textHeight > bitmapHeight ? false : true;

            //Bitmap.getHeight() != BitmapDrawable.getIntrinsicHeight()
            scale = drawableHeight / drawable.getIntrinsicHeight();
            matrix.setScale(scale, scale);

            if (baseBitmap) {
                height = (int) Math.ceil(bitmapHeight);
                matrix.postTranslate(drawableMarginLeft, drawableMarginTop);
            } else {
                height = (int) Math.ceil(textHeight + drawableMarginTop * 2);
                matrix.postTranslate(drawableMarginLeft, (height - drawableHeight) / 2);
            }
        }
        /**
         * 宽度默认为match_parent
         */
        if (widthMeasureSpec == MeasureSpec.AT_MOST) {
        }

        setMeasuredDimension(width, height);
    }


}
