package com.lucasluqui.datdec;

import com.lucasluqui.datdec.cli.DatdecCLI;
import com.lucasluqui.datdec.gui.DatdecGUI;
import org.apache.commons.cli.*;

public class DatdecApp
{
  static void main (String[] args)
  {
    if (args.length == 0) {
      DatdecGUI.init();
    } else {
      DatdecCLI.main(args);
    }
  }
}
