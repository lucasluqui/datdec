package com.lucasluqui.datdec.gui;

import com.lucasluqui.datdec.export.Import;
import com.lucasluqui.datdec.export.Export;
import com.lucasluqui.datdec.util.FileUtil;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;

public class DatdecEventHandler
{
  public DatdecEventHandler (DatdecGUI gui)
  {
    this.gui = gui;
  }

  public void updateConfigList ()
  {
    List<String> fileNames = FileUtil.fileNamesInDirectory("rsrc/config/");
    for (String fileName : fileNames) {
      if (fileName.endsWith(".dat")) {
        gui.exportAllButton.setEnabled(true);
      } else if (fileName.endsWith(".xml")) {
        gui.importAllButton.setEnabled(true);
      }
    }
  }

  public void selectedConfigChanged (String file)
  {
    gui.importButton.setEnabled(!file.endsWith(".dat"));
    gui.exportButton.setEnabled(file.endsWith(".dat"));
    gui.labelSelectedConfigName.setText("Selected: " + file + ".");
  }

  public void exportSingle (ActionEvent action)
  {
    File file = gui.selectedFile;
    if (file != null) {
      Export.exportSingle(file);
      gui.setState(file.getName() + " was exported.");
      updateConfigList();
    }
  }

  public void exportAll (ActionEvent action)
  {
    int nFiles = Export.exportAll();
    gui.setState(String.format("%s configs were exported.", nFiles));
    updateConfigList();
  }

  public void importSingle (ActionEvent action)
  {
    File file = gui.selectedFile;
    if (file != null) {
      Import.importSingle(file);
      gui.setState(file.getName() + " was imported.");
      updateConfigList();
    }
  }

  public void importAll (ActionEvent action)
  {
    int nFiles = Import.importAll();
    gui.setState(String.format("%s configs were imported.", nFiles));
    updateConfigList();
  }

  private final DatdecGUI gui;
}
