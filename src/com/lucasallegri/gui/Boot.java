package com.lucasallegri.gui;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;

import com.lucasallegri.util.ConfigChoice;

public class Boot {
	
	public static void onBoot() {
		// setupLookAndFeel();
		ConfigChoice.populate();
	}
	
	private static void setupLookAndFeel() {
		
		for(LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
			if("Windows".equals(info.getName())) {
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
