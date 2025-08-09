package com.lucasallegri.datdec.util;

import java.util.List;

import com.lucasallegri.datdec.gui.DatdecGUI;

public class ConfigChoice {
	
	public static void populate() {
		
		if(DatdecGUI.configList.getItemCount() > 0) DatdecGUI.configList.removeAll();
		
		List<String> _list = FileUtil.fileNamesInDirectory("rsrc/config/");
		DatdecGUI.configCountLabel.setText("(" + _list.size() + " found)");
		for(int i = 0; i < _list.size(); i++) DatdecGUI.configList.add(_list.get(i));
		if(_list.size() > 0) {
			DatdecGUI.decompileButton.setEnabled(true);
			DatdecGUI.decompileAllButton.setEnabled(true);
			DatdecGUI.compileAllButton.setEnabled(true);
		}
		
	}

}
