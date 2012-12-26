package net.sourceforge.obexftpfrontend.gui;

import net.sourceforge.obexftpfrontend.gui.listener.FetchFileListFromFolderListener;
import net.sourceforge.obexftpfrontend.gui.listener.FileSystemSelectionListener;
import java.awt.Cursor;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import net.sourceforge.obexftpfrontend.command.OBEXFTPCommandConsumer;
import net.sourceforge.obexftpfrontend.command.OBEXFTPCommandLifecycleEvent;
import net.sourceforge.obexftpfrontend.command.OBEXFTPCommandQueueListener;
import net.sourceforge.obexftpfrontend.command.OBEXFTPCommandQueue;
import net.sourceforge.obexftpfrontend.command.DefaultOBEXFTPCommandConsumer;
import net.sourceforge.obexftpfrontend.command.DefaultOBEXFTPCommandQueue;
import net.sourceforge.obexftpfrontend.gui.action.CreateFolderAction;
import net.sourceforge.obexftpfrontend.gui.action.QuitApplicationAction;
import net.sourceforge.obexftpfrontend.gui.action.GetSelectedFilesAction;
import net.sourceforge.obexftpfrontend.gui.action.RefreshSelectedFoldersAction;
import net.sourceforge.obexftpfrontend.gui.action.RemoveSelectedFilesAction;
import net.sourceforge.obexftpfrontend.gui.action.SendFilesAction;
import net.sourceforge.obexftpfrontend.gui.action.ShowAboutDialogAction;
import net.sourceforge.obexftpfrontend.gui.action.ShowConfigurationsDialogAction;
import net.sourceforge.obexftpfrontend.gui.action.ShowDeviceInfoAction;
import net.sourceforge.obexftpfrontend.gui.action.ShowFilePropertiesAction;
import net.sourceforge.obexftpfrontend.gui.action.ShowRootFilesAction;
import net.sourceforge.obexftpfrontend.model.ConfigurationEvent;
import net.sourceforge.obexftpfrontend.model.ConfigurationListener;
import net.sourceforge.obexftpfrontend.gui.model.OBEXFileSystemTreeModel;
import net.sourceforge.obexftpfrontend.model.OBEXElement;
import net.sourceforge.obexftpfrontend.obexftp.DefaultDevInfoFileParser;
import net.sourceforge.obexftpfrontend.obexftp.DevInfoFileParser;
import net.sourceforge.obexftpfrontend.obexftp.JDOMOBEXResponseParser;
import net.sourceforge.obexftpfrontend.obexftp.OBEXFTPNotFoundException;
import net.sourceforge.obexftpfrontend.obexftp.OBEXResponseParser;
import net.sourceforge.obexftpfrontend.persistence.DefaultMetaConfigurationReader;
import org.apache.log4j.Logger;

/**
 * MainFrame UI helper class.
 * @author Daniel F. Martins
 */
public class MainFrameHelper extends AbstractUIHelper<MainFrame> implements FileSystemTreeHolder, ContextMenuHandler, OBEXFTPCommandQueueListener, ConfigurationListener {

    private static final Logger log = Logger.getLogger(MainFrameHelper.class);
    private DevInfoFileParser devInfoFileParser;
    private OBEXResponseParser parser;
    private OBEXFTPCommandQueue queue;
    private OBEXFTPCommandConsumer commandConsumer;
    private FileSystemCellRenderer cellRenderer;
    private FetchFileListFromFolderListener expansionListener;
    private ShowAboutDialogAction showAboutDialogAction;
    private QuitApplicationAction exitApplicationAction;
    private ShowRootFilesAction showRootFilesAction;
    private CreateFolderAction createFolderAction;
    private SendFilesAction sendFilesAction;
    private RefreshSelectedFoldersAction refreshSelectedFoldersAction;
    private GetSelectedFilesAction getSelectedFilesAction;
    private GetSelectedFilesAction getAndRemoveSelectedFilesAction;
    private RemoveSelectedFilesAction removeSelectedFilesAction;
    private ShowFilePropertiesAction showFilePropertiesAction;
    private ShowDeviceInfoAction showDeviceInfoAction;
    private ShowConfigurationsDialogAction showConfigurationsDialogAction;

    /**
     * Create a new instance of MainFrameHelper.
     * @param window MainFrame window.
     */
    public MainFrameHelper(MainFrame window) {
        super(window);
        setupCommandQueue();
        wireActions();
    }

