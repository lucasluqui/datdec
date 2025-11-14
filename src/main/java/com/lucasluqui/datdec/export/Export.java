package com.lucasluqui.datdec.export;

import com.lucasluqui.datdec.util.FileUtil;
import com.lucasluqui.datdec.util.PathUtil;
import com.threerings.export.BinaryImporter;
import com.threerings.export.XMLExporter;

import java.io.*;
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

  public static int exportAllCrucible ()
  {
    List<String> fileNames = FileUtil.fileNamesInDirectory("crucible/rsrc/config/");
    for (String fileName : fileNames) {
      if (fileName.endsWith(".dat")) convert(new File(PathUtil.getPathToCrucibleConfig(fileName)));
    }
    return fileNames.size();
  }

  private static void convert (File file)
  {
    String path = file.getAbsolutePath();
    String dest = path.replaceFirst("\\.dat$", ".xml");

    BinaryImporter in = null;
    XMLExporter out = null;
    try {
      in = new BinaryImporter(new FileInputStream(file));
      out = new XMLExporter(new FileOutputStream(dest));
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }

    Object object = null;

    while (true) {
      try {
        object = in.readObject();
      } catch (Exception e) {
        in.close();
        out.close();
        return;
      }
      System.out.println("read=" + object.getClass());
      out.writeObject(object);
    }
  }
}
