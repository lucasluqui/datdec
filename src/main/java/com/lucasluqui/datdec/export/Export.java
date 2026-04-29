package com.lucasluqui.datdec.export;

import com.lucasluqui.datdec.util.FileUtil;
import com.lucasluqui.datdec.util.PathUtil;
import com.lucasluqui.datdec.util.StringUtil;
import com.threerings.export.BinaryImporter;
import com.threerings.export.XMLExporter;

import java.awt.*;
import java.io.*;
import java.util.List;

public class Export
{
  public static void exportSingle (File file)
  {
    if (file.getName().contains("colordefs")) {
      ColorDefs.exportColorDefs(file, true);
      return;
    }

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
        // pN = BinaryImporter::readObject
        object = in.pN();
      } catch (Exception e) {
        in.close();
        out.close();
        return;
      }
      System.out.println("Exporting " + StringUtil.sanitizedClassName(String.valueOf(object.getClass())) + "...");
      // be = XMLExporter::writeObject
      out.be(object);
      System.out.println("Successfully exported " + StringUtil.sanitizedClassName(String.valueOf(object.getClass())));
    }
  }

  protected static String floatArrayToString(float[] numbers) {
    StringBuilder sb = new StringBuilder();
    boolean first = true;
    for (float number : numbers) {
      if (first) {
        first = false;
      } else {
        sb.append(", ");
      }
      sb.append(number);
    }
    return sb.toString();
  }
}