    /**
     * This method returns the current release version.
     * @return The current application version.
     */
    public static final String getTitle() {
        return "ObexFTP Front-end 0.7";
    }

    /**
     * Update the enablement of context menus, by generating a fake tree
     * selection event.
     */
    private void updateContextMenusEnablement() {
        JTree tree = window.getFileSystemTree();

        /* generate a "fake" tree selection event */
        TreeSelectionEvent event = new TreeSelectionEvent(tree, null, false, null, null);
        for (TreeSelectionListener listener : tree.getTreeSelectionListeners()) {
            listener.valueChanged(event);
        }

        log.debug("Updating the initial enablement state of context menus");
    }

    /**
     * Configure the additional initial state of the progress label.
     */
    private void setupProgressLabel() {
        window.getProgressLabel().setVisible(false);
        log.debug("Progress label configured");
    }

    /**
     * Configure the JTree component.
     */
    private void setupJTree() {
        cellRenderer = new FileSystemCellRenderer(showConfigurationsDialogAction);

        expansionListener = new FetchFileListFromFolderListener(showConfigurationsDialogAction, this, parser, queue);
        queue.addCommandExecutionListener(expansionListener);

        window.getFileSystemTree().setModel(new OBEXFileSystemTreeModel());
        window.getFileSystemTree().setCellRenderer(cellRenderer);

        window.getFileSystemTree().addTreeExpansionListener(expansionListener);
        window.getFileSystemTree().addTreeSelectionListener(new FileSystemSelectionListener(this));

        log.debug("File system tree configured");
    }

    /**
     * Create the and wire application's Actions.
     */
    private void wireActions() {
        showAboutDialogAction = new ShowAboutDialogAction(window);
        exitApplicationAction = new QuitApplicationAction(window);

        devInfoFileParser = new DefaultDevInfoFileParser();

        showConfigurationsDialogAction = new ShowConfigurationsDialogAction(window, devInfoFileParser);
        showConfigurationsDialogAction.addConfigurationListener(parser);
        showConfigurationsDialogAction.addConfigurationListener(this);

        showDeviceInfoAction = new ShowDeviceInfoAction(window, showConfigurationsDialogAction, queue, devInfoFileParser);

        /* generate a fake event */
        ConfigurationEvent fakeConfigEvent = new ConfigurationEvent(showConfigurationsDialogAction.getConfiguration());
        parser.configurationChanged(fakeConfigEvent);
        configurationChanged(fakeConfigEvent);

        showRootFilesAction = new ShowRootFilesAction(this);
        createFolderAction = new CreateFolderAction(window, showConfigurationsDialogAction, this, queue);
        sendFilesAction = new SendFilesAction(showConfigurationsDialogAction, queue, window, this);

        refreshSelectedFoldersAction = new RefreshSelectedFoldersAction(showConfigurationsDialogAction, queue, this);
        getSelectedFilesAction = new GetSelectedFilesAction(window, showConfigurationsDialogAction, queue, this, false);
        getAndRemoveSelectedFilesAction = new GetSelectedFilesAction(window, showConfigurationsDialogAction, queue, this, true);
        removeSelectedFilesAction = new RemoveSelectedFilesAction(showConfigurationsDialogAction, queue, window, this);
        showFilePropertiesAction = new ShowFilePropertiesAction(window, this);

        log.debug("Application's actions created");
    }

    /**
     * Create the CommandQueue object and launch the command consumer object.
     */
    private void setupCommandQueue() {
        parser = new JDOMOBEXResponseParser();
        log.debug("OBEXResponseParser object created");

        queue = new DefaultOBEXFTPCommandQueue();
        queue.addCommandExecutionListener(this);
        log.debug("Queue object created");

        commandConsumer = new DefaultOBEXFTPCommandConsumer(queue);
        Thread consumer = new Thread(commandConsumer);

        /* start the command consumer thread */
        consumer.setName("command-consumer");
        consumer.start();

        log.debug("CommandConsumer object is running");
    }

    // <editor-fold defaultstate="collapsed" desc=" Overriden Methods ">
    @Override
    public void prepareWindow() {
        setupJTree();
        setupProgressLabel();
        updateContextMenusEnablement();

        if (!DefaultMetaConfigurationReader.getInstance().getConfigFile().exists()) {
            showConfigurationsDialogAction.actionPerformed(null);
        }
    }

