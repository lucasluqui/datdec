package com.lucasluqui.datdec.export;

import com.lucasluqui.datdec.DatdecSettings;
import com.lucasluqui.datdec.util.FileUtil;
import com.lucasluqui.datdec.util.PathUtil;
import com.lucasluqui.datdec.util.ReflectionUtil;
import com.lucasluqui.datdec.util.StringUtil;
import com.threerings.export.BinaryExporter;
import com.threerings.export.XMLImporter;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class Import
{
  public static void importSingle (File file)
  {
    if (file.getName().contains("colordefs")) {
      ColorDefs.importColorDefs(file);
      return;
    }

    convert(file);
  }

  public static int importAll ()
  {
    List<String> fileNames = FileUtil.fileNamesInDirectory("rsrc/config/");
    for (String fileName : fileNames) {
      if (fileName.endsWith(".xml")) convert(new File(PathUtil.getPathToConfig(fileName)));
    }
    return fileNames.size(); // why not return the number that were converted?
  }

  private static void convert (File file)
  {
    String path = file.getAbsolutePath();
    String dest = path.replaceFirst("\\.xml$", ".dat");
    if (DatdecSettings.doBackups) FileUtil.backupFile(dest);

    XMLImporter in = null;
    BinaryExporter out = null;
    try {
      in = new XMLImporter(new FileInputStream(file));
      out = new BinaryExporter(new FileOutputStream(dest));
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
      System.out.println("Importing " + StringUtil.sanitizedClassName(String.valueOf(object.getClass())) + "...");
      try {
        writeObject.invoke(out, object);
      } catch (InvocationTargetException e) {
        throw new RuntimeException(e.getCause());
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      }
      System.out.println("Successfully imported " + StringUtil.sanitizedClassName(String.valueOf(object.getClass())) + "...");
    }
  }

  /**
   * Finds XMLImporter::readObject by signature.
   */
  private static Method getReadObjectMethod ()
  {
    if (_readObject == null) {
      _readObject = ReflectionUtil.findMethod(XMLImporter.class, Object.class);
    }
    return _readObject;
  }

  /**
   * Finds BinaryExporter::writeObject by signature.
   */
  private static Method getWriteObjectMethod ()
  {
    if (_writeObject == null) {
      _writeObject = ReflectionUtil.findMethod(BinaryExporter.class, void.class, Object.class);
    }
    return _writeObject;
  }

  private static Method _readObject;
  private static Method _writeObject;
}
