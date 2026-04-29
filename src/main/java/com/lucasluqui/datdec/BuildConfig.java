package com.lucasluqui.datdec;

import com.samskivert.util.Config;

public class BuildConfig
{
  public BuildConfig ()
  {
    // empty.
  }

  public static String getVersion ()
  {
    return _build.getValue("datdec.version", "0");
  }

  /** The build config file. */
  protected static Config _build = new Config("build");
}