    @Override
    public void configurationChanged(ConfigurationEvent event) {
        showDeviceInfoAction.setEnabled(event.getConfiguration().isFetchDeviceInfo());
    }

    @Override
    public void commandAdded(OBEXFTPCommandLifecycleEvent event) {
        log.debug("commandAdded() called");
    }

    @Override
    public void commandRemoved(OBEXFTPCommandLifecycleEvent event) {
        window.getProgressLabel().setVisible(true);
        window.getProgressLabel().setText(event.getCommand().getDescription());

        showWaitCursor(true);

        log.debug("commandRemoved() called");
    }

    @Override
    public void commandExecuted(OBEXFTPCommandLifecycleEvent event) {
        window.getProgressLabel().setVisible(!queue.isEmpty());
        window.getProgressLabel().setText("");

        showWaitCursor(!queue.isEmpty());

        if (event.getStatus() == OBEXFTPCommandLifecycleEvent.ExecutionStatus.ERROR) {
            window.getProgressLabel().setVisible(false);

            if (event.getResult() instanceof OBEXFTPNotFoundException) {
                showErrorMessage("Obexftp application not found. Check the configuration");
            } else if (showConfirmMessage("<html>Command <strong>" + event.getCommand().getDescription() + "</strong> has failed.<br/>Try again?</html>", "Command failure")) {
                try {
                    log.debug("The user chose to execute the command again");
                    queue.putAtHead(event.getCommand());
                } catch (InterruptedException exc) {
                    log.warn("Cannot re-execute the failed command", exc);
                }
            } else {
                log.debug("The user chose to ignore the command");
            }
        }

        log.debug("commandExecuted() called");
    }

    @Override
    public void enableGetSelectedFilesMenuItem(boolean b) {
        String state = b ? "Enabling" : "Disabling";

        log.debug(state + " getSelectedFilesAction");
        getSelectedFilesAction.setEnabled(b);

        log.debug(state + " getAndRemoveSelectedFilesAction");
        getAndRemoveSelectedFilesAction.setEnabled(b);
    }

    @Override
    public void enableRemoveSelectedFilesMenuItem(boolean b) {
        String state = b ? "Enabling" : "Disabling";

        log.debug(state + " removeSelectedFilesAction");
        removeSelectedFilesAction.setEnabled(b);
    }

    @Override
    public void enableRefreshFolderMenuItem(boolean b) {
        String state = b ? "Enabling" : "Disabling";

        log.debug(state + " refreshSelectedFoldersAction");
        refreshSelectedFoldersAction.setEnabled(b);
    }

    @Override
    public void enableCreateFolderMenuItems(boolean b) {
        String state = b ? "Enabling" : "Disabling";

        log.debug(state + " createFolderAction");
        createFolderAction.setEnabled(b);
    }

    @Override
    public void enableSendFilesMenuItem(boolean b) {
        String state = b ? "Enabling" : "Disabling";

        log.debug(state + " sendFilesAction");
        sendFilesAction.setEnabled(b);
    }

    @Override
    public void enableFilePropertiesMenuItem(boolean b) {
        String state = b ? "Enabling" : "Disabling";

        log.debug(state + " showFilesProperties");
        showFilePropertiesAction.setEnabled(b);
    }

    @Override
    public JTree getFileSystemTree() {
        return window.getFileSystemTree();
    }

    @Override
    public OBEXElement getRoot() {
        return (OBEXElement) window.getFileSystemTree().getModel().getRoot();
    }

    @Override
    public OBEXElement getSelectedElement() {
        TreePath path = window.getFileSystemTree().getSelectionPath();

        if (path != null) {
            return (OBEXElement) path.getLastPathComponent();
        }
        return null;
    }

    @Override
    public List<OBEXElement> getSelectedElements() {
        List<OBEXElement> elements = new LinkedList<OBEXElement>();
        TreePath[] paths = window.getFileSystemTree().getSelectionPaths();

        if (paths != null) {
            for (TreePath path : paths) {
                elements.add((OBEXElement) path.getLastPathComponent());
            }
            return elements;
        }

        log.debug("There's no selected elements in the tree");
        return null;
    }

    @Override
    public void reloadNodeInTree(OBEXElement element) {
        if (element == null) {
            log.debug("No selected node to reload");
            return;
        }

        log.debug("Reloading information of " + element);

        JTree tree = window.getFileSystemTree();
        TreePath path = new TreePath(element.getPath());

        log.debug("Reloading the children files of " + element);

        tree.collapsePath(path);
        clearFetchedInfo(element);
        tree.expandPath(path);
    }

