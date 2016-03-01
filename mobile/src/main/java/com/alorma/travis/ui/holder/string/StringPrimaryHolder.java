package com.alorma.travis.ui.holder.string;

public class StringPrimaryHolder implements StringHolder {
  private String string;

  public StringPrimaryHolder(String string) {
    super();
    this.string = string;
  }

  @Override
  public String get() {
    return string;
  }
}
