package com.alorma.travis.ui.holder.drawable;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.IIcon;

public class IconicDrawableHolder implements DrawableHolder {
  private Context context;
  private IIcon icon;
  @ColorInt private int color;

  public IconicDrawableHolder(Context context, IIcon icon) {
    super();
    this.context = context;
    this.icon = icon;
  }

  public IconicDrawableHolder color(@ColorInt int color) {
    this.color = color;
    return this;
  }

  @Override
  public IconicsDrawable get() {
    return new IconicsDrawable(context).icon(icon).color(color);
  }
}
