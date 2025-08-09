package com.lucasallegri.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;

import com.lucasallegri.compile.Compile;
import com.lucasallegri.decompile.Decompile;

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
		
		if(DatdecContext.useOldClassMappings) {
			//LegacyDecompile.decompile();
		} else {
			Decompile.decompile();
		}
		
	}
	
	public static void decompileAll(ActionEvent _action) {
		
		if(DatdecContext.useOldClassMappings) {
			//LegacyDecompile.decompileAll();
		} else {
			Decompile.decompileAll();
		}
		
	}
	
	public static void compile(ActionEvent _action) {
		
		Compile.compile();
		
	}
	
	public static void compileAll(ActionEvent _action) {
		
		Compile.compileAll();
		
	}

}
