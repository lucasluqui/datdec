package com.lucasallegri.datdec.cli;

import com.lucasallegri.datdec.DatdecConstants;
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
     * @param args
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
        if (cmd.hasOption("h")) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("java -jar datdec.jar [options]", options);
            System.exit(0);
        }

        if (cmd.hasOption("d")) {
            String[] files = cmd.getOptionValues("d");
            for (String file : files) {

            }
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
                .desc("List of .dat files to decompile")
                .hasArgs()
                .required(false)
                .argName("dat files")
                .get();
        options.addOption(decompile);

        // List of files to compile
        Option compile = Option.builder("c")
                .longOpt("compile")
                .desc("List of .xml files to compile")
                .hasArgs()
                .required(false)
                .argName("file paths")
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
