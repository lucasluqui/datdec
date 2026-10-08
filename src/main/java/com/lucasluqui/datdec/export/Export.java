package com.lucasluqui.datdec.export;

import com.lucasluqui.datdec.util.FileUtil;
import com.lucasluqui.datdec.util.PathUtil;
import com.lucasluqui.datdec.util.ReflectionUtil;
import com.lucasluqui.datdec.util.StringUtil;
import com.threerings.export.BinaryImporter;
import com.threerings.export.XMLExporter;

import java.awt.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
      // special delivery for parma.
      if (fileName.contains("colordefs")) continue;

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

    Method readObject = getReadObjectMethod();
    Method writeObject = getWriteObjectMethod();

    while (true) {
      try {
        object = readObject.invoke(in);
      } catch (Exception e) {
        in.close();
        out.close();
        return;
      }
      System.out.println("Exporting " + StringUtil.sanitizedClassName(String.valueOf(object.getClass())) + "...");
      try {
        writeObject.invoke(out, object);
      } catch (InvocationTargetException e) {
        throw new RuntimeException(e.getCause());
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      }
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

  /**
   * Finds BinaryImporter::readObject by signature.
   */
  private static Method getReadObjectMethod ()
  {
    if (_readObject == null) {
      _readObject = ReflectionUtil.findMethod(BinaryImporter.class, Object.class);
    }
    return _readObject;
  }

  /**
   * Finds XMLExporter::writeObject by signature.
   */
  private static Method getWriteObjectMethod ()
  {
    if (_writeObject == null) {
      _writeObject = ReflectionUtil.findMethod(XMLExporter.class, void.class, Object.class);
    }
    return _writeObject;
  }

  private static Method _readObject;
  private static Method _writeObject;
}
