package com.lucasallegri.datdec.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
	
	public static List<String> fileNamesInDirectory(String dir) {
		
		File folder = new File(dir);
		File[] fileList = folder.listFiles();
		List<String> fileNames = new ArrayList<String>();
		
		for(int i = 0; i < fileList.length; i++) {
			if(fileList[i].isDirectory() == false) { fileNames.add(fileList[i].getName()); }
		}
		
		return fileNames;
	}
	
	public static void backupFile(String target) {
		try {
			Files.copy(Paths.get(target), Paths.get(target+".backup"), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
