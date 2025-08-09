package com.lucasallegri.datdec.decompile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.lucasallegri.datdec.gui.DatdecContext;
import com.lucasallegri.datdec.gui.DatdecGUI;
import com.lucasallegri.datdec.ooo.OOOExporter;
import com.lucasallegri.datdec.util.ConfigChoice;
import com.lucasallegri.datdec.util.FileUtil;
import com.lucasallegri.datdec.util.PathUtil;

public class Decompile {
	
	public static void decompile() {
		
		File source = new File(PathUtil.getPathToConfig(DatdecContext.selectedConfig));
		FileInputStream in = null;
		String path = source.getAbsolutePath();
		
		try {
			
			in = new FileInputStream(source);
			String dest = path.replaceFirst("\\.dat$", ".xml");
			FileOutputStream out = new FileOutputStream(dest);
			OOOExporter.export(in, out);
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
				OOOExporter.export(in, out);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		DatdecGUI.pushState(_list.size() + " configs were decompiled.");
		ConfigChoice.populate();
		
	}

}
