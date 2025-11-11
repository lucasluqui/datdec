package com.lucasluqui.datdec.export;

import com.lucasluqui.datdec.DatdecSettings;
import com.lucasluqui.datdec.util.FileUtil;
import com.lucasluqui.datdec.util.PathUtil;
import com.threerings.export.BinaryExporter;
import com.threerings.export.XMLImporter;

import java.io.*;
import java.util.List;

public class Import
{
  private static final boolean COMPRESS = true;

  public static void importSingle (File file)
  {
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
