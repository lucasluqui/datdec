package com.lucasallegri.datdec.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;

import com.lucasallegri.datdec.compile.Compile;
import com.lucasallegri.datdec.decompile.Decompile;
import com.lucasallegri.datdec.gui.util.ConfigChoice;

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
			//LegacyDecompile.decompile());
		} else {
			Decompile.decompile();
			DatdecGUI.pushState(DatdecContext.selectedConfig + " was decompiled.");
			ConfigChoice.populate();
		}
		
	}
	
	public static void decompileAll(ActionEvent _action) {
		
		if(DatdecContext.useOldClassMappings) {
			//LegacyDecompile.decompileAll();
		} else {
			int nFiles = Decompile.decompileAll();
			DatdecGUI.pushState(String.format("%s configs were decompiled.", nFiles));
			ConfigChoice.populate();
		}
		
	}
	
	public static void compile(ActionEvent _action) {

		Compile.compile();
		DatdecGUI.pushState(DatdecContext.selectedConfig + " was compiled.");
		ConfigChoice.populate();
		
	}
	
	public static void compileAll(ActionEvent _action) {
		
		int nFiles = Compile.compileAll();
		DatdecGUI.pushState(String.format("%s configs were compiled.", nFiles));
		ConfigChoice.populate();
		
	}

}
