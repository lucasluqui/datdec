package com.lucasallegri.ooo;

	/**
	 * 	Based on Fission Mailed#4370's work.
	 * 	(Discord ID: 342110230861316097)
	 */

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import com.threerings.export.b; 			// BinaryImporter
import com.threerings.export.aC; 			// XMLExporter

public class OOOExporter {
	
    public static void export(FileInputStream source, OutputStream dest) throws IOException {
        b in = new b(source);
        aC out = new aC(dest);
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
