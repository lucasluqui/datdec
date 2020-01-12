package com.lucasallegri.gui;

import com.lucasallegri.util.ConfigChoice;

public class Boot {
	
	public static void onBoot() {
		ConfigChoice.populate();
	}
}
