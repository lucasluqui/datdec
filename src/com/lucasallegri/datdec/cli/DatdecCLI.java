package com.lucasallegri.datdec.cli;

import com.lucasallegri.datdec.DatdecConstants;
import com.lucasallegri.datdec.compile.Compile;
import com.lucasallegri.datdec.decompile.Decompile;
import com.lucasallegri.datdec.gui.DatdecContext;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.HelpFormatter;

import static com.lucasallegri.datdec.gui.DatdecGUI.startGUI;

public class DatdecCLI {

    /**
     * Main method of the application. Decides whether GUI or CLI should be used.
     */
    public static void main(String[] args) {
        Options options = createOptions();
        CommandLineParser parser = new DefaultParser();

        try {
            CommandLine cmd = parser.parse(options,args);

            // Start GUI if no CLI option gets used
            boolean cliUsed = options.getOptions().stream()
                    .anyMatch(o -> cmd.hasOption(o.getOpt()));

            if (cliUsed) {
                System.out.printf("datdec %s\n",DatdecConstants.version);
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
    private static void processOptions(CommandLine cmd, Options options) {

        // Help option
        if (cmd.hasOption("h")) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("java -jar datdec.jar [options]", options);
            System.exit(0);
        }

        // Use old class mappings
        if (cmd.hasOption("m")) {
            DatdecContext.useOldClassMappings = true;
        }

        // Decompile files specified by the argument
        // if no files given, decompile all under rsrc/config.
        if (cmd.hasOption("d")) {
            String[] configs = cmd.getOptionValues("d");
            int nConfigs;
            if (configs != null && configs.length > 0) {
                nConfigs = 0;
                for (String config : configs) {
                    if (!config.contains(".dat")) {
                        System.out.printf("Warning: %s cannot be decompiled, is not dat.\n",config);
                        continue;
                    }
                    DatdecContext.selectedConfig = config;
                    Decompile.decompile();
                    nConfigs++;
                }
            } else {
                nConfigs = Decompile.decompileAll();
            }
            System.out.printf("%s configs were decompiled.", nConfigs);
        }

        // Create file backup when compiling option
        if (cmd.hasOption("b")) {
            DatdecContext.doBackups = true;
        }

        // Compile files specified by the argument
        // if no files given, compile all under rsrc/config.
        if (cmd.hasOption("c")) {
            String[] configs = cmd.getOptionValues("c");
            int nConfigs;
            if (configs.length > 0) {
                nConfigs = 0;
                for (String config : configs) {
                    if (!config.contains(".xml")){
                        System.out.printf("Warning: %s cannot be compiled, is not xml.\n",config);
                        continue;
                    }
                    DatdecContext.selectedConfig = config;
                    Compile.compile();
                    nConfigs++;
                }
            } else {
                nConfigs = Compile.compileAll();
            }
            System.out.printf("%s configs were decompiled.", nConfigs);
        }
    }

    /**
     * Creates options for command line interface.
     * @return Options object
     */
    private static Options createOptions() {
        Options options = new Options();

        Option help = Option.builder("h")
                .longOpt("help")
                .desc("Shows help message")
                .hasArg(false)
                .required(false)
                .get();
        options.addOption(help);

        // List of files to decompile
        Option decompile = Option.builder("d")
                .longOpt("decompile")
                .desc("List of .dat files to decompile. Not full paths. If no files are given, will decompile everything under rsrc/config.")
                .hasArgs()
                .optionalArg(true)
                .required(false)
                .argName("file names")
                .get();
        options.addOption(decompile);

        // List of files to compile
        Option compile = Option.builder("c")
                .longOpt("compile")
                .desc("List of .xml files to compile. Not full paths. If no files are given, will compile everything under rsrc/config.")
                .hasArgs()
                .optionalArg(true)
                .required(false)
                .argName("file names, not")
                .get();
        options.addOption(compile);

        // Switch to back-up files when compiling them from xml
        Option backups = Option.builder("b")
                .longOpt("backups")
                .desc("Back-up xml files when compiling.")
                .hasArg(false)
                .required(false)
                .get();
        options.addOption(backups);

        // Switch to use old class mappings
        Option mappings = Option.builder("m")
                .longOpt("old-mappings")
                .desc("Use old class mappings")
                .hasArg(false)
                .required(false)
                .get();
        options.addOption(mappings);

        return options;
    }
}
