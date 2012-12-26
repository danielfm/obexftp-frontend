package net.sourceforge.obexftpfrontend.gui;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JToolBar;
import javax.swing.JTree;

/**
 * Main frame.
 * @author Daniel F. Martins
 */
public class MainFrame extends JFrame {

    /** UI helper. */
    private MainFrameHelper helper;

    /**
     * Create a new instance of MainFrame.
     */
    public MainFrame() {
        helper = new MainFrameHelper(this);

        initComponents();

        helper.prepareWindow();
        setLocationRelativeTo(null);
    }

    // <editor-fold defaultstate="collapsed" desc=" Component Getters ">
    /**
     * Get the fileSystemTree object. This method should be
     * only used by the ViewHelper object.
     * @return The fileSystemTree object.
     */
    protected JTree getFileSystemTree() {
        return fileSystemTree;
    }

    /**
     * Get the toolBar object. This method should be
     * only used by the ViewHelper object.
     * @return The toolBar object.
     */
    protected JToolBar getToolBar() {
        return toolBar;
    }

    /**
     * Get the progressLabel object. This method should be
     * only used by the ViewHelper object.
     * @return The progressLabel object.
     */
    protected JLabel getProgressLabel() {
        return progressLabel;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileSystemPopupMenu = new javax.swing.JPopupMenu();
        createFolderPopupMenuItem = new javax.swing.JMenuItem();
        sendFilesPopupMenuItem = new javax.swing.JMenuItem();
        selectedFilesPopupMenu = new javax.swing.JMenu();
        refreshSelectedFoldersPopupMenuItem = new javax.swing.JMenuItem();
        selectedFilesPopupMenuSeparator1 = new javax.swing.JSeparator();
        getSelectedFilesPopupMenuItem = new javax.swing.JMenuItem();
        getAndRemoveSelectedFilesPopupMenuItem = new javax.swing.JMenuItem();
        removeSelectedFilesPopupMenuItem = new javax.swing.JMenuItem();
        selectedFilesMenuSeparator3 = new javax.swing.JSeparator();
        showFilePropertiesPopupMenuItem = new javax.swing.JMenuItem();
        toolBar = new javax.swing.JToolBar();
        showRootFilesButton = new javax.swing.JButton();
        refreshSelectedFoldersButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        sendFilesButton = new javax.swing.JButton();
        getSelectedFilesButton = new javax.swing.JButton();
        removeSelectedFilesButton = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        configurationButton = new javax.swing.JButton();
        fileSystemTreeScrollPane = new javax.swing.JScrollPane();
        fileSystemTree = new javax.swing.JTree();
        progressPanel = new javax.swing.JPanel();
        progressLabel = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        applicationMenu = new javax.swing.JMenu();
        exitMenuItem = new javax.swing.JMenuItem();
        deviceMenu = new javax.swing.JMenu();
        showRootFilesMenuItem = new javax.swing.JMenuItem();
        deviceMenuSeparator1 = new javax.swing.JSeparator();
        createFolderMenuItem = new javax.swing.JMenuItem();
        sendFilesMenuItem = new javax.swing.JMenuItem();
        selectedFilesMenu = new javax.swing.JMenu();
        refreshSelectedFoldersMenuItem = new javax.swing.JMenuItem();
        selectedFilesMenuSeparator1 = new javax.swing.JSeparator();
        getSelectedFilesMenuItem = new javax.swing.JMenuItem();
        getAndRemoveSelectedFilesMenuItem = new javax.swing.JMenuItem();
        removeSelectedFilesMenuItem = new javax.swing.JMenuItem();
        selectedFilesMenuSeparator2 = new javax.swing.JSeparator();
        showFilePropertiesMenuItem = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JSeparator();
        showDeviceInfoMenuItem = new javax.swing.JMenuItem();
        optionMenu = new javax.swing.JMenu();
        configurationMenuItem = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        aboutMenuItem = new javax.swing.JMenuItem();

        fileSystemPopupMenu.setComponentPopupMenu(fileSystemPopupMenu);

        createFolderPopupMenuItem.setAction(helper.getCreateFolderAction());
        fileSystemPopupMenu.add(createFolderPopupMenuItem);

        sendFilesPopupMenuItem.setAction(helper.getSendFilesAction());
        fileSystemPopupMenu.add(sendFilesPopupMenuItem);

        selectedFilesPopupMenu.setMnemonic('d');
        selectedFilesPopupMenu.setText("Selected files");

        refreshSelectedFoldersPopupMenuItem.setAction(helper.getRefreshSelectedFoldersAction());
        selectedFilesPopupMenu.add(refreshSelectedFoldersPopupMenuItem);
        selectedFilesPopupMenu.add(selectedFilesPopupMenuSeparator1);

        getSelectedFilesPopupMenuItem.setAction(helper.getGetSelectedFilesAction());
        selectedFilesPopupMenu.add(getSelectedFilesPopupMenuItem);

        getAndRemoveSelectedFilesPopupMenuItem.setAction(helper.getGetAndRemoveSelectedFilesAction());
        selectedFilesPopupMenu.add(getAndRemoveSelectedFilesPopupMenuItem);

        removeSelectedFilesPopupMenuItem.setAction(helper.getRemoveSelectedFilesAction());
        selectedFilesPopupMenu.add(removeSelectedFilesPopupMenuItem);
        selectedFilesPopupMenu.add(selectedFilesMenuSeparator3);

        showFilePropertiesPopupMenuItem.setAction(helper.getShowFilePropertiesAction());
        selectedFilesPopupMenu.add(showFilePropertiesPopupMenuItem);

        fileSystemPopupMenu.add(selectedFilesPopupMenu);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle(MainFrameHelper.getTitle());
        setIconImage(new ImageIcon(getClass().getResource("/icon/main.png")).getImage());
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formClosing(evt);
            }
        });

        showRootFilesButton.setAction(helper.getShowRootFilesAction());
        showRootFilesButton.setFocusable(false);
        showRootFilesButton.setHideActionText(true);
        showRootFilesButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        showRootFilesButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBar.add(showRootFilesButton);

        refreshSelectedFoldersButton.setAction(helper.getRefreshSelectedFoldersAction());
        refreshSelectedFoldersButton.setFocusable(false);
        refreshSelectedFoldersButton.setHideActionText(true);
        refreshSelectedFoldersButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        refreshSelectedFoldersButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBar.add(refreshSelectedFoldersButton);
        toolBar.add(jSeparator1);

        sendFilesButton.setAction(helper.getSendFilesAction());
        sendFilesButton.setFocusable(false);
        sendFilesButton.setHideActionText(true);
        sendFilesButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        sendFilesButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBar.add(sendFilesButton);

        getSelectedFilesButton.setAction(helper.getGetSelectedFilesAction());
        getSelectedFilesButton.setFocusable(false);
        getSelectedFilesButton.setHideActionText(true);
        getSelectedFilesButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        getSelectedFilesButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBar.add(getSelectedFilesButton);

        removeSelectedFilesButton.setAction(helper.getRemoveSelectedFilesAction());
        removeSelectedFilesButton.setFocusable(false);
        removeSelectedFilesButton.setHideActionText(true);
        removeSelectedFilesButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        removeSelectedFilesButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBar.add(removeSelectedFilesButton);
        toolBar.add(jSeparator3);

        configurationButton.setAction(helper.getShowConfigurationsDialogAction());
        configurationButton.setFocusable(false);
        configurationButton.setHideActionText(true);
        configurationButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        configurationButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBar.add(configurationButton);

        getContentPane().add(toolBar, java.awt.BorderLayout.PAGE_START);

        fileSystemTree.setAutoscrolls(true);
        fileSystemTree.setComponentPopupMenu(fileSystemPopupMenu);
        fileSystemTree.setRootVisible(false);
        fileSystemTree.setShowsRootHandles(true);
        fileSystemTreeScrollPane.setViewportView(fileSystemTree);

        getContentPane().add(fileSystemTreeScrollPane, java.awt.BorderLayout.CENTER);

        progressPanel.setPreferredSize(new java.awt.Dimension(100, 26));
        progressPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        progressLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        progressLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/loading.gif"))); // NOI18N
        progressLabel.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        progressPanel.add(progressLabel);

        getContentPane().add(progressPanel, java.awt.BorderLayout.PAGE_END);

        applicationMenu.setMnemonic('a');
        applicationMenu.setText("Application");

        exitMenuItem.setAction(helper.getExitApplicationAction());
        applicationMenu.add(exitMenuItem);

        menuBar.add(applicationMenu);

        deviceMenu.setMnemonic('d');
        deviceMenu.setText("Device");

        showRootFilesMenuItem.setAction(helper.getShowRootFilesAction());
        deviceMenu.add(showRootFilesMenuItem);
        deviceMenu.add(deviceMenuSeparator1);

        createFolderMenuItem.setAction(helper.getCreateFolderAction());
        deviceMenu.add(createFolderMenuItem);

        sendFilesMenuItem.setAction(helper.getSendFilesAction());
        deviceMenu.add(sendFilesMenuItem);

        selectedFilesMenu.setMnemonic('d');
        selectedFilesMenu.setText("Selected files");

        refreshSelectedFoldersMenuItem.setAction(helper.getRefreshSelectedFoldersAction());
        selectedFilesMenu.add(refreshSelectedFoldersMenuItem);
        selectedFilesMenu.add(selectedFilesMenuSeparator1);

        getSelectedFilesMenuItem.setAction(helper.getGetSelectedFilesAction());
        selectedFilesMenu.add(getSelectedFilesMenuItem);

        getAndRemoveSelectedFilesMenuItem.setAction(helper.getGetAndRemoveSelectedFilesAction());
        selectedFilesMenu.add(getAndRemoveSelectedFilesMenuItem);

        removeSelectedFilesMenuItem.setAction(helper.getRemoveSelectedFilesAction());
        selectedFilesMenu.add(removeSelectedFilesMenuItem);
        selectedFilesMenu.add(selectedFilesMenuSeparator2);

        showFilePropertiesMenuItem.setAction(helper.getShowFilePropertiesAction());
        selectedFilesMenu.add(showFilePropertiesMenuItem);

        deviceMenu.add(selectedFilesMenu);
        deviceMenu.add(jSeparator4);

        showDeviceInfoMenuItem.setAction(helper.getShowDeviceInfoAction());
        deviceMenu.add(showDeviceInfoMenuItem);

        menuBar.add(deviceMenu);

        optionMenu.setMnemonic('o');
        optionMenu.setText("Options");

        configurationMenuItem.setAction(helper.getShowConfigurationsDialogAction());
        optionMenu.add(configurationMenuItem);

        menuBar.add(optionMenu);

        jMenu1.setMnemonic('h');
        jMenu1.setText("Help");

        aboutMenuItem.setAction(helper.getShowAboutDialogAction());
        jMenu1.add(aboutMenuItem);

        menuBar.add(jMenu1);

        setJMenuBar(menuBar);

        getAccessibleContext().setAccessibleParent(this);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-780)/2, (screenSize.height-585)/2, 780, 585);
    }// </editor-fold>//GEN-END:initComponents

    private void formClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formClosing
        helper.frameClosing();
    }//GEN-LAST:event_formClosing

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JMenu applicationMenu;
    private javax.swing.JButton configurationButton;
    private javax.swing.JMenuItem configurationMenuItem;
    private javax.swing.JMenuItem createFolderMenuItem;
    private javax.swing.JMenuItem createFolderPopupMenuItem;
    private javax.swing.JMenu deviceMenu;
    private javax.swing.JSeparator deviceMenuSeparator1;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JPopupMenu fileSystemPopupMenu;
    private javax.swing.JTree fileSystemTree;
    private javax.swing.JScrollPane fileSystemTreeScrollPane;
    private javax.swing.JMenuItem getAndRemoveSelectedFilesMenuItem;
    private javax.swing.JMenuItem getAndRemoveSelectedFilesPopupMenuItem;
    private javax.swing.JButton getSelectedFilesButton;
    private javax.swing.JMenuItem getSelectedFilesMenuItem;
    private javax.swing.JMenuItem getSelectedFilesPopupMenuItem;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu optionMenu;
    private javax.swing.JLabel progressLabel;
    private javax.swing.JPanel progressPanel;
    private javax.swing.JButton refreshSelectedFoldersButton;
    private javax.swing.JMenuItem refreshSelectedFoldersMenuItem;
    private javax.swing.JMenuItem refreshSelectedFoldersPopupMenuItem;
    private javax.swing.JButton removeSelectedFilesButton;
    private javax.swing.JMenuItem removeSelectedFilesMenuItem;
    private javax.swing.JMenuItem removeSelectedFilesPopupMenuItem;
    private javax.swing.JMenu selectedFilesMenu;
    private javax.swing.JSeparator selectedFilesMenuSeparator1;
    private javax.swing.JSeparator selectedFilesMenuSeparator2;
    private javax.swing.JSeparator selectedFilesMenuSeparator3;
    private javax.swing.JMenu selectedFilesPopupMenu;
    private javax.swing.JSeparator selectedFilesPopupMenuSeparator1;
    private javax.swing.JButton sendFilesButton;
    private javax.swing.JMenuItem sendFilesMenuItem;
    private javax.swing.JMenuItem sendFilesPopupMenuItem;
    private javax.swing.JMenuItem showDeviceInfoMenuItem;
    private javax.swing.JMenuItem showFilePropertiesMenuItem;
    private javax.swing.JMenuItem showFilePropertiesPopupMenuItem;
    private javax.swing.JButton showRootFilesButton;
    private javax.swing.JMenuItem showRootFilesMenuItem;
    private javax.swing.JToolBar toolBar;
    // End of variables declaration//GEN-END:variables
}