package com.lucasluqui.datdec.gui;

import com.lucasluqui.datdec.BuildConfig;
import com.lucasluqui.datdec.DatdecSettings;

import javax.swing.*;
import java.awt.*;

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
    guiFrame.setTitle("Datdec");
    guiFrame.setBounds(100, 100, 325, 300);
    guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    guiFrame.getContentPane().setLayout(null);

    JLabel lblSelectConfigFile = new JLabel("Select config file");
    lblSelectConfigFile.setHorizontalAlignment(SwingConstants.CENTER);
    lblSelectConfigFile.setBounds(10, 13, 280, 14);
    guiFrame.getContentPane().add(lblSelectConfigFile);

    configList = new Choice();
    configList.setBounds(96, 33, 110, 20);
    configList.addItemListener(event -> eventHandler.configChoiceChanged(event));
    guiFrame.getContentPane().add(configList);

    configCountLabel = new JLabel("(...)");
    configCountLabel.setBounds(212, 36, 74, 14);
    guiFrame.getContentPane().add(configCountLabel);

    exportButton = new JButton("Export");
    exportButton.setEnabled(false);
    exportButton.addActionListener(action -> eventHandler.exportSingle(action));
    exportButton.setBounds(40, 90, 110, 23);
    guiFrame.getContentPane().add(exportButton);

    exportAllButton = new JButton("Export All");
    exportAllButton.setEnabled(false);
    exportAllButton.addActionListener(action -> eventHandler.exportAll(action));
    exportAllButton.setBounds(160, 90, 110, 23);
    guiFrame.getContentPane().add(exportAllButton);

    importButton = new JButton("Import");
    importButton.setEnabled(false);
    importButton.addActionListener(action -> eventHandler.importSingle(action));
    importButton.setBounds(40, 124, 110, 23);
    guiFrame.getContentPane().add(importButton);

    importAllButton = new JButton("Import All");
    importAllButton.setEnabled(false);
    importAllButton.setBounds(160, 124, 110, 23);
    importAllButton.addActionListener(action -> eventHandler.importAll(action));
    guiFrame.getContentPane().add(importAllButton);

    JCheckBox checkboxMakeBackups = new JCheckBox("Make backups when exporting");
    checkboxMakeBackups.setHorizontalAlignment(SwingConstants.CENTER);
    checkboxMakeBackups.addActionListener(action -> DatdecSettings.doBackups = checkboxMakeBackups.isSelected());
    checkboxMakeBackups.setBounds(40, 194, 230, 23);
    guiFrame.getContentPane().add(checkboxMakeBackups);

    labelState = new JLabel("");
    labelState.setBounds(10, 236, 217, 14);
    guiFrame.getContentPane().add(labelState);

    JLabel labelVersion = new JLabel("v" + BuildConfig.getVersion());
    labelVersion.setHorizontalAlignment(SwingConstants.RIGHT);
    labelVersion.setBounds(237, 236, 62, 14);
    guiFrame.getContentPane().add(labelVersion);

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

  public void onBoot ()
  {
    eventHandler.updateConfigList();
  }

  protected void setState (String state)
  {
    labelState.setText("State: " + state);
  }

  private final DatdecEventHandler eventHandler;
  private JFrame guiFrame;
  protected Choice configList;
  protected JLabel labelState;
  protected JLabel configCountLabel;
  protected JButton exportButton;
  protected JButton exportAllButton;
  protected JButton importButton;
  protected JButton importAllButton;
}
