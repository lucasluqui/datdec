package com.lucasallegri.datdec.ooo;

	/**
	 * 	Based on Fission Mailed#4370's work.
	 * 	(Discord ID: 342110230861316097)
	 */

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import com.threerings.export.XMLImporter;
import com.threerings.export.a; 			// BinaryExporter

public class OOOImporter {

    public static void _import(FileInputStream source, OutputStream dest) throws IOException {
        XMLImporter in = new XMLImporter(source);
        a out = new a(dest);
        Object o = null;
    	while (true) {
    		try {
                o = in.readObject();
    		} catch (Exception e) {
                in.close();
                out.close();
                return;
    		}
    		System.out.println("read=" + o.getClass().toString());
    		out.writeObject(o);
    	}
	}
}
