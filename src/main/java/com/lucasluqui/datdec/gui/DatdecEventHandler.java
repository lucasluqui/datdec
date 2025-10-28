package com.lucasluqui.datdec.gui;

import com.lucasluqui.datdec.export.Import;
import com.lucasluqui.datdec.export.Export;
import com.lucasluqui.datdec.util.FileUtil;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.List;

public class DatdecEventHandler
{
  public DatdecEventHandler (DatdecGUI gui)
  {
    this.gui = gui;
  }

  public void updateConfigList ()
  {
    if (gui.configList.getItemCount() > 0) gui.configList.removeAll();

    List<String> _list = FileUtil.fileNamesInDirectory("rsrc/config/");
    gui.configCountLabel.setText("(" + _list.size() + " found)");
    for (int i = 0; i < _list.size(); i++) gui.configList.add(_list.get(i));
    if (_list.size() > 0) {
      gui.exportButton.setEnabled(true);
      gui.exportAllButton.setEnabled(true);
      gui.importAllButton.setEnabled(true);
    }
  }

  public void configChoiceChanged (ItemEvent event)
  {
    if (event.getItem().toString().endsWith(".dat")) {
      gui.importButton.setEnabled(false);
      gui.exportButton.setEnabled(true);
    } else {
      gui.importButton.setEnabled(true);
      gui.exportButton.setEnabled(false);
    }
  }

  public void exportSingle (ActionEvent action)
  {
    String file = gui.configList.getItem(0);
    Export.exportSingle(file);
    gui.setState(file + " was exported.");
    updateConfigList();
  }

  public void exportAll (ActionEvent action)
  {
    int nFiles = Export.exportAll();
    gui.setState(String.format("%s configs were exported.", nFiles));
    updateConfigList();
  }

  public void importSingle (ActionEvent action)
  {
    String file = gui.configList.getItem(0);
    Import.importSingle(file);
    gui.setState(file + " was imported.");
    updateConfigList();
  }

  public void importAll (ActionEvent action)
  {
    int nFiles = Import.importAll();
    gui.setState(String.format("%s configs were imported.", nFiles));
    updateConfigList();
  }

  private final DatdecGUI gui;
}