    @Override
    public void clearFetchedInfo(OBEXElement element) {
        log.debug("Removing fetched information of " + element);
        expansionListener.removeFetchedFolder(element);
        element.removeAllChildren();
        ((DefaultTreeModel) window.getFileSystemTree().getModel()).reload(element);
    }

    @Override
    public void showNode(OBEXElement element) {
        TreePath path = new TreePath(element.getPath());
        JTree tree = window.getFileSystemTree();

        if (tree.getModel() instanceof DefaultTreeModel) {
            ((DefaultTreeModel) window.getFileSystemTree().getModel()).reload(element);
            tree.expandPath(path);
        }
    }

    @Override
    public void collapseNode(OBEXElement element) {
        TreePath path = new TreePath(element.getPath());
        JTree tree = window.getFileSystemTree();

        tree.setSelectionPath(path);
        tree.collapsePath(path);

        ((DefaultTreeModel) tree.getModel()).reload(element);
    }

    @Override
    public void showWaitCursor(boolean b) {
        String state = b ? "Showing" : "Hiding";
        log.debug(state + " the wait cursor");

        if (b) {
            window.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        } else {
            window.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }

    /**
     * Show a confirmation message if the user tries to close the application
     * when a command is being processed.
     */
    public void frameClosing() {
        if (!queue.isEmpty() || commandConsumer.isWorking()) {
            if (showConfirmMessage("Interrupt processing?", "Exit")) {
                log.info("Closing the application");
                System.exit(0);
            }
        } else {
            log.info("Closing the application");
            System.exit(0);
        }

        log.info("Close operation aborted");
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" Action Getters ">
    /**
     * Get the createFolderAction object.
     * @return The createFolderAction object.
     */
    public CreateFolderAction getCreateFolderAction() {
        return createFolderAction;
    }

    /**
     * Get the exitApplicationAction object.
     * @return The exitApplicationAction object.
     */
    public QuitApplicationAction getExitApplicationAction() {
        return exitApplicationAction;
    }

    /**
     * Get the getAndRemoveSelectedFilesAction object.
     * @return The getAndRemoveSelectedFilesAction object.
     */
    public GetSelectedFilesAction getGetAndRemoveSelectedFilesAction() {
        return getAndRemoveSelectedFilesAction;
    }

    /**
     * Get the getSelectedFilesAction object.
     * @return The getSelectedFilesAction object.
     */
    public GetSelectedFilesAction getGetSelectedFilesAction() {
        return getSelectedFilesAction;
    }

    /**
     * Get the refreshSelectedFoldersAction object.
     * @return The refreshSelectedFoldersAction object.
     */
    public RefreshSelectedFoldersAction getRefreshSelectedFoldersAction() {
        return refreshSelectedFoldersAction;
    }

    /**
     * Get the removeSelectedFilesAction object.
     * @return The removeSelectedFilesAction object.
     */
    public RemoveSelectedFilesAction getRemoveSelectedFilesAction() {
        return removeSelectedFilesAction;
    }

    /**
     * Get the sendFilesAction object.
     * @return The sendFilesAction object.
     */
    public SendFilesAction getSendFilesAction() {
        return sendFilesAction;
    }

    /**
     * Get the showAboutDialogAction object.
     * @return The showAboutDialogAction object.
     */
    public ShowAboutDialogAction getShowAboutDialogAction() {
        return showAboutDialogAction;
    }

    /**
     * Get the showConfigurationsDialogAction object.
     * @return The showConfigurationsDialogAction object.
     */
    public ShowConfigurationsDialogAction getShowConfigurationsDialogAction() {
        return showConfigurationsDialogAction;
    }

    /**
     * Get the showFilePropertiesAction object.
     * @return The showFilePropertiesAction object.
     */
    public ShowFilePropertiesAction getShowFilePropertiesAction() {
        return showFilePropertiesAction;
    }

    /**
     * Get the showRootFilesAction object.
     * @return The showRootFilesAction object.
     */
    public ShowRootFilesAction getShowRootFilesAction() {
        return showRootFilesAction;
    }

    /**
     * Get the showDeviceInfoAction object.
     * @return The showDeviceInfoAction object.
     */
    public ShowDeviceInfoAction getShowDeviceInfoAction() {
        return showDeviceInfoAction;
    }
    // </editor-fold>
}