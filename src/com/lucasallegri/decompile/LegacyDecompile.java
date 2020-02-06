package com.lucasallegri.decompile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.lucasallegri.gui.DatdecContext;
import com.lucasallegri.gui.DatdecGUI;
import com.lucasallegri.ooo.LegacyOOOExporter;
import com.lucasallegri.util.ConfigChoice;
import com.lucasallegri.util.FileUtil;
import com.lucasallegri.util.PathUtil;

public class LegacyDecompile {

	public static void decompile() {
		
		File source = new File(PathUtil.getPathToConfig(DatdecContext.selectedConfig));
		FileInputStream in = null;
		String path = source.getAbsolutePath();
		
		try {
			
			in = new FileInputStream(source);
			String dest = path.replaceFirst("\\.dat$", ".xml");
			FileOutputStream out = new FileOutputStream(dest);
			LegacyOOOExporter.export(in, out);
			DatdecGUI.pushState(DatdecContext.selectedConfig + " was decompiled.");
			ConfigChoice.populate();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void decompileAll() {
		
		List<String> _list = FileUtil.fileNamesInDirectory("rsrc/config/");
		
		for(int i = 0; i < _list.size(); i++) {
			
			if(_list.get(i).endsWith(".xml")) continue;
			
			File source = new File(PathUtil.getPathToConfig(_list.get(i)));
			FileInputStream in = null;
			String path = source.getAbsolutePath();
			
			try {
				
				in = new FileInputStream(source);
				String dest = path.replaceFirst("\\.dat$", ".xml");
				FileOutputStream out = new FileOutputStream(dest);
				LegacyOOOExporter.export(in, out);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		DatdecGUI.pushState(_list.size() + " configs were decompiled.");
		ConfigChoice.populate();
		
	}
	
}
