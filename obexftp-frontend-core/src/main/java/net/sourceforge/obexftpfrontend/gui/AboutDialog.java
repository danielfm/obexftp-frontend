package net.sourceforge.obexftpfrontend.gui;

import java.awt.Window;
import javax.swing.JButton;
import javax.swing.JDialog;

/**
 * About dialog, which shows some information about this
 * product and its author.
 * @author Daniel F. Martins
 */
public class AboutDialog extends JDialog {

    /** UI Helper. */
    private AboutDialogHelper helper;

    /**
     * Create a new instance of AboutDialog
     * @param owner Parent frame.
     */
    public AboutDialog(Window owner) {
        super(owner, ModalityType.MODELESS);
        initComponents();

        helper = new AboutDialogHelper(this);
        setLocationRelativeTo(getOwner());
    }

    // <editor-fold defaultstate="collapsed" desc=" Component Getters ">
    /**
     * Get the visitWebSiteButton object. This method should be
     * used only by the ViewHelper object.
     * @return The visitWebSiteButton object.
     */
    protected JButton getVisitWebsiteButton() {
        return visitWebsiteButton;
    }

    /**
     * Get the okButton object. This method should be
     * used only by the ViewHelper object.
     * @return The okButton object.
     */
    protected JButton getOkButton() {
        return okButton;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc=" Overriden methods ">
    @Override
    public void setVisible(boolean b) {
        helper.prepareWindow();
        super.setVisible(b);
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        logoLabel = new javax.swing.JLabel();
        okButton = new javax.swing.JButton();
        versionLabel = new javax.swing.JLabel();
        visitWebsiteButton = new javax.swing.JButton();
        moteLabel = new javax.swing.JLabel();
        copyrightLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("About");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);

        logoLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        logoLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/logo.png"))); // NOI18N

        okButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/ok.png"))); // NOI18N
        okButton.setMnemonic('o');
        okButton.setText("OK");
        okButton.setToolTipText("Close this dialog");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        versionLabel.setFont(new java.awt.Font("DejaVu Sans", 1, 18));
        versionLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        versionLabel.setText(MainFrameHelper.getTitle());

        visitWebsiteButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/website.png"))); // NOI18N
        visitWebsiteButton.setMnemonic('w');
        visitWebsiteButton.setText("Visit website");
        visitWebsiteButton.setActionCommand("http://obexftpfrontend.sourceforge.net");
        visitWebsiteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                visitWebsiteButtonActionPerformed(evt);
            }
        });

        moteLabel.setFont(moteLabel.getFont().deriveFont((moteLabel.getFont().getStyle() | java.awt.Font.ITALIC)));
        moteLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        moteLabel.setText("ObexFTP easier than ever!");

        copyrightLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        copyrightLabel.setText("Copyright 2007 - 2008 Daniel F. Martins");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(versionLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
                    .addComponent(logoLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
                    .addComponent(moteLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(visitWebsiteButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 91, Short.MAX_VALUE)
                        .addComponent(okButton))
                    .addComponent(copyrightLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(logoLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(versionLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(moteLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(copyrightLabel)
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(visitWebsiteButton)
                    .addComponent(okButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void visitWebsiteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_visitWebsiteButtonActionPerformed
    helper.openURL("sourceforge.net/projects/obexftpfrontend");
}//GEN-LAST:event_visitWebsiteButtonActionPerformed

private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
    dispose();
}//GEN-LAST:event_okButtonActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel copyrightLabel;
    private javax.swing.JLabel logoLabel;
    private javax.swing.JLabel moteLabel;
    private javax.swing.JButton okButton;
    private javax.swing.JLabel versionLabel;
    private javax.swing.JButton visitWebsiteButton;
    // End of variables declaration//GEN-END:variables
}