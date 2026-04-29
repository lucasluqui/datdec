package com.lucasluqui.datdec.export;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

import static com.lucasluqui.datdec.export.Export.floatArrayToString;

// Credits to flowmailed @ Discord.
public class ColorDefs
{
  protected static void exportColorDefs (File file)
  {
    exportColorDefs(file, false);
  }

  protected static void exportColorDefs (File file, boolean html)
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
    ArrayList<ColorPository.ClassRecord> classes = new ArrayList<ColorPository.ClassRecord>(pos.getClasses());
    Collections.sort(classes, (a, b) -> (a.classId - b.classId));

    for (ColorPository.ClassRecord cr : classes) {
      xml.write("  <class classId=\"" + cr.classId + "\" name=\"" + cr.name + "\" source=\"#" + String.format("%06x", cr.source.getRGB() & 0xffffff) + "\" range=\"" + floatArrayToString(cr.range) + "\"");
      if (cr.starter) {
        xml.write(" starter=\"true\"");
      }
      xml.write(">\r\n");
      ArrayList<ColorPository.ColorRecord> colors = new ArrayList<ColorPository.ColorRecord>(cr.colors.values());
      Collections.sort(colors, (a, b) -> (a.colorId - b.colorId));
      for (ColorPository.ColorRecord cl : colors) {
        xml.write("    <color colorId=\"" + cl.colorId + "\" name=\"" + cl.name + "\" offsets=\"" + floatArrayToString(cl.offsets) + "\"");
        if (cl.starter) {
          xml.write(" starter=\"true\"");
        }
        xml.write("/>\r\n");
      }
      xml.write("  </class>\r\n");
    }

    xml.write("</colors>\r\n");
    xml.close();

    if (html) {
      File htmlDest = new File(file.getAbsolutePath().replaceFirst("\\.dat$", ".html"));

      try {
        writeColorDefsToHtml(pos, new FileOutputStream(htmlDest));
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
    }
  }

  protected static void importColorDefs (File file)
  {
    File dest = new File(file.getAbsolutePath().replaceFirst("\\.xml$", ".dat"));

    SAXParserFactory parserFactory = SAXParserFactory.newInstance();
    ColorDefsHandler cdHandler = new ColorDefsHandler();

    try {
      SAXParser parser = parserFactory.newSAXParser();
      parser.parse(new FileInputStream(file), cdHandler);
    } catch (SAXException | IOException | ParserConfigurationException var4) {
      System.err.println("Problem reading in xml file.");
    }

    ColorPository colorpos = cdHandler.colorpos;

    try {
      ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dest));

      try {
        oos.writeObject(colorpos);
      } catch (Throwable e) {
        try {
          oos.close();
        } catch (Throwable ee) {
          e.addSuppressed(ee);
        }
        throw e;
      }

