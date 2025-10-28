package com.lucasluqui.datdec.gui;

import com.lucasluqui.datdec.gui.util.ConfigChoice;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;

public class Boot
{
  public static void onBoot ()
  {
    // setupLookAndFeel();
    ConfigChoice.populate();
  }

  private static void setupLookAndFeel ()
  {

    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
      if ("Windows".equals(info.getName())) {
        try {
          UIManager.setLookAndFeel(info.getClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                 | UnsupportedLookAndFeelException e) {
          e.printStackTrace();
        }
      }
    }

  }

}
