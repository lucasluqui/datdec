package com.lucasallegri.datdec.gui;

import com.lucasallegri.datdec.DatdecConstants;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Choice;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.io.InputStream;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.FontFormatException;
import javax.swing.JCheckBox;

public class DatdecGUI {

	private JFrame frmDatdecGUI;
	public static Choice configList;
	public static JLabel stateLabel;
	public static JLabel configCountLabel;
	public static JButton decompileButton;
	public static JButton decompileAllButton;
	public static JButton compileButton;
	public static JButton compileAllButton;

	/**
	 * Launch the application GUI.
	 */
	public static void startGUI() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DatdecGUI window = new DatdecGUI();
					window.frmDatdecGUI.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public DatdecGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		InputStream fontRegIs = DatdecGUI.class.getResourceAsStream("/fonts/GoogleSans-Regular.ttf");
		InputStream fontMedIs = DatdecGUI.class.getResourceAsStream("/fonts/GoogleSans-Medium.ttf");
		Font fontReg = null;
		Font fontMed = null;
		try {
			fontReg = Font.createFont(Font.TRUETYPE_FONT, fontRegIs);
			fontMed = Font.createFont(Font.TRUETYPE_FONT, fontMedIs);
			fontReg = fontReg.deriveFont(11.0f);
			fontReg = fontReg.deriveFont(Font.ITALIC);
			fontMed = fontMed.deriveFont(11.0f);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		frmDatdecGUI = new JFrame();
		frmDatdecGUI.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 11));
		frmDatdecGUI.setResizable(false);
		frmDatdecGUI.setLocationRelativeTo(null);
		frmDatdecGUI.setTitle("Datdec GUI");
		frmDatdecGUI.setBounds(100, 100, 325, 300);
		frmDatdecGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDatdecGUI.getContentPane().setLayout(null);
		
		JLabel lblSelectConfigFile = new JLabel("Select config file");
		lblSelectConfigFile.setFont(fontMed);
		lblSelectConfigFile.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelectConfigFile.setBounds(10, 13, 280, 14);
		frmDatdecGUI.getContentPane().add(lblSelectConfigFile);
		
		configList = new Choice();
		configList.setBounds(96, 33, 110, 20);
		configList.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent _event) {
				EventController.configChoiceChanged(_event);
			}
		});
		frmDatdecGUI.getContentPane().add(configList);
		
		configCountLabel = new JLabel("(...)");
		configCountLabel.setFont(fontReg);
		configCountLabel.setBounds(212, 36, 74, 14);
		frmDatdecGUI.getContentPane().add(configCountLabel);
		
		decompileButton = new JButton("Decompile");
		decompileButton.setEnabled(false);
		decompileButton.setFont(fontMed);
		decompileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent _action) {
				EventController.decompile(_action);
			}
		});
		decompileButton.setBounds(40, 90, 110, 23);
		frmDatdecGUI.getContentPane().add(decompileButton);
		
		decompileAllButton = new JButton("Decompile All");
		decompileAllButton.setEnabled(false);
		decompileAllButton.setFont(fontMed);
		decompileAllButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent _action) {
				EventController.decompileAll(_action);
			}
		});
		decompileAllButton.setBounds(160, 90, 110, 23);
		frmDatdecGUI.getContentPane().add(decompileAllButton);
		
		compileButton = new JButton("Compile");
		compileButton.setEnabled(false);
		compileButton.setFont(fontMed);
		compileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent _action) {
				EventController.compile(_action);
			}
		});
		compileButton.setBounds(40, 124, 110, 23);
		frmDatdecGUI.getContentPane().add(compileButton);
		
		compileAllButton = new JButton("Compile All");
		compileAllButton.setEnabled(false);
		compileAllButton.setFont(fontMed);
		compileAllButton.setBounds(160, 124, 110, 23);
		compileAllButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent _action) {
				EventController.compileAll(_action);
			}
		});
		frmDatdecGUI.getContentPane().add(compileAllButton);
		
		JCheckBox checkboxMakeBackups = new JCheckBox("Make backups when compiling");
		checkboxMakeBackups.setHorizontalAlignment(SwingConstants.CENTER);
		checkboxMakeBackups.setFont(fontReg);
		checkboxMakeBackups.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent _action) {
				DatdecContext.doBackups = checkboxMakeBackups.isSelected() ? true : false;
			}
		});
		checkboxMakeBackups.setBounds(40, 194, 230, 23);
		frmDatdecGUI.getContentPane().add(checkboxMakeBackups);
		
		stateLabel = new JLabel("State: Awaiting interaction.");
		stateLabel.setFont(fontReg);
		stateLabel.setBounds(10, 236, 217, 14);
		frmDatdecGUI.getContentPane().add(stateLabel);
		
		JLabel lblVersion = new JLabel("v." + DatdecConstants.version);
		lblVersion.setFont(fontReg);
		lblVersion.setHorizontalAlignment(SwingConstants.RIGHT);
		lblVersion.setBounds(237, 236, 62, 14);
		frmDatdecGUI.getContentPane().add(lblVersion);
		
		JCheckBox checkboxUseOldMappings = new JCheckBox("Use old class mappings (older versions)");
		checkboxUseOldMappings.setHorizontalAlignment(SwingConstants.CENTER);
		checkboxUseOldMappings.setFont(fontReg);
		checkboxUseOldMappings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent _action) {
				DatdecContext.useOldClassMappings = checkboxUseOldMappings.isSelected() ? true : false;
			}
		});
		checkboxUseOldMappings.setBounds(40, 168, 230, 23);
		frmDatdecGUI.getContentPane().add(checkboxUseOldMappings);
		
		Boot.onBoot();
		
	}
	
	public static void pushState(String state) {
		stateLabel.setText("State: " + state);
	}
}
