package net.sourceforge.obexftpfrontend.gui;

import java.awt.Window;
import javax.swing.JButton;
import javax.swing.JTextField;
import net.sourceforge.obexftpfrontend.command.OBEXFTPCommandQueue;
import net.sourceforge.obexftpfrontend.model.ConfigurationHolder;
import net.sourceforge.obexftpfrontend.obexftp.DevInfoFileParser;

/**
 * Device information frame.
 * @author Daniel F. Martins
 */
public class DeviceInfoDialog extends javax.swing.JDialog {

    /** UI helper. */
    private DeviceInfoDialogHelper helper;

    /**
     * Create a new instance of DeviceInfoDialog.
     * @param parent Parent frame.
     */
    public DeviceInfoDialog(Window parent, ConfigurationHolder configHolder, OBEXFTPCommandQueue queue, DevInfoFileParser devInfoParser) {
        super(parent, ModalityType.MODELESS);

        helper = new DeviceInfoDialogHelper(this, configHolder, queue, devInfoParser);
        initComponents();

        setLocationRelativeTo(getOwner());
    }

    // <editor-fold defaultstate="collapsed" desc=" Overriden methods ">
    @Override
    public void setVisible(boolean b) {
        helper.prepareWindow();
        super.setVisible(b);
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc=" Getter methods ">
    /**
     * Get the okButton object.  This method should be
     * used only by the ViewHelper object.
     * @return The okButton object.
     */
    protected JButton getOkButton() {
        return okButton;
    }

    /**
     * Get the refreshButton object.  This method should be
     * used only by the ViewHelper object.
     * @return The refreshButton object.
     */
    protected JButton getRefreshButton() {
        return refreshButton;
    }

    /**
     * Get the manufacturerTextField object.  This method should be
     * used only by the ViewHelper object.
     * @return The manufacturerTextField object.
     */
    protected JTextField getManufacturerTextField() {
        return manufacturerTextField;
    }

    /**
     * Get the modelTextField object.  This method should be
     * used only by the ViewHelper object.
     * @return The modelTextField object.
     */
    protected JTextField getModelTextField() {
        return modelTextField;
    }

    /**
     * Get the firmwareVersionTextField object.  This method should be
     * used only by the ViewHelper object.
     * @return The firmwareVersionTextField object.
     */
    protected JTextField getFirmwareVersionTextField() {
        return firmwareVersionTextField;
    }

    /**
     * Get the hardwareVersionTextField object.  This method should be
     * used only by the ViewHelper object.
     * @return The hardwareVersionTextField object.
     */
    protected JTextField getHardwareVersionTextField() {
        return hardwareVersionTextField;
    }

    /**
     * Get the serialNumberTextField object.  This method should be
     * used only by the ViewHelper object.
     * @return The serialNumberTextField object.
     */
    protected JTextField getSerialNumberTextField() {
        return serialNumberTextField;
    }

    /**
     * Get the firmwareDateTextField object.  This method should be
     * used only by the ViewHelper object.
     * @return The firmwareDateTextField object.
     */
    protected JTextField getFirmwareDateTextField() {
        return firmwareDateTextField;
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        descriptionLabel = new javax.swing.JLabel();
        okButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        firmwareVersionTextField = new javax.swing.JTextField();
        hardwareVersionTextField = new javax.swing.JTextField();
        serialNumberTextField = new javax.swing.JTextField();
        modelTextField = new javax.swing.JTextField();
        manufacturerTextField = new javax.swing.JTextField();
        refreshButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        firmwareDateTextField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Device info");

        descriptionLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/top-description.png"))); // NOI18N
        descriptionLabel.setText("Some information about the connected device...");
        descriptionLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));
        descriptionLabel.setPreferredSize(new java.awt.Dimension(303, 26));

        okButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/ok.png"))); // NOI18N
        okButton.setMnemonic('o');
        okButton.setText("OK");
        okButton.setToolTipText("Close this dialog");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        jLabel1.setDisplayedMnemonic('m');
        jLabel1.setLabelFor(manufacturerTextField);
        jLabel1.setText("Manufacturer:");

        jLabel2.setDisplayedMnemonic('d');
        jLabel2.setLabelFor(modelTextField);
        jLabel2.setText("Model:");

        jLabel3.setDisplayedMnemonic('f');
        jLabel3.setLabelFor(firmwareVersionTextField);
        jLabel3.setText("Firmware version:");

        jLabel4.setDisplayedMnemonic('h');
        jLabel4.setLabelFor(hardwareVersionTextField);
        jLabel4.setText("Hardware version:");

        jLabel5.setDisplayedMnemonic('s');
        jLabel5.setLabelFor(serialNumberTextField);
        jLabel5.setText("Serial number:");

        firmwareVersionTextField.setEditable(false);
        firmwareVersionTextField.setText("-");

        hardwareVersionTextField.setEditable(false);
        hardwareVersionTextField.setText("-");

        serialNumberTextField.setEditable(false);
        serialNumberTextField.setText("-");

        modelTextField.setEditable(false);
        modelTextField.setText("-");

        manufacturerTextField.setEditable(false);
        manufacturerTextField.setText("-");

        refreshButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/refresh.png"))); // NOI18N
        refreshButton.setMnemonic('r');
        refreshButton.setText("Refresh");
        refreshButton.setToolTipText("Refresh the information about the device");
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });

        jLabel6.setDisplayedMnemonic('u');
        jLabel6.setLabelFor(firmwareDateTextField);
        jLabel6.setText("Last update:");

        firmwareDateTextField.setEditable(false);
        firmwareDateTextField.setText("-");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(refreshButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(okButton))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(manufacturerTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
                            .addComponent(serialNumberTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
                            .addComponent(hardwareVersionTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
                            .addComponent(firmwareVersionTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
                            .addComponent(modelTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
                            .addComponent(firmwareDateTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE))))
                .addContainerGap())
            .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
            .addComponent(descriptionLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {okButton, refreshButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(descriptionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(manufacturerTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(modelTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(firmwareVersionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(firmwareDateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(hardwareVersionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(serialNumberTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 54, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(okButton)
                    .addComponent(refreshButton))
                .addContainerGap())
        );

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-408)/2, (screenSize.height-306)/2, 408, 306);
    }// </editor-fold>//GEN-END:initComponents

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        dispose();
    }//GEN-LAST:event_okButtonActionPerformed

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
        helper.readDevInfoFile(true);
}//GEN-LAST:event_refreshButtonActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel descriptionLabel;
    private javax.swing.JTextField firmwareDateTextField;
    private javax.swing.JTextField firmwareVersionTextField;
    private javax.swing.JTextField hardwareVersionTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField manufacturerTextField;
    private javax.swing.JTextField modelTextField;
    private javax.swing.JButton okButton;
    private javax.swing.JButton refreshButton;
    private javax.swing.JTextField serialNumberTextField;
    // End of variables declaration//GEN-END:variables
}