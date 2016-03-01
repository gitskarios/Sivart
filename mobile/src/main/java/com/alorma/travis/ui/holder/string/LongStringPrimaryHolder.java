package com.alorma.travis.ui.holder.string;

public class LongStringPrimaryHolder implements StringHolder {
  private long number;

  public LongStringPrimaryHolder(long number) {
    this.number = number;
  }

  @Override
  public String get() {
    return "#" + String.valueOf(number);
  }
}
