package com.lucasluqui.datdec.export;

import com.lucasluqui.datdec.DatdecSettings;
import com.lucasluqui.datdec.util.FileUtil;
import com.lucasluqui.datdec.util.PathUtil;
import com.threerings.export.tools.XMLToBinaryConverter;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Import
{
  private static final boolean COMPRESS = true;

  public static void importSingle (String file)
  {
    convert(file);
  }

  public static int importAll ()
  {
    List<String> list = FileUtil.fileNamesInDirectory("rsrc/config/");
    for (String file : list) {
      if (file.endsWith(".xml")) convert(file);
    }
    return list.size(); // why not return the number that were converted?
  }

  private static void convert (String file)
  {
    File source = new File(PathUtil.getPathToConfig(file));
    String path = source.getAbsolutePath();
    try {
      String dest = path.replaceFirst("\\.xml$", ".dat");
      if (DatdecSettings.doBackups) FileUtil.backupFile(dest);
      XMLToBinaryConverter.convert(path, dest, COMPRESS);
      source.delete();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
