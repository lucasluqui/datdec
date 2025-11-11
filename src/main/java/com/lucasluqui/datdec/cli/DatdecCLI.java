package com.lucasluqui.datdec.cli;

import com.lucasluqui.datdec.BuildConfig;
import com.lucasluqui.datdec.export.Import;
import com.lucasluqui.datdec.export.Export;
import com.lucasluqui.datdec.DatdecSettings;
import org.apache.commons.cli.*;

import java.io.File;

import static com.lucasluqui.datdec.gui.DatdecGUI.startGUI;

public class DatdecCLI
{
  /**
   * Main method of the application. Decides whether GUI or CLI should be used.
   */
  public static void main (String[] args)
  {
    Options options = createOptions();
    CommandLineParser parser = new DefaultParser();

    try {
      CommandLine cmd = parser.parse(options, args);

      // Start GUI if no CLI option gets used
      boolean cliUsed = options.getOptions().stream()
        .anyMatch(o -> cmd.hasOption(o.getOpt()));

      if (cliUsed) {
        System.out.printf("datdec %s\n", BuildConfig.getVersion());
        processOptions(cmd, options);
      } else {
        startGUI();
      }

    } catch (ParseException e) {
      System.err.println("Error: " + e.getMessage());
      new HelpFormatter().printHelp("java -jar datdec.jar [options]", options);
      System.exit(1);
    }
  }

  /**
   * Process command line options. Entry point for CLI.
   */
  private static void processOptions (CommandLine cmd, Options options)
  {

    // Help option
    if (cmd.hasOption("h")) {
      HelpFormatter formatter = new HelpFormatter();
      formatter.printHelp("java -jar datdec.jar [options]", options);
      System.exit(0);
    }

    // Export files specified by the argument
    // if no files given, export all under rsrc/config.
    if (cmd.hasOption("e")) {
      String[] configs = cmd.getOptionValues("e");
      int nConfigs;
      if (configs != null && configs.length > 0) {
        nConfigs = 0;
        for (String config : configs) {
          if (!config.contains(".dat")) {
            System.out.printf("Warning: %s cannot be exported, is not .dat.\n", config);
            continue;
          }
          Export.exportSingle(new File(config));
          nConfigs++;
        }
      } else {
        nConfigs = Export.exportAll();
      }
      System.out.printf("%s configs were exported.", nConfigs);
    }

    // Create file backup
    if (cmd.hasOption("b")) {
      DatdecSettings.doBackups = true;
    }

    // Import files specified by the argument
    // if no files given, import all under rsrc/config.
    if (cmd.hasOption("i")) {
      String[] configs = cmd.getOptionValues("i");
      int nConfigs;
      if (configs.length > 0) {
        nConfigs = 0;
        for (String config : configs) {
          if (!config.contains(".xml")) {
            System.out.printf("Warning: %s cannot be imported, is not .xml.\n", config);
            continue;
          }
          Import.importSingle(new File(config));
          nConfigs++;
        }
      } else {
        nConfigs = Import.importAll();
      }
      System.out.printf("%s configs were imported.", nConfigs);
    }
  }

  /**
   * Creates options for command line interface.
   *
   * @return Options object
   */
  private static Options createOptions ()
  {
    Options options = new Options();

    Option help = Option.builder("h")
      .longOpt("help")
      .desc("Shows help message")
      .hasArg(false)
      .required(false)
      .get();
    options.addOption(help);

    // List of files to decompile
    Option decompile = Option.builder("e")
      .longOpt("export")
      .desc("List of .dat files to export. Not full paths. If no files are given, will export everything under rsrc/config.")
      .hasArgs()
      .optionalArg(true)
      .required(false)
      .argName("file names")
      .get();
    options.addOption(decompile);

    // List of files to compile
    Option compile = Option.builder("i")
      .longOpt("import")
      .desc("List of .xml files to import. Not full paths. If no files are given, will import everything under rsrc/config.")
      .hasArgs()
      .optionalArg(true)
      .required(false)
      .argName("file names")
      .get();
    options.addOption(compile);

    // Switch to back-up existing .dat files when importing them from .xml
    Option backups = Option.builder("b")
      .longOpt("backups")
      .desc("Back-up existing .dat when importing, so that the result doesn't overwrite them.")
      .hasArg(false)
      .required(false)
      .get();
    options.addOption(backups);

    return options;
  }
}
