package com.lucasluqui.datdec.gui.util;

import com.lucasluqui.datdec.gui.DatdecContext;
import com.lucasluqui.datdec.gui.DatdecGUI;
import com.lucasluqui.datdec.util.FileUtil;

import java.util.List;

public class ConfigChoice
{
  public static void populate ()
  {

    if (DatdecGUI.configList.getItemCount() > 0) DatdecGUI.configList.removeAll();

    List<String> _list = FileUtil.fileNamesInDirectory("rsrc/config/");
    DatdecGUI.configCountLabel.setText("(" + _list.size() + " found)");
    for (int i = 0; i < _list.size(); i++) DatdecGUI.configList.add(_list.get(i));
    if (_list.size() > 0) {
      DatdecGUI.decompileButton.setEnabled(true);
      DatdecGUI.decompileAllButton.setEnabled(true);
      DatdecGUI.compileAllButton.setEnabled(true);
    }
    DatdecContext.selectedConfig = DatdecGUI.configList.getItem(0);
  }

}
