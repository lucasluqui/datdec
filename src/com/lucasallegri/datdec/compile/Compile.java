package com.lucasallegri.datdec.compile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.lucasallegri.datdec.gui.DatdecContext;
import com.lucasallegri.datdec.gui.DatdecGUI;
import com.lucasallegri.datdec.gui.util.ConfigChoice;
import com.lucasallegri.datdec.util.FileUtil;
import com.lucasallegri.datdec.util.PathUtil;

import com.threerings.export.tools.XMLToBinaryConverter;

public class Compile {
  private static final boolean COMPRESS = true;

	public static void compile() {
    convert(DatdecContext.selectedConfig);
	}

	public static int compileAll() {
    List<String> list = FileUtil.fileNamesInDirectory("rsrc/config/");
    for (String file : list) {
			if (file.endsWith(".xml")) convert(file);
		}
		return list.size(); // why not return the number that were converted?
	}

  private static void convert(String file) {
		File source = new File(PathUtil.getPathToConfig(file));
		String path = source.getAbsolutePath();
		try {
			String dest = path.replaceFirst("\\.xml$", ".dat");
			if (DatdecContext.doBackups) FileUtil.backupFile(dest);
      XMLToBinaryConverter.convert(path, dest, COMPRESS);
			source.delete();
		} catch (IOException e) {
			e.printStackTrace();
		}
  }
}
