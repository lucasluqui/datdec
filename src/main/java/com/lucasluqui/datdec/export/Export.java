package com.lucasluqui.datdec.export;

import com.lucasluqui.datdec.util.FileUtil;
import com.lucasluqui.datdec.util.PathUtil;
import com.threerings.export.tools.BinaryToXMLConverter;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Export
{
  public static void exportSingle (File file)
  {
    convert(file);
  }

  public static int exportAll ()
  {
    List<String> fileNames = FileUtil.fileNamesInDirectory("rsrc/config/");
    for (String fileName : fileNames) {
      if (fileName.endsWith(".dat")) convert(new File(PathUtil.getPathToConfig(fileName)));
    }
    return fileNames.size();
  }

  private static void convert (File file)
  {
    String path = file.getAbsolutePath();
    try {
      String dest = path.replaceFirst("\\.dat$", ".xml");
      BinaryToXMLConverter.convert(path, dest);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
