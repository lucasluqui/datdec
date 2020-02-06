package com.lucasallegri.ooo;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import com.threerings.export.b; 			// BinaryImporter
import com.threerings.export.ax; 			// XMLExporter

public class LegacyOOOExporter {
	
    public static void export(FileInputStream source, OutputStream dest) throws IOException {
        b in = new b(source);
        ax out = new ax(dest);    
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