package com.lucasallegri;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainUI extends javax.swing.JFrame {
    
    public MainUI() {
        initComponents();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        decompileButton = new javax.swing.JButton();
        compileButton = new javax.swing.JButton();
        pathInput = new javax.swing.JTextField();
        pathInputLabel = new javax.swing.JLabel();
        proTip = new javax.swing.JLabel();
        proTipTwo = new javax.swing.JLabel();
        proTipThree = new javax.swing.JLabel();
        proTipExample = new javax.swing.JLabel();
        fileNameCheckBox = new javax.swing.JCheckBox();
        rsrcConfigPathInput = new javax.swing.JTextField();
        rsrcConfigInputLabel = new javax.swing.JLabel();
        fileNameInputLabel = new javax.swing.JLabel();
        fileNameInput = new javax.swing.JTextField();
        statusLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("datdec");
        setResizable(false);

        decompileButton.setText("Decompile");
        decompileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                decompileButtonActionPerformed(evt);
            }
        });

        compileButton.setText("Compile");
        compileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                compileButtonActionPerformed(evt);
            }
        });

        pathInputLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        pathInputLabel.setText("Path to file/files");

        proTip.setBackground(new java.awt.Color(0, 0, 0));
        proTip.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        proTip.setForeground(new java.awt.Color(255, 0, 51));
        proTip.setText("Pro-Tip:");

        proTipTwo.setText("You can use semicolons (;) to compile/decompile");

        proTipThree.setText("various files at once.");

        proTipExample.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        proTipExample.setText("Example: C:\\actor.dat;C:\\item.dat or actor.dat;item.dat");

        fileNameCheckBox.setText("Use .dat or .xml file names instead of full path (configs only)");
        fileNameCheckBox.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                fileNameCheckBoxStateChanged(evt);
            }
        });

        rsrcConfigPathInput.setEnabled(false);

        rsrcConfigInputLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        rsrcConfigInputLabel.setText("Path to rsrc\\config");

        fileNameInputLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        fileNameInputLabel.setText(".dat or .xml file/files");

        fileNameInput.setEnabled(false);

        statusLabel.setText("Status: Idle.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pathInputLabel)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(compileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                        .addComponent(decompileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pathInput, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)
                    .addComponent(rsrcConfigInputLabel)
                    .addComponent(rsrcConfigPathInput, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)
                    .addComponent(fileNameInputLabel)
                    .addComponent(fileNameInput, javax.swing.GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)
                    .addComponent(fileNameCheckBox)
                    .addComponent(proTip)
                    .addComponent(proTipTwo)
                    .addComponent(proTipThree)
                    .addComponent(proTipExample)
                    .addComponent(statusLabel))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pathInputLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pathInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addComponent(rsrcConfigInputLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rsrcConfigPathInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(fileNameInputLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(fileNameInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(fileNameCheckBox)
                .addGap(18, 18, 18)
                .addComponent(proTip)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(proTipTwo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(proTipThree)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(proTipExample)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addComponent(statusLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(compileButton)
                    .addComponent(decompileButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void decompileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_decompileButtonActionPerformed
        if(!fileNameCheckBox.isSelected()){
            String[] paths = pathInput.getText().split(";");
            for (int i=0; i < paths.length; i++){
                File source = new File(paths[i]);
                if(source != null)
                {
                    FileInputStream in = null;
                    try {
                        String path = source.getAbsolutePath();
                        in = new FileInputStream(source);
                        String dest = path.replaceFirst("\\.dat$", ".xml");
                        FileOutputStream out = new FileOutputStream(dest);
                        ConversionComponent.decompileBinaryToXML(in, out);
                        statusLabel.setText("Status: Success!");
                    } catch (IOException ex) {
                        statusLabel.setText("Status: Error! Something went wrong.");
                        Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        try {
                            in.close();
                        } catch (IOException ex) {
                            Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }else{
            String rsrcConfigPath = rsrcConfigPathInput.getText();
            String[] fileNames = fileNameInput.getText().split(";");
            for (int i=0; i < fileNames.length; i++){
                File source = new File(rsrcConfigPath+File.separator+fileNames[i]);
                if(source != null)
                {
                    FileInputStream in = null;
                    try {
                        String path = source.getAbsolutePath();
                        in = new FileInputStream(source);
                        String dest = path.replaceFirst("\\.dat$", ".xml");
                        FileOutputStream out = new FileOutputStream(dest);
                        ConversionComponent.decompileBinaryToXML(in, out);
                        statusLabel.setText("Status: Success!");
                    } catch (IOException ex) {
                        statusLabel.setText("Status: Error! Something went wrong.");
                        Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        try {
                            in.close();
                        } catch (IOException ex) {
                            Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }
}//GEN-LAST:event_decompileButtonActionPerformed

    private void compileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_compileButtonActionPerformed
        if(!fileNameCheckBox.isSelected()){
            String[] paths = pathInput.getText().split(";");
            for (int i=0; i < paths.length; i++){
                File source = new File(paths[i]);
                if(source != null)
                {
                    FileInputStream in = null;
                    try {
                        String path = source.getAbsolutePath();
                        in = new FileInputStream(source);
                        String dest = path.replaceFirst("\\.xml$", ".dat");
                        FileOutputStream out = new FileOutputStream(dest);
                        ConversionComponent.compileXMLToBinary(in, out);
                        statusLabel.setText("Status: Success!");
                    } catch (IOException ex) {
                        statusLabel.setText("Status: Error! Something went wrong.");
                        Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        try {
                            in.close();
                        } catch (IOException ex) {
                            Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }else{
            String rsrcConfigPath = rsrcConfigPathInput.getText();
            String[] fileNames = fileNameInput.getText().split(";");
            for (int i=0; i < fileNames.length; i++){
                File source = new File(rsrcConfigPath+File.separator+fileNames[i]);
                if(source != null)
                {
                    FileInputStream in = null;
                    try {
                        String path = source.getAbsolutePath();
                        in = new FileInputStream(source);
                        String dest = path.replaceFirst("\\.xml$", ".dat");
                        FileOutputStream out = new FileOutputStream(dest);
                        ConversionComponent.compileXMLToBinary(in, out);
                        statusLabel.setText("Status: Success!");
                    } catch (IOException ex) {
                        statusLabel.setText("Status: Error! Something went wrong.");
                        Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        try {
                            in.close();
                        } catch (IOException ex) {
                            Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }
    }//GEN-LAST:event_compileButtonActionPerformed

    private void fileNameCheckBoxStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_fileNameCheckBoxStateChanged
        if(fileNameCheckBox.isSelected()){
            pathInput.setEnabled(false);
            rsrcConfigPathInput.setEnabled(true);
            fileNameInput.setEnabled(true);
        }else{
            pathInput.setEnabled(true);
            rsrcConfigPathInput.setEnabled(false);
            fileNameInput.setEnabled(false);
        }
    }//GEN-LAST:event_fileNameCheckBoxStateChanged
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]){
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainUI().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton compileButton;
    private javax.swing.JButton decompileButton;
    private javax.swing.JCheckBox fileNameCheckBox;
    private javax.swing.JTextField fileNameInput;
    private javax.swing.JLabel fileNameInputLabel;
    private javax.swing.JTextField pathInput;
    private javax.swing.JLabel pathInputLabel;
    private javax.swing.JLabel proTip;
    private javax.swing.JLabel proTipExample;
    private javax.swing.JLabel proTipThree;
    private javax.swing.JLabel proTipTwo;
    private javax.swing.JLabel rsrcConfigInputLabel;
    private javax.swing.JTextField rsrcConfigPathInput;
    private javax.swing.JLabel statusLabel;
    // End of variables declaration//GEN-END:variables
    
}
