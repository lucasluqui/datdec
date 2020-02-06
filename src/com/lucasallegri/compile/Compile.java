package com.lucasallegri.compile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.lucasallegri.gui.DatdecContext;
import com.lucasallegri.gui.DatdecGUI;
import com.lucasallegri.ooo.OOOImporter;
import com.lucasallegri.util.ConfigChoice;
import com.lucasallegri.util.FileUtil;
import com.lucasallegri.util.PathUtil;

public class Compile {
	
	public static void compile() {
		
		File source = new File(PathUtil.getPathToConfig(DatdecContext.selectedConfig));
		FileInputStream in = null;
		String path = source.getAbsolutePath();
		
		try {
			
			in = new FileInputStream(source);
			String dest = path.replaceFirst("\\.xml$", ".dat");
			if(DatdecContext.doBackups) FileUtil.backupFile(dest);
			FileOutputStream out = new FileOutputStream(dest);
			OOOImporter._import(in, out);
			source.delete();
			DatdecGUI.pushState(DatdecContext.selectedConfig + " was compiled.");
			ConfigChoice.populate();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void compileAll() {
		
		List<String> _list = FileUtil.fileNamesInDirectory("rsrc/config/");
		
		for(int i = 0; i < _list.size(); i++) {
			
			if(_list.get(i).endsWith(".dat")) continue;
			
			File source = new File(PathUtil.getPathToConfig(_list.get(i)));
			FileInputStream in = null;
			String path = source.getAbsolutePath();
			
			try {
				
				in = new FileInputStream(source);
				String dest = path.replaceFirst("\\.xml$", ".dat");
				if(DatdecContext.doBackups) FileUtil.backupFile(dest);
				FileOutputStream out = new FileOutputStream(dest);
				OOOImporter._import(in, out);
				source.delete();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		DatdecGUI.pushState(_list.size() + " configs were compiled.");
		ConfigChoice.populate();
		
	}

}
