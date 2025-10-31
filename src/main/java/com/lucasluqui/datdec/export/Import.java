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
    try {
      String dest = path.replaceFirst("\\.xml$", ".dat");
      if (DatdecSettings.doBackups) FileUtil.backupFile(dest);
      XMLImporter in = new XMLImporter(new FileInputStream(file));
      BinaryExporter out = new BinaryExporter(new FileOutputStream(dest), COMPRESS);
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
      file.delete();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
