package com.lucasluqui.datdec.export;

import com.lucasluqui.datdec.util.FileUtil;
import com.lucasluqui.datdec.util.PathUtil;
import com.threerings.export.BinaryImporter;
import com.threerings.export.XMLExporter;

import com.threerings.media.image.ColorPository;
import com.threerings.media.image.ColorPository.ClassRecord;
import com.threerings.media.image.ColorPository.ColorRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Export
{
  public static void exportSingle (File file)
  {
    if (file.getName().contains("colordefs")) {
      convertColordefs(file);
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

  // Credits to flowmailed @ Discord.
  private static void convertColordefs (File file)
  {
    File dest = new File(file.getAbsolutePath().replaceFirst("\\.dat$", ".xml"));
    InputStream stream;

    try {
      stream = new FileInputStream(file);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }

    ObjectInputStream oin = null;
    Object object = null;
    PrintWriter xml = null;

    try {
      oin = new ObjectInputStream(stream);
      object = oin.readObject();
      xml = new PrintWriter(dest);
    } catch (ClassNotFoundException e) {
      System.err.println("Can't parse colordefs.");
    } catch (FileNotFoundException e) {
      System.err.println("Can't open output xml.");
    } catch (IOException e) {
      System.err.println("Can't open colordefs.");
    } finally {
      try {
        if (oin != null)
          oin.close();
      } catch (IOException e1) {
        System.err.println("Can't close colordefs after error.");
      }
    }

    ColorPository pos = (ColorPository) object;
    xml.write("<?xml version=\"1.0\" standalone=\"yes\"?>\r\n");
    xml.write("<colors>\r\n");
    ArrayList<ClassRecord> classes = new ArrayList<ClassRecord>(pos.getClasses());
    Collections.sort(classes, (a, b) -> (a.classId - b.classId));

    for (ClassRecord cr : classes) {
      xml.write("  <class classId=\"" + cr.classId + "\" name=\"" + cr.name + "\" source=\"#" + String.format("%06x", cr.source.getRGB() & 0xffffff) + "\" range=\"" + floatArrayToString(cr.range) + "\"");
      if (cr.starter) {
        xml.write(" starter=\"true\"");
      }
      xml.write(">\r\n");
      ArrayList<ColorRecord> colors = new ArrayList<ColorRecord>(cr.colors.values());
      Collections.sort(colors, (a, b) -> (a.colorId - b.colorId));
      for (ColorRecord cl : colors) {
        xml.write("    <color colorId=\"" + cl.colorId + "\" name=\"" + cl.name + "\" offsets=\"" + floatArrayToString(cl.offsets) + "\"");
        if (cl.starter) {
          xml.write(" starter=\"true\"");
        }
        xml.write(">\r\n");
      }
      xml.write("  </class>\r\n");
    }

    xml.write("</colors>\r\n");
    xml.close();
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

  private static String floatArrayToString(float[] numbers) {
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
