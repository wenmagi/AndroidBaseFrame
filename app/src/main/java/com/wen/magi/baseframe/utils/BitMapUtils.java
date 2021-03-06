package com.wen.magi.baseframe.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by MVEN on 16/6/16.
 * <p/>
 * email: magiwen@126.com.
 */


public class BitMapUtils {
    /**
     * 缩放图片
     *
     * @param bm
     * @param newWidth
     * @param newHeight
     * @return Bitmap
     */
    public static Bitmap scaleImg(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        int newWidth1 = newWidth;
        int newHeight1 = newHeight;
        float scaleWidth = ((float) newWidth1) / width;
        float scaleHeight = ((float) newHeight1) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }

    /**
     * drawable 转为 Bitmap
     *
     * @param drawable
     * @return Bitmap
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * bitmap 转 Drawable
     *
     * @param bitmap
     * @return Drawable
     */
    public static Drawable bitmap2Drawable(Bitmap bitmap) {
        Drawable drawable = new BitmapDrawable(bitmap);
        return drawable;
    }


    /**
     * 字节转图片
     *
     * @param data
     * @return Bitmap
     */
    public static Bitmap getBitmap(byte[] data) {
        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }

    /**
     * 图片转字节
     *
     * @param bitmap
     * @return byte[]
     */
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream baops = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, baops);
        return baops.toByteArray();
    }

    /**
     * 获取圆形bitmap
     *
     * @param bitmap
     * @param radius
     * @return
     */
    public static Bitmap getCircleBitmap(Bitmap bitmap, float radius) {
        Paint paint = new Paint();
        final int width = bitmap.getWidth();
        final int height = bitmap.getHeight();
        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff000000;
        final Rect rect = new Rect(0, 0, width, height);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(rect.centerX(), rect.centerY(), radius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    /**
     * 创建一个缩放到指定大小的Bitmap 并保存到文件中
     *
     * @param originFile
     * @param maxWidth
     * @param maxHeight
     * @return
     */
    public static File createAndSaveScaledBitmap(File originFile, int maxWidth,
                                                 int maxHeight) {
        if (originFile == null || !originFile.exists() || maxWidth <= 0 || maxHeight <= 0)
            return originFile;

        Bitmap ret = null;
        double width = 0, height = 0;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(originFile.getAbsolutePath(), options);
            width = options.outWidth;
            height = options.outHeight;

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (width != 0 && height != 0) {
            if (width <= maxWidth || height <= maxHeight)
                return originFile;

            int sample = 1;

            if (width > maxWidth) {
                sample = (int) Math.ceil(width / maxWidth);
                height = height / sample;
            }

            if (height > maxHeight)
                sample += (int) Math.ceil(height / maxHeight);

            BitmapFactory.Options options = new BitmapFactory.Options();
            try {
                options.inSampleSize = sample;
                ret = BitmapFactory.decodeFile(originFile.getAbsolutePath(), options);
            } catch (OutOfMemoryError e) {
                options.inSampleSize = sample * 2;
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                try {
                    ret = BitmapFactory.decodeFile(originFile.getAbsolutePath(), options);
                } catch (OutOfMemoryError e1) {
                }
            }
        } else {
            return originFile;
        }

        if (ret.getWidth() > maxWidth || ret.getHeight() > maxHeight) {
            ret = createScaledBitmap(ret, maxWidth, maxHeight);
        }

        if (ret == null)
            return originFile;

        File newFile = new File(originFile.getAbsolutePath() + Constants.BITMAP_SCALED_COPY_PATH);
        IOUtils.saveBitmap(ret, newFile, Bitmap.CompressFormat.JPEG, 85);

        if (newFile.exists()) {
            return newFile;
        }
        return originFile;
    }

    /**
     * 创建一个缩放到指定大小的Bitmap
     *
     * @param bm
     * @param maxWidth
     * @param maxHeight
     * @return Bitmap
     */
    public static Bitmap createScaledBitmap(@NonNull Bitmap bm, int maxWidth, int maxHeight) {
        if (bm.getWidth() <= maxWidth && bm.getHeight() <= maxHeight)
            return bm;

        int width = bm.getWidth();
        int height = bm.getHeight();

        if (bm.getWidth() > maxWidth) {
            width = maxWidth;
            height = (int) (bm.getHeight() * (maxWidth + 0.0) / bm.getWidth());
        }

        if (height > maxHeight) {
            width = (int) (width * (maxHeight + 0.0) / height);
            height = maxHeight;
        }

        return Bitmap.createScaledBitmap(bm, width, height, true);
    }
}