      oos.close();
    } catch (IOException e) {
      System.err.println("Problem writing out file.");
    }
  }

  private static void writeColorDefsToHtml (ColorPository colorpos, OutputStream out)
  {
    if (colorpos == null) {
      return;
    }

    PrintWriter writer = new PrintWriter(out);
    writer.write("<!DOCTYPE html>\r\n<html><head><style>body { font-size:75%;background:white;color:black } .cl { clear:left;margin-bottom:0.5em;padding:0.5em 0 } .cn { padding:0.5em;margin:0.5em 0;width:30em} .cl .cr { width:8em;min-height:5em;text-align:center;padding:0.5em;float:left } .b { color: black } .w { color: white }</style></head>\r\n<body>\r\n");

    List<ColorPository.ClassRecord> classRecords = new ArrayList<>(colorpos.getClasses());
    classRecords.sort(Comparator.comparingInt((rec) -> rec.classId));

    float[] apOffsets = new float[0];
    LinkedList<ColorPository.ColorRecord> apColorRecords = new LinkedList<>();
    float[] ajOffsets = new float[0];
    LinkedList<ColorPository.ColorRecord> ajColorRecords = new LinkedList<>();
    float[] asOffsets = new float[0];
    LinkedList<ColorPository.ColorRecord> asColorRecords = new LinkedList<>();

    for (ColorPository.ClassRecord classRecord : classRecords) {
      Color colorSource = classRecord.source;

      String htmlSourceColor = String.format("%06x", colorSource.getRGB() & 16777215);
      writer.write("<div class=\"cl\">\r\n\t<div class=\"cn\" style=\"border:2px solid #" + htmlSourceColor + "\">" + classRecord.classId + ". " + classRecord.name + ": source=#" + htmlSourceColor + ", range=" + rangeToString(classRecord.range) + "</div>\r\n");

      List<ColorPository.ColorRecord> colorRecords = new ArrayList<>(classRecord.colors.values());
      colorRecords.sort(Comparator.comparingInt((rec) -> rec.colorId));

      float[] offsets = Color.RGBtoHSB(
        colorSource.getRed(), colorSource.getGreen(), colorSource.getBlue(), null);
      int id = classRecord.classId << 8;

      for (ColorPository.ColorRecord colorRecord : colorRecords) {
        if (classRecord.name.equalsIgnoreCase("armor_primary")) {
          apOffsets = offsets;
          apColorRecords.add(colorRecord);
        }

        if (classRecord.name.equalsIgnoreCase("armor_joint")) {
          ajOffsets = offsets;
          ajColorRecords.add(colorRecord);
        }

        if (classRecord.name.equalsIgnoreCase("armor_secondary")) {
          asOffsets = offsets;
          asColorRecords.add(colorRecord);
        }

        float hue = offsets[0] + colorRecord.offsets[0];
        float sat = Math.min(Math.max(offsets[1] + colorRecord.offsets[1], 0.0F), 1.0F);
        float bri = Math.min(Math.max(offsets[2] + colorRecord.offsets[2], 0.0F), 1.0F);

        int rgb = Color.HSBtoRGB(hue, sat, bri);

        String htmlColor = String.format("%06x", rgb & 16777215);
        writer.write("\t\t<div style=\"background:#" + htmlColor + "\" class=\"cr " + (bri > 0.729F ? "b" : "w") + "\">" + (id + colorRecord.colorId) + "<br />" + colorRecord.name + "<br />#" + htmlColor + "</div>\r\n");
      }

      writer.write("</div>\r\n");
    }

    writer.write("<div class=\"cl\">\r\n\t<div class=\"cn\" style=\"border:2px solid\">armor palettes (primary, secondary, joint... or primary, secondary, tertiary. that works too)</div>\r\n");
    for (int i = 0; i < apColorRecords.size(); i++) {
      ColorPository.ColorRecord primaryRecord = apColorRecords.get(i);
      float huePrimary = apOffsets[0] + primaryRecord.offsets[0];
      float satPrimary = Math.min(Math.max(apOffsets[1] + primaryRecord.offsets[1], 0.0F), 1.0F);
      float briPrimary = Math.min(Math.max(apOffsets[2] + primaryRecord.offsets[2], 0.0F), 1.0F);
      int rgbPrimary = Color.HSBtoRGB(huePrimary, satPrimary, briPrimary);
      String htmlColorPrimary = String.format("%06x", rgbPrimary & 16777215);

      ColorPository.ColorRecord jointRecord = ajColorRecords.get(i);
      float hueJoint = ajOffsets[0] + jointRecord.offsets[0];
      float satJoint = Math.min(Math.max(ajOffsets[1] + jointRecord.offsets[1], 0.0F), 1.0F);
      float briJoint = Math.min(Math.max(ajOffsets[2] + jointRecord.offsets[2], 0.0F), 1.0F);
      int rgbJoint = Color.HSBtoRGB(hueJoint, satJoint, briJoint);
      String htmlColorJoint = String.format("%06x", rgbJoint & 16777215);

      ColorPository.ColorRecord secondaryRecord = asColorRecords.get(i);
      float hueSecondary = asOffsets[0] + secondaryRecord.offsets[0];
      float satSecondary = Math.min(Math.max(asOffsets[1] + secondaryRecord.offsets[1], 0.0F), 1.0F);
      float briSecondary = Math.min(Math.max(asOffsets[2] + secondaryRecord.offsets[2], 0.0F), 1.0F);
      int rgbSecondary = Color.HSBtoRGB(hueSecondary, satSecondary, briSecondary);
      String htmlColorSecondary = String.format("%06x", rgbSecondary & 16777215);

      writer.write("\t\t<div style=\"background:#" + htmlColorPrimary + "\" class=\"cr " + (briPrimary > 0.729F ? "b" : "w") + "\">" + primaryRecord.colorId + "<br />" + primaryRecord.name + "<br />#" + htmlColorPrimary + "</div>\r\n");
      writer.write("\t\t<div style=\"background:#" + htmlColorSecondary + "\" class=\"cr " + (briSecondary > 0.729F ? "b" : "w") + "\">" + secondaryRecord.colorId + "<br />" + secondaryRecord.name + "<br />#" + htmlColorSecondary + "</div>\r\n");
      writer.write("\t\t<div style=\"background:#" + htmlColorJoint + "\" class=\"cr " + (briJoint > 0.729F ? "b" : "w") + "\">" + jointRecord.colorId + "<br />" + jointRecord.name + "<br />#" + htmlColorJoint + "</div>\r\n");
      writer.write("<br><br><br><br><br>\r\n");
    }
    writer.write("</div>\r\n");

    writer.write("</body></html>");
    writer.close();
  }

  private static float[] parseFloatArray (String text)
  {
    String[] textSplit = text.split(",\\s*", 4);
    int size = Math.min(textSplit.length, 3);
    float[] floatArr = new float[3];

    for (int i = 0; i < size; i++) {
      try {
        floatArr[i] = Float.parseFloat(textSplit[i]);
      } catch (NumberFormatException nfe) {}
    }

    return floatArr;
  }

  private static float[] diffColors (Color color1, Color color2)
  {
    float[] color1HSB = Color.RGBtoHSB(color1.getRed(), color1.getGreen(), color1.getBlue(), null);
    float[] color2HSB = Color.RGBtoHSB(color2.getRed(), color2.getGreen(), color2.getBlue(), null);
    float[] diff = new float[]{color2HSB[0] - color1HSB[0], color2HSB[1] - color1HSB[1], color2HSB[2] - color1HSB[2]};
    if (color2HSB[1] == 0.0F) {
      diff[0] = 0.0F;
    }

    return diff;
  }

  public static String rangeToString (float[] range)
  {
    if (range == null) {
      return "null";
    } else {
      StringBuilder sb = new StringBuilder();
      boolean first = true;

      for (float val : range) {
        if (first) {
          first = false;
        } else {
          sb.append(", ");
        }

        sb.append(val);
      }

      return sb.toString();
    }
  }

  public static class ColorDefsHandler extends DefaultHandler
  {
    ColorPository colorpos;
    ColorPository.ClassRecord classrec;

    public void startElement (String namespace, String localName, String qualifiedName, Attributes attributes)
    {
      switch (qualifiedName) {
        case "colors":
          this.colorpos = new ColorPository();
          break;
        case "class":
          this.classrec = new ColorPository.ClassRecord();

          try {
            this.classrec.classId = Integer.parseInt(attributes.getValue("classId"));
          } catch (NumberFormatException nfe) {}

          this.classrec.name = attributes.getValue("name");
          this.classrec.source = Color.decode(attributes.getValue("source"));
          this.classrec.range = parseFloatArray(attributes.getValue("range"));
          this.classrec.starter = Boolean.parseBoolean(attributes.getValue("starter"));

          try {
            this.classrec.defaultId = Integer.parseInt(attributes.getValue("defaultId"));
          } catch (NumberFormatException nfe) {}

          this.colorpos._classes.put(this.classrec.classId, this.classrec);
          break;
        case "color":
          ColorPository.ColorRecord colorRecord = new ColorPository.ColorRecord();

          try {
            colorRecord.colorId = Integer.parseInt(attributes.getValue("colorId"));
          } catch (NumberFormatException nfe) {}

          colorRecord.name = attributes.getValue("name");
          String sourceString = attributes.getValue("source");
          if (sourceString != null) {
            try {
              Color colorSource = Color.decode(sourceString);
              colorRecord.offsets = diffColors(this.classrec.source, colorSource);
            } catch (NumberFormatException nfe) {}
          }

          if (colorRecord.offsets == null) {
            colorRecord.offsets = parseFloatArray(attributes.getValue("offsets"));
          }

          colorRecord.starter = Boolean.parseBoolean(attributes.getValue("starter"));
          this.classrec.colors.put(colorRecord.colorId, colorRecord);
      }
    }
  }
}
