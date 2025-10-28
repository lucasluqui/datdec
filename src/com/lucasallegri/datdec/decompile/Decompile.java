package com.lucasallegri.datdec.decompile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.lucasallegri.datdec.gui.DatdecContext;
import com.lucasallegri.datdec.util.FileUtil;
import com.lucasallegri.datdec.util.PathUtil;

import com.threerings.export.tools.BinaryToXMLConverter;

public class Decompile {

	public static void decompile() {
    convert(DatdecContext.selectedConfig);
	}

	public static int decompileAll() {
		List<String> list = FileUtil.fileNamesInDirectory("rsrc/config/");
    for (String file : list) {
			if (file.endsWith(".dat")) convert(file);
		}
		return list.size();
	}

  private static void convert(String file) {
		File source = new File(PathUtil.getPathToConfig(file));
		String path = source.getAbsolutePath();
		try {
			String dest = path.replaceFirst("\\.dat$", ".xml");
      BinaryToXMLConverter.convert(path, dest);
		} catch (IOException e) {
			e.printStackTrace();
		}
  }
}
