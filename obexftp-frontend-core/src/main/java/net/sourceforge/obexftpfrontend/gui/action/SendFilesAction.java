package net.sourceforge.obexftpfrontend.gui.action;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.KeyStroke;
import net.sourceforge.obexftpfrontend.gui.FileSystemTreeHolder;
import net.sourceforge.obexftpfrontend.model.ConfigurationHolder;
import net.sourceforge.obexftpfrontend.model.OBEXElement;
import net.sourceforge.obexftpfrontend.command.OBEXFTPCommandQueue;
import net.sourceforge.obexftpfrontend.command.ReloadNodeCommand;
import net.sourceforge.obexftpfrontend.command.SendFilesCommand;
import org.apache.log4j.Logger;

/**
 * Action that is responsible to create and add SendFilesCommand objects to the
 * command queue.
 * @author Daniel F. Martins
 */
public class SendFilesAction extends DefaultAction {

    /** Logger. */
    private static final Logger log = Logger.getLogger(SendFilesAction.class);
    /** Object that manages the user's preferences. */
    private ConfigurationHolder configHolder;
    /** Command queue to be used. */
    private OBEXFTPCommandQueue queue;
    /** Object that manages the file system tree component. */
    private FileSystemTreeHolder treeHolder;
    /** File chooser dialog. */
    private JFileChooser chooser;

    /**
     * Create a new instance of SendFilesActions.
     * @param configHolder Object that manages the user's preferences.
     * @param queue Command queue to be used.
     * @param parent Parent frame.
     * @param treeHolder Object that manages the file system tree component.
     */
    public SendFilesAction(ConfigurationHolder configHolder, OBEXFTPCommandQueue queue, Window parentWindow, FileSystemTreeHolder treeHolder) {
        super(parentWindow);

        this.configHolder = configHolder;
        this.queue = queue;
        this.treeHolder = treeHolder;

        setUpFileChooser();

        /* Action info */
        putValue(NAME, "Send files...");
        putValue(SHORT_DESCRIPTION, "Send files to the selected folder");
        putValue(MNEMONIC_KEY, KeyEvent.VK_S);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.CTRL_MASK));
        putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/icon/send.png")));
        putValue(LARGE_ICON_KEY, new ImageIcon(getClass().getResource("/icon/send-tb.png")));
    }

    /**
     * Set up the file chooser component.
     */
    private void setUpFileChooser() {
        chooser = new JFileChooser();
        chooser.setMultiSelectionEnabled(true);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        List<File> selectedFiles = getSelectedFiles();
        OBEXElement selectedFolder = treeHolder.getSelectedElement();

        if (selectedFiles == null) {
            log.debug("No selected files");
            return;
        }

        if (selectedFolder == null || (selectedFolder != null && selectedFolder.getType() == OBEXElement.OBEXElementType.FOLDER)) {
            SendFilesCommand sendFilesCommand = new SendFilesCommand(configHolder, selectedFolder, selectedFiles);
            ReloadNodeCommand reloadNodeCommand = new ReloadNodeCommand(configHolder, selectedFolder, treeHolder);

            try {
                log.debug("Adding commands to the queue");
                queue.append(sendFilesCommand, reloadNodeCommand);
            } catch (Exception exc) {
                log.warn("The action cannot be executed successfully due to some error in it's underlying commands", exc);
            }
        }
    }

    /**
     * Display the dialog which the user selects the files to be uploaded to
     * the device.
     * @return Selected files or null, if no files where selected. It returns
     * null if the user cancel the operation.
     */
    private List<File> getSelectedFiles() {
        List<File> selectedFiles = null;

        if (chooser.showOpenDialog(getParentWindow()) != JFileChooser.APPROVE_OPTION) {
            log.debug("Aborting the operation");
            return null;
        }

        if (chooser.getSelectedFiles() != null) {
            selectedFiles = new LinkedList<File>();

            for (File file : chooser.getSelectedFiles()) {
                if (file.isFile() && file.exists()) {
                    log.debug("Adding valid file to the selected files list");
                    selectedFiles.add(file);
                } else {
                    log.debug("Ignoring invalid file");
                }
            }
        }

        return selectedFiles;
    }
}