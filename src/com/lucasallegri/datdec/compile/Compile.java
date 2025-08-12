package com.lucasallegri.datdec.compile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.lucasallegri.datdec.gui.DatdecContext;
import com.lucasallegri.datdec.gui.DatdecGUI;
import com.lucasallegri.datdec.ooo.OOOImporter;
import com.lucasallegri.datdec.gui.util.ConfigChoice;
import com.lucasallegri.datdec.util.FileUtil;
import com.lucasallegri.datdec.util.PathUtil;

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
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static int compileAll() {
		
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
		
		return _list.size();
		
	}

}
