package com.wen.magi.baseframe.base;

import android.animation.Animator;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.wen.magi.baseframe.interfaces.IThemeView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author MVEN @ Zhihu Inc.
 * @since 09-30-2016
 */

public class ABActivity extends AppCompatActivity {

    /**
     * 调用此方法切换Theme
     *
     * @param newThemeRes 新的主题
     */
    @SuppressWarnings("unused")
    public void notifyThemeChanged(final int newThemeRes) {
        this.notifyThemeChanged(newThemeRes, true, 300);
    }

    /**
     * 调用此方法切换Theme
     *
     * @param newThemeRes 新的主题
     */
    @SuppressWarnings("deprecation")
    public void notifyThemeChanged(final int newThemeRes, final boolean pWithAnimation, final long pAnimationDuration) {
        final View decorView = this.getWindow().getDecorView();

        if (pWithAnimation) {
            final Bitmap drawingCache = obtainCachedBitmap(decorView);
            if (decorView instanceof ViewGroup && drawingCache != null) {
                final View maskView = new View(this);
                maskView.setBackgroundDrawable(new BitmapDrawable(this.getResources(), drawingCache));
                ((ViewGroup) decorView).addView(maskView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

                this.onPrepareThemeChanged();

                this.changeViewStyle(decorView, newThemeRes);

                maskView.animate().alpha(0).setDuration(pAnimationDuration).setListener(new Animator.AnimatorListener() {

                    @Override
                    public void onAnimationStart(final Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(final Animator animation) {
                        ((ViewGroup) decorView).removeView(maskView);

                        ABActivity.this.onPostThemeChanged();
                    }

                    @Override
                    public void onAnimationCancel(final Animator animation) {
                    }

                    @Override
                    public void onAnimationRepeat(final Animator animation) {
                    }
                }).start();
            } else {
                this.onPrepareThemeChanged();

                this.changeViewStyle(decorView, newThemeRes);

                this.onPostThemeChanged();
            }
        } else {
            this.onPrepareThemeChanged();

            this.changeViewStyle(decorView, newThemeRes);

            this.onPostThemeChanged();
        }
    }

    protected void onPrepareThemeChanged() {

    }

    protected void onPostThemeChanged() {

    }

    /**
     * 遍历子View,切换Theme
     *
     * @param pView       默认为DecorView
     * @param newThemeRes 新的主题
     */
    protected void changeViewStyle(final View pView, final int newThemeRes) {
        if (pView instanceof IThemeView) {
            ((IThemeView) pView).setTheme(this.getTheme());
        }

        if (pView instanceof ViewGroup) {
            final int childCount = ((ViewGroup) pView).getChildCount();

            for (int i = 0; i < childCount; i++) {
                this.changeViewStyle(((ViewGroup) pView).getChildAt(i), newThemeRes);
            }

            try {
                if (pView instanceof RecyclerView) {
                    final Field recycler = RecyclerView.class.getDeclaredField("mRecycler");
                    recycler.setAccessible(true);

                    final Method method = Class.forName("android.support.v7.widget.RecyclerView$Recycler").getDeclaredMethod("clear");
                    method.setAccessible(true);

                    method.invoke(recycler.get(pView));

                    ((RecyclerView) pView).getRecycledViewPool().clear();

                    if (pView.getContext() instanceof ContextThemeWrapper) {
                        pView.getContext().setTheme(newThemeRes);
                    }
                } else if (pView instanceof AbsListView) {
                    final Field recycler = AbsListView.class.getDeclaredField("mRecycler");
                    recycler.setAccessible(true);

                    final Method method = Class.forName("android.widget.AbsListView$RecycleBin").getDeclaredMethod("clear");
                    method.setAccessible(true);

                    method.invoke(recycler.get(pView));
                }
            } catch (Exception pE) {
                pE.printStackTrace();
            }
        }
    }

    /**
     * 避免在OPPO上由于节电管理使Service无法启动导致的Crash
     *
     * @see <a href="http://bbs.coloros.com/thread-174655-1-1.html">http://bbs.coloros.com/thread-174655-1-1.html</a>
     */
    @Override
    public ComponentName startService(@NonNull final Intent pIntent) {
        try {
            return super.startService(pIntent);
        } catch (RuntimeException ignored) {
            // Exception ignored
            return null;
        }
    }

    private Bitmap obtainCachedBitmap(View decorView) {
        decorView.setDrawingCacheEnabled(true);
        decorView.buildDrawingCache(true);
        Bitmap drawingCache = decorView.getDrawingCache();
        if (drawingCache != null) {
            Bitmap bitmap = Bitmap.createBitmap(drawingCache);
            decorView.setDrawingCacheEnabled(false);
            return bitmap;
        }
        return null;
    }
}
