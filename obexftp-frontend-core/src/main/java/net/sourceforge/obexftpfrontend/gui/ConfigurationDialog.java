package net.sourceforge.obexftpfrontend.gui;

import net.sourceforge.obexftpfrontend.model.ConfigurationListener;
import java.awt.Window;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import net.sourceforge.obexftpfrontend.model.Configuration;
import net.sourceforge.obexftpfrontend.model.ConfigurationHolder;
import net.sourceforge.obexftpfrontend.model.TransportType;
import net.sourceforge.obexftpfrontend.obexftp.DevInfoFileParser;

/**
 * Configuration dialog used to read and save the user's preferences.
 * @author Daniel F. Martins
 */
public class ConfigurationDialog extends JDialog implements ConfigurationHolder {

    /** UI helper. */
    private ConfigurationDialogHelper helper;

    /**
     * Create a new instance of ConfigurationDialog.
     * @param owner Parent frame.
     * @param devInfoFileParser Object used to parse the device information
     * file.
     */
    public ConfigurationDialog(Window owner, DevInfoFileParser devInfoFileParser) {
        super(owner, ModalityType.MODELESS);
        initComponents();

        helper = new ConfigurationDialogHelper(this);
        helper.initialize(devInfoFileParser);
        setLocationRelativeTo(getOwner());
    }

    // <editor-fold defaultstate="collapsed" desc=" Overriden methods ">
    @Override
    public void setVisible(boolean b) {
        helper.prepareWindow();
        super.setVisible(b);
    }

    /**
     * Delegates the call to the helper object.
     */
    @Override
    public Configuration getConfiguration() {
        return helper.getConfiguration();
    }

    /**
     * Delegates the call to the helper object.
     */
    @Override
    public void addConfigurationListener(ConfigurationListener listener) {
        helper.addConfigurationListener(listener);
    }

