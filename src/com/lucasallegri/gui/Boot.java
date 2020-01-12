package com.lucasallegri.gui;

import java.util.List;

import com.lucasallegri.util.FileUtil;

public class Boot {
	
	public static void onBoot() {
		populateConfigList();
	}
	
	public static void populateConfigList() {
		
		List<String> _list = FileUtil.fileNamesInDirectory("rsrc/config/");
		DatdecGUI.configCountLabel.setText("(" + _list.size() + " found)");
		for(int i = 0; i < _list.size(); i++) DatdecGUI.configList.add(_list.get(i));
		if(_list.size() > 0) {
			DatdecGUI.decompileButton.setEnabled(true);
			DatdecGUI.decompileAllButton.setEnabled(true);
		}
		
	}

}
