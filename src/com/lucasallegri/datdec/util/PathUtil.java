package com.lucasallegri.datdec.util;

import java.io.File;

public class PathUtil {
	
	public static String getPathToConfig(String name) {
		return System.getProperty("user.dir") + File.separator + "rsrc" + File.separator + "config" + File.separator + name;
	}

}