    /**
     * Delegates the call to the helper object.
     */
    @Override
    public void removeConfigurationListener(ConfigurationListener listener) {
        helper.removeConfigurationListener(listener);
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc=" Component Getters ">
    /**
     * Get the descriptionLabel object. This method should
     * be used only by the ViewHelper object.
     * @return The descriptionLabel object.
     */
    protected JLabel getDescriptionLabel() {
        return descriptionLabel;
    }

    /**
     * Get the tabbedPane object. This method should
     * be used only by the ViewHelper object.
     * @return The tabbedPane object.
     */
    protected JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    /**
     * Get the okButton object. This method should be
     * used only by the ViewHelper object.
     * @return The okButton object.
     */
    protected JButton getOkButton() {
        return okButton;
    }

    /**
     * Get the obexPathTextField object. This method should be
     * used only by the ViewHelper object.
     * @return The obexPathTextField object.
     */
    protected JTextField getObexPathTextField() {
        return obexPathTextField;
    }

    /**
     * Get the obexPathLabel object. This method should be
     * used only by the ViewHelper object.
     * @return The obexPathLabel object.
     */
    protected JLabel getObexPathLabel() {
        return obexPathLabel;
    }

    /**
     * Get the obexPathFindButton object. This method should be
     * used only by the ViewHelper object.
     * @return The obexPathFindButton object.
     */
    protected JButton getObexPathFindButton() {
        return obexPathFindButton;
    }

    /**
     * Get the timeoutCheckbox object. This method should be
     * used only by the ViewHelper object.
     * @return The timeoutCheckbox object.
     */
    protected JCheckBox getTimeoutCheckBox() {
        return timeoutCheckbox;
    }

    /**
     * Get the deviceInfoCheckbox object. This method should be
     * used only by the ViewHelper object.
     * @return The deviceInfoCheckbox object.
     */
    protected JCheckBox getDeviceInfoCheckbox() {
        return deviceInfoCheckbox;
    }

    /**
     * Get the timeoutSpinner object. This method should be
     * used only by the ViewHelper object.
     * @return timeoutSpinner object.
     */
    protected JSpinner getTimeoutSpinner() {
        return timeoutSpinner;
    }

    /**
     * Get the timeoutLabel object. This method should be
     * used only by the ViewHelper object.
     * @return The timeoutLabel object.
     */
    protected JLabel getTimeoutLabel() {
        return timeoutLabel;
    }

    /**
     * Get the validateObexListing object. This method should be
     * used only by the ViewHelper object.
     * @return The validateObexListing object.
     */
    protected JCheckBox getValidateObexListing() {
        return validateObexListing;
    }

    /**
     * Get the transportComboBox object. This method should be
     * used only by the ViewHelper object.
     * @return The transportComboBox object.
     */
    protected JComboBox getTransportComboBox() {
        return transportComboBox;
    }

    /**
     * Get the transportLabel object. This method should be
     * used only by the ViewHelper object.
     * @return The transportLabel object.
     */
    protected JLabel getTransportLabel() {
        return transportLabel;
    }

    /**
     * Get the obexFTPPanel object. This method should be
     * used only by the ViewHelper object.
     * @return The obexFTPPanel object.
     */
    protected JPanel getObexFTPPanel() {
        return obexFTPPanel;
    }

    /**
     * Get the fileExtensionsPanel object. This method should be
     * used only by the ViewHelper object.
     * @return The fileExtensionsPanel.
     */
    protected JPanel getFileExtensionsPanel() {
        return fileExtensionsPanel;
    }

    /**
     * Get the valueTextField object. This method should be
     * used only by the ViewHelper object.
     * @return The valueTextField object.
     */
    protected JTextField getValueTextField() {
        return valueTextField;
    }

    /**
     * Get the valueLabel object. This method should be
     * used only by the ViewHelper object.
     * @return The valueLabel object.
     */
    protected JLabel getValueLabel() {
        return valueLabel;
    }

    /**
     * Get the connectionProgressLabel object.  This method should be
     * used only by the ViewHelper object.
     * @return The connectionProgressLabel object.
     */
    protected JLabel getConnectionProgressLabel() {
        return connectionProgressLabel;
    }

    /**
     * Get the testButton object.  This method should be
     * used only by the ViewHelper object.
     * @return The testButton object.
     */
    protected JButton getTestButton() {
        return testButton;
    }

    /**
     * Get the fileTypeTable object. This method should be
     * used only by the fileTypeTable object.
     * @return The fileTypeTable object.
     */
    protected JTable getFileTypeTable() {
        return fileTypeTable;
    }

    /**
     * Get the deviceInfoLabel object. This method should be
     * used only by the ViewHelper object.
     * @return The deviceInfoLabel object.
     */
    protected JLabel getDeviceInfoLabel() {
        return deviceInfoLabel;
    }

    /**
     * Get the deviceInfoSeparator object. This method should be
     * used only by the ViewHelper object.
     * @return The deviceInfoSeparator object.
     */
    protected JSeparator getDeviceInfoSeparator() {
        return deviceInfoSeparator;
    }
    
    /**
     * Get the connectionHelpLabel object. This method should be
     * used only by the ViewHelper object.
     * @return The connectionHelpLabel object.
     */
    protected JLabel getConnectionHelpLabel() {
        return connectionHelpLabel;
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        tabbedPane = new javax.swing.JTabbedPane();
        obexFTPPanel = new javax.swing.JPanel();
        obexPathLabel = new javax.swing.JLabel();
        obexPathTextField = new javax.swing.JTextField();
        obexPathFindButton = new javax.swing.JButton();
        timeoutCheckbox = new javax.swing.JCheckBox();
        timeoutSpinner = new javax.swing.JSpinner();
        timeoutLabel = new javax.swing.JLabel();
        validateObexListing = new javax.swing.JCheckBox();
        optionsLabel = new javax.swing.JLabel();
        deviceInfoLabel = new javax.swing.JLabel();
        deviceInfoSeparator = new javax.swing.JSeparator();
        transportLabel = new javax.swing.JLabel();
        valueTextField = new javax.swing.JTextField();
        valueLabel = new javax.swing.JLabel();
        testButton = new javax.swing.JButton();
        deviceInfoCheckbox = new javax.swing.JCheckBox();
        connectionHelpLabel = new javax.swing.JLabel();
        transportComboBox = new javax.swing.JComboBox();
        fileExtensionsPanel = new javax.swing.JPanel();
        fileTypeScrollPane = new javax.swing.JScrollPane();
        fileTypeTable = new javax.swing.JTable();
        fileExtensionsHelpLabel = new javax.swing.JLabel();
        descriptionLabel = new javax.swing.JLabel();
        descriptionSeparator = new javax.swing.JSeparator();
        connectionProgressLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Configuration");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        okButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/ok.png"))); // NOI18N
        okButton.setMnemonic('o');
        okButton.setText("OK");
        okButton.setToolTipText("Confirm changes and close this dialog");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        cancelButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/cancel.png"))); // NOI18N
        cancelButton.setMnemonic('c');
        cancelButton.setText("Cancel");
        cancelButton.setToolTipText("Cancel changes and close this dialog");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        tabbedPane.setFont(new java.awt.Font("Dialog", 0, 12));

        obexPathLabel.setDisplayedMnemonic('a');
        obexPathLabel.setLabelFor(obexPathTextField);
        obexPathLabel.setText("ObexFTP path:");

        obexPathTextField.setToolTipText("Path to obexftp program");

        obexPathFindButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/find.png"))); // NOI18N
        obexPathFindButton.setMnemonic('f');
        obexPathFindButton.setText("Find...");
        obexPathFindButton.setToolTipText("Search for the obexftp program...");
        obexPathFindButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                obexPathFindButtonActionPerformed(evt);
            }
        });

        timeoutCheckbox.setMnemonic('t');
        timeoutCheckbox.setText("Process timeout to");
        timeoutCheckbox.setToolTipText("Wait for the obexftp process for the given time, in seconds");
        timeoutCheckbox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        timeoutCheckbox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                timeoutCheckboxItemStateChanged(evt);
            }
        });

        timeoutSpinner.setFont(new java.awt.Font("Dialog", 0, 12));
        timeoutSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 10, 1));
        timeoutSpinner.setEnabled(false);

        timeoutLabel.setText("seconds");
        timeoutLabel.setEnabled(false);

        validateObexListing.setMnemonic('d');
        validateObexListing.setText("Validate OBEX listing");
        validateObexListing.setToolTipText("Whether the OBEX listing XML should be validated against the DTD");
        validateObexListing.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));

        optionsLabel.setText("Advanced options:");

        deviceInfoLabel.setText("Device information");

        transportLabel.setDisplayedMnemonic('y');
        transportLabel.setLabelFor(transportComboBox);
        transportLabel.setText("Connection type:");

        valueLabel.setDisplayedMnemonic('l');
        valueLabel.setLabelFor(valueTextField);
        valueLabel.setText("Connection line:");

        testButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/test-connection.png"))); // NOI18N
        testButton.setMnemonic('e');
        testButton.setText("Test");
        testButton.setToolTipText("Try to connect to the device");
        testButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testButtonActionPerformed(evt);
            }
        });

        deviceInfoCheckbox.setMnemonic('i');
        deviceInfoCheckbox.setText("Device info fetching");
        deviceInfoCheckbox.setToolTipText("Whether the device info fetching should be enabled");
        deviceInfoCheckbox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));

        connectionHelpLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/help.png"))); // NOI18N
        connectionHelpLabel.setText("Device connection help goes here");
        connectionHelpLabel.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        transportComboBox.setModel(new DefaultComboBoxModel(TransportType.class.getEnumConstants()));

        javax.swing.GroupLayout obexFTPPanelLayout = new javax.swing.GroupLayout(obexFTPPanel);
        obexFTPPanel.setLayout(obexFTPPanelLayout);
        obexFTPPanelLayout.setHorizontalGroup(
            obexFTPPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, obexFTPPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(obexFTPPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(connectionHelpLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 566, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, obexFTPPanelLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(validateObexListing))
                    .addGroup(obexFTPPanelLayout.createSequentialGroup()
                        .addComponent(obexPathTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 484, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(obexPathFindButton))
                    .addComponent(obexPathLabel, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(optionsLabel, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, obexFTPPanelLayout.createSequentialGroup()
                        .addComponent(deviceInfoLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deviceInfoSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, obexFTPPanelLayout.createSequentialGroup()
                        .addGroup(obexFTPPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(transportLabel)
                            .addComponent(transportComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(obexFTPPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(valueLabel)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, obexFTPPanelLayout.createSequentialGroup()
                                .addComponent(valueTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(testButton))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, obexFTPPanelLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(obexFTPPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(deviceInfoCheckbox)
                            .addGroup(obexFTPPanelLayout.createSequentialGroup()
                                .addComponent(timeoutCheckbox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(timeoutSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(timeoutLabel)))
                        .addGap(217, 217, 217)))
                .addContainerGap())
        );

        obexFTPPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {obexPathFindButton, testButton});

        obexFTPPanelLayout.setVerticalGroup(
            obexFTPPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(obexFTPPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(obexPathLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(obexFTPPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(obexPathFindButton)
                    .addComponent(obexPathTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(optionsLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(validateObexListing)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(obexFTPPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(timeoutCheckbox)
                    .addComponent(timeoutSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(timeoutLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(deviceInfoCheckbox)
                .addGap(22, 22, 22)
                .addGroup(obexFTPPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(deviceInfoLabel)
                    .addComponent(deviceInfoSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(obexFTPPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(transportLabel)
                    .addComponent(valueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(obexFTPPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(testButton)
                    .addComponent(valueTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(transportComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addComponent(connectionHelpLabel)
                .addContainerGap())
        );

        tabbedPane.addTab("ObexFTP settings", obexFTPPanel);

        fileTypeScrollPane.setViewportView(fileTypeTable);

        fileExtensionsHelpLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/help.png"))); // NOI18N
        fileExtensionsHelpLabel.setText("<html>Delimit file extensions with a space. <strong>Ex</strong>: .mp3 .mp4</html>");
        fileExtensionsHelpLabel.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout fileExtensionsPanelLayout = new javax.swing.GroupLayout(fileExtensionsPanel);
        fileExtensionsPanel.setLayout(fileExtensionsPanelLayout);
        fileExtensionsPanelLayout.setHorizontalGroup(
            fileExtensionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, fileExtensionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(fileExtensionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(fileExtensionsHelpLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 566, Short.MAX_VALUE)
                    .addComponent(fileTypeScrollPane, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 566, Short.MAX_VALUE))
                .addContainerGap())
        );
        fileExtensionsPanelLayout.setVerticalGroup(
            fileExtensionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fileExtensionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(fileTypeScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(fileExtensionsHelpLabel)
                .addContainerGap())
        );

        tabbedPane.addTab("File extensions", fileExtensionsPanel);

        descriptionLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/top-description.png"))); // NOI18N
        descriptionLabel.setText("Tab description goes here");
        descriptionLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));

        connectionProgressLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/loading.gif"))); // NOI18N
        connectionProgressLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));
        connectionProgressLabel.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(descriptionSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 618, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(descriptionLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 592, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(connectionProgressLabel))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 594, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cancelButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(okButton)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cancelButton, okButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(connectionProgressLabel, 0, 0, Short.MAX_VALUE)
                    .addComponent(descriptionLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addComponent(descriptionSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(okButton)
                    .addComponent(cancelButton))
                .addContainerGap())
        );

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-628)/2, (screenSize.height-471)/2, 628, 471);
    }// </editor-fold>//GEN-END:initComponents

private void timeoutCheckboxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_timeoutCheckboxItemStateChanged
    helper.timeoutChanged(evt);
}//GEN-LAST:event_timeoutCheckboxItemStateChanged

private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
    dispose();
}//GEN-LAST:event_formWindowClosing

private void testButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_testButtonActionPerformed
    helper.testConnection();
}//GEN-LAST:event_testButtonActionPerformed

private void obexPathFindButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_obexPathFindButtonActionPerformed
    helper.findObexFTP();
}//GEN-LAST:event_obexPathFindButtonActionPerformed

private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
    helper.ok();
}//GEN-LAST:event_okButtonActionPerformed

private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
    helper.cancel();
}//GEN-LAST:event_cancelButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel connectionHelpLabel;
    private javax.swing.JLabel connectionProgressLabel;
    private javax.swing.JLabel descriptionLabel;
    private javax.swing.JSeparator descriptionSeparator;
    private javax.swing.JCheckBox deviceInfoCheckbox;
    private javax.swing.JLabel deviceInfoLabel;
    private javax.swing.JSeparator deviceInfoSeparator;
    private javax.swing.JLabel fileExtensionsHelpLabel;
    private javax.swing.JPanel fileExtensionsPanel;
    private javax.swing.JScrollPane fileTypeScrollPane;
    private javax.swing.JTable fileTypeTable;
    private javax.swing.JPanel obexFTPPanel;
    private javax.swing.JButton obexPathFindButton;
    private javax.swing.JLabel obexPathLabel;
    private javax.swing.JTextField obexPathTextField;
    private javax.swing.JButton okButton;
    private javax.swing.JLabel optionsLabel;
    private javax.swing.JTabbedPane tabbedPane;
    private javax.swing.JButton testButton;
    private javax.swing.JCheckBox timeoutCheckbox;
    private javax.swing.JLabel timeoutLabel;
    private javax.swing.JSpinner timeoutSpinner;
    private javax.swing.JComboBox transportComboBox;
    private javax.swing.JLabel transportLabel;
    private javax.swing.JCheckBox validateObexListing;
    private javax.swing.JLabel valueLabel;
    private javax.swing.JTextField valueTextField;
    // End of variables declaration//GEN-END:variables
}