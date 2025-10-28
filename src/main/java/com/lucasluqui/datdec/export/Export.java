package com.lucasluqui.datdec.export;

import com.lucasluqui.datdec.util.FileUtil;
import com.lucasluqui.datdec.util.PathUtil;
import com.threerings.export.tools.BinaryToXMLConverter;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Export
{
  public static void exportSingle (String file)
  {
    convert(file);
  }

  public static int exportAll ()
  {
    List<String> list = FileUtil.fileNamesInDirectory("rsrc/config/");
    for (String file : list) {
      if (file.endsWith(".dat")) convert(file);
    }
    return list.size();
  }

  private static void convert (String file)
  {
    File source = new File(PathUtil.getPathToConfig(file));
    String path = source.getAbsolutePath();
    try {
      String dest = path.replaceFirst("\\.dat$", ".xml");
      BinaryToXMLConverter.convert(path, dest);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
