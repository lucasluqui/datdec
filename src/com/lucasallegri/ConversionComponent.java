package com.lucasallegri;

// Based on Fission Mailed#4370's (ID: 342110230861316097)work.

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import com.threerings.export.aA; // XMLExporter
import com.threerings.export.XMLImporter; // XMLImporter
import com.threerings.export.a; // BinaryExporter
import com.threerings.export.b; // BinaryImporter

public class ConversionComponent {
	
    public static void decompileBinaryToXML(FileInputStream source, OutputStream dest) throws IOException {
        b in = new b(source);
        aA out = new aA(dest);
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

    public static void compileXMLToBinary(FileInputStream source, OutputStream dest) throws IOException {
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
