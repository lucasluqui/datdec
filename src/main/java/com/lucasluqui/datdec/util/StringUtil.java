package com.lucasluqui.datdec.util;

public class StringUtil
{
  public static String sanitizedClassName (String className)
  {
    return className
      .replace("class ", "")
      .replace("[L", "")
      .replace(";", "");
  }
}
