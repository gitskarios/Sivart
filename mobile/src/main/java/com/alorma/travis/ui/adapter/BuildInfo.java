package com.alorma.travis.ui.adapter;

import com.alorma.travis.ui.holder.drawable.DrawableHolder;
import com.alorma.travis.ui.holder.string.NullableStringHolder;
import com.alorma.travis.ui.holder.string.StringHolder;

public class BuildInfo {
  private DrawableHolder drawableHolder;
  private StringHolder title = new NullableStringHolder();
  private StringHolder value = new NullableStringHolder();

  public DrawableHolder getDrawableHolder() {
    return drawableHolder;
  }

  public void setDrawableHolder(DrawableHolder drawableHolder) {
    this.drawableHolder = drawableHolder;
  }

  public StringHolder getTitle() {
    return title;
  }

  public void setTitle(StringHolder title) {
    this.title = title;
  }

  public StringHolder getValue() {
    return value;
  }

  public void setValue(StringHolder value) {
    this.value = value;
  }
}
