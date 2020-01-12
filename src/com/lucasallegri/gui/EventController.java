package com.lucasallegri.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.lucasallegri.ooo.OOOExporter;
import com.lucasallegri.ooo.OOOImporter;
import com.lucasallegri.util.FileUtil;

public class EventController {
	
	public static void configChoiceChanged(ItemEvent event) {
		
		DatdecContext.selectedConfig = event.getItem().toString();
		
		if (DatdecContext.selectedConfig.endsWith(".dat")) {
			DatdecGUI.compileButton.setEnabled(false);
			DatdecGUI.decompileButton.setEnabled(true);
		} else {
			DatdecGUI.compileButton.setEnabled(true);
			DatdecGUI.decompileButton.setEnabled(false);
		}
		
	}
	
	public static void decompile(ActionEvent _action) {
		
		File source = new File(getPathToConfig(DatdecContext.selectedConfig));
		FileInputStream in = null;
		String path = source.getAbsolutePath();
		
		try {
			
			in = new FileInputStream(source);
			String dest = path.replaceFirst("\\.dat$", ".xml");
			FileOutputStream out = new FileOutputStream(dest);
			//if(DatdecContext.useWatermark) out.write(DatdecConstants.exportWatermark.getBytes());
			OOOExporter._export(in, out);
			DatdecGUI.pushState(DatdecContext.selectedConfig + " was decompiled.");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void decompileAll(ActionEvent _action) {
		
		List<String> _list = FileUtil.fileNamesInDirectory("rsrc/config/");
		
		for(int i = 0; i < _list.size(); i++) {
			
			if(_list.get(i).endsWith(".xml")) continue;
			
			File source = new File(getPathToConfig(_list.get(i)));
			FileInputStream in = null;
			String path = source.getAbsolutePath();
			
			try {
				
				in = new FileInputStream(source);
				String dest = path.replaceFirst("\\.dat$", ".xml");
				FileOutputStream out = new FileOutputStream(dest);
				//if(DatdecContext.useWatermark) out.write(DatdecConstants.exportWatermark.getBytes());
				OOOExporter._export(in, out);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		DatdecGUI.pushState(_list.size() + " configs were decompiled.");
		
	}
	
	public static void compile(ActionEvent _action) {
		
		File source = new File(getPathToConfig(DatdecContext.selectedConfig));
		FileInputStream in = null;
		String path = source.getAbsolutePath();
		
		try {
			
			in = new FileInputStream(source);
			String dest = path.replaceFirst("\\.xml$", ".dat");
			FileOutputStream out = new FileOutputStream(dest);
			OOOImporter._import(in, out);
			source.delete();
			DatdecGUI.pushState(DatdecContext.selectedConfig + " was compiled.");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void compileAll(ActionEvent _action) {
		
		List<String> _list = FileUtil.fileNamesInDirectory("rsrc/config/");
		
		for(int i = 0; i < _list.size(); i++) {
			
			if(_list.get(i).endsWith(".dat")) continue;
			
			File source = new File(getPathToConfig(_list.get(i)));
			FileInputStream in = null;
			String path = source.getAbsolutePath();
			
			try {
				
				in = new FileInputStream(source);
				String dest = path.replaceFirst("\\.xml$", ".dat");
				FileOutputStream out = new FileOutputStream(dest);
				OOOImporter._import(in, out);
				source.delete();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		DatdecGUI.pushState(_list.size() + " configs were compiled.");
		
	}
	
	private static String getPathToConfig(String name) {
		return System.getProperty("user.dir") + File.separator + "rsrc" + File.separator + "config" + File.separator + name;
	}

}
