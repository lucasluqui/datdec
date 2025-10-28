package com.lucasluqui.datdec.gui;

import com.lucasluqui.datdec.BuildConfig;
import com.lucasluqui.datdec.DatdecSettings;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class DatdecGUI
{
  /**
   * Launch the application GUI.
   */
  public static void startGUI ()
  {
    EventQueue.invokeLater(() -> {
      try {
        DatdecGUI window = new DatdecGUI();
        window.guiFrame.setVisible(true);
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
  }

  /**
   * Create the application.
   */
  public DatdecGUI ()
  {
    setupLookAndFeel();
    this.eventHandler = new DatdecEventHandler(this);
    initialize();
  }

  /**
   * Initialize the contents of the frame.
   */
  private void initialize ()
  {

    guiFrame = new JFrame();
    guiFrame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 11));
    guiFrame.setResizable(false);
    guiFrame.setLocationRelativeTo(null);
    guiFrame.setTitle("datdec (" + BuildConfig.getVersion() + ")");
    guiFrame.setBounds(100, 100, 325, 300);
    guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    guiFrame.getContentPane().setLayout(null);

    fileChooser = new JFileChooser();
    fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir") + "/rsrc"));
    fileChooser.setDialogTitle("Select config");
    fileChooser.setApproveButtonText("Select");
    FileNameExtensionFilter restrict = new FileNameExtensionFilter(".dat, .xml", "dat", "xml");
    fileChooser.setAcceptAllFileFilterUsed(false);
    fileChooser.addChoosableFileFilter(restrict);

    selectConfigButton = new JButton();
    selectConfigButton.setText("Select...");
    selectConfigButton.setBounds(95, 25, 120, 25);
    selectConfigButton.addActionListener(action -> {
      int result = fileChooser.showOpenDialog(null);
      if (result == JFileChooser.APPROVE_OPTION) {
        File file = fileChooser.getSelectedFile();
        String fileName = file.getName();
        if (fileName.endsWith(".dat") || fileName.endsWith(".xml")) {
          selectedFile = file;
          eventHandler.selectedConfigChanged(fileName);
        } else {
          System.out.println("Tried to select invalid file. " + file.getPath());
        }
      }
    });
    guiFrame.getContentPane().add(selectConfigButton);

    labelSelectedConfigName = new JLabel("Selected: None.");
    labelSelectedConfigName.setHorizontalAlignment(SwingConstants.CENTER);
    labelSelectedConfigName.setBounds(0, 55, 310, 15);
    guiFrame.getContentPane().add(labelSelectedConfigName);

    exportButton = new JButton("Export");
    exportButton.setEnabled(false);
    exportButton.addActionListener(eventHandler::exportSingle);
    exportButton.setBounds(40, 90, 110, 25);
    guiFrame.getContentPane().add(exportButton);

    importButton = new JButton("Import");
    importButton.setEnabled(false);
    importButton.addActionListener(eventHandler::importSingle);
    importButton.setBounds(160, 90, 110, 25);
    guiFrame.getContentPane().add(importButton);

    JSeparator separator = new JSeparator();
    separator.setBounds(25, 126, 255, 5);
    guiFrame.getContentPane().add(separator);

    exportAllButton = new JButton("Export All Configs");
    exportAllButton.setEnabled(false);
    exportAllButton.addActionListener(eventHandler::exportAll);
    exportAllButton.setBounds(89, 140, 130, 25);
    guiFrame.getContentPane().add(exportAllButton);

    importAllButton = new JButton("Import All Configs");
    importAllButton.setEnabled(false);
    importAllButton.setBounds(89, 175, 130, 25);
    importAllButton.addActionListener(eventHandler::importAll);
    guiFrame.getContentPane().add(importAllButton);

    JCheckBox checkboxMakeBackups = new JCheckBox("Make back-ups when importing");
    checkboxMakeBackups.setHorizontalAlignment(SwingConstants.CENTER);
    checkboxMakeBackups.addActionListener(action -> DatdecSettings.doBackups = checkboxMakeBackups.isSelected());
    checkboxMakeBackups.setBounds(40, 210, 230, 25);
    guiFrame.getContentPane().add(checkboxMakeBackups);

    labelState = new JLabel("");
    labelState.setBounds(10, 236, 217, 14);
    guiFrame.getContentPane().add(labelState);

    onBoot();
  }

  private void setupLookAndFeel ()
  {
    for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
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

  private void onBoot ()
  {
    eventHandler.updateConfigList();
  }

  protected void setState (String state)
  {
    labelState.setText(state);
  }

  private final DatdecEventHandler eventHandler;

  private JFrame guiFrame;
  protected JLabel labelState;
  protected JLabel labelSelectedConfigName;
  protected JButton selectConfigButton;
  protected JButton exportButton;
  protected JButton exportAllButton;
  protected JButton importButton;
  protected JButton importAllButton;

  protected JFileChooser fileChooser;
  protected File selectedFile = null;
}
