package com.wen.magi.baseframe.interfaces;

import android.content.res.Resources;
import android.support.annotation.NonNull;

/**
 * @author MVEN @ Zhihu Inc.
 * @since 09-30-2016
 */

public interface IThemeView {

    void setTheme(final Resources.Theme pTheme);

    @NonNull
    void getView();
}
