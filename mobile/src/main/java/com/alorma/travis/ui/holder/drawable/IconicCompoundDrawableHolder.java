package com.alorma.travis.ui.holder.drawable;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DimenRes;
import android.support.annotation.Size;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.IIcon;

public class IconicCompoundDrawableHolder extends IconicDrawableHolder {

  @DimenRes private int size;

  public IconicCompoundDrawableHolder(Context context, IIcon icon) {
    super(context, icon);
  }

  public IconicCompoundDrawableHolder size(@DimenRes int size) {
    this.size = size;
    return this;
  }

  @Override
  public IconicsDrawable get() {
    return super.get().sizeRes(size);
  }
}
