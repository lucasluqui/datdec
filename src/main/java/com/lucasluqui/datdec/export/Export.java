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

  private static void convert (File file)
  {
    String path = file.getAbsolutePath();
    try {
      String dest = path.replaceFirst("\\.dat$", ".xml");
      BinaryImporter in = new BinaryImporter(new FileInputStream(file));
      XMLExporter out = new XMLExporter(new FileOutputStream(dest));
      try {
        while (true) {
          out.writeObject(in.readObject());
        }
      } catch (EOFException e) {
        // no problem
      } finally {
        in.close();
        out.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
