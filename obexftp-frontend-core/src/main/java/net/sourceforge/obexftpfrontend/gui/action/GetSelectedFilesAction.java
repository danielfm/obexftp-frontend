package net.sourceforge.obexftpfrontend.gui.action;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.KeyStroke;
import net.sourceforge.obexftpfrontend.gui.FileSystemTreeHolder;
import net.sourceforge.obexftpfrontend.model.ConfigurationHolder;
import net.sourceforge.obexftpfrontend.model.OBEXElement;
import net.sourceforge.obexftpfrontend.command.OBEXFTPCommandQueue;
import net.sourceforge.obexftpfrontend.command.GetFilesCommand;
import net.sourceforge.obexftpfrontend.command.ReloadNodeCommand;
import org.apache.log4j.Logger;

/**
 * Action that is responsible to create and add GetFilesCommand objects to the
 * command queue.
 * @author Daniel F. Martins
 */
public class GetSelectedFilesAction extends DefaultAction {

    /** Logger. */
    private static final Logger log = Logger.getLogger(GetSelectedFilesAction.class);
    /** Object that manages the user's preferences. */
    private ConfigurationHolder configHolder;
    /** Command queue to be used. */
    private OBEXFTPCommandQueue queue;
    /** Object that manages the file system tree component. */
    private FileSystemTreeHolder treeHolder;
    /** Whether the fetched files should be also removed from the device. */
    private boolean removeFetchedFiles;
    /** File chooser dialog. */
    private JFileChooser chooser;

    /**
     * Create a new instance of GetSelectedFilesAction.
     * @param parent Parent frame.
     * @param configHolder Object that manages the user's preferences.
     * @param queue Command queue to be used.
     * @param treeHolder Object that manages the file system tree component.
     * @param removeFetchedFiles Whether the fetched files should be also
     * removed from the device.
     */
    public GetSelectedFilesAction(Window parentWindow, ConfigurationHolder configHolder, OBEXFTPCommandQueue queue, FileSystemTreeHolder treeHolder, boolean removeFetchedFiles) {
        super(parentWindow);

        this.configHolder = configHolder;
        this.queue = queue;
        this.treeHolder = treeHolder;
        this.removeFetchedFiles = removeFetchedFiles;

        setUpFileChooserDialog();

        /* action info */
        if (removeFetchedFiles) {
            putValue(NAME, "Get and remove...");
            putValue(SHORT_DESCRIPTION, "Get and remove the selected files");
            putValue(MNEMONIC_KEY, KeyEvent.VK_N);
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.SHIFT_MASK + InputEvent.CTRL_MASK));
        } else {
            putValue(NAME, "Get...");
            putValue(SHORT_DESCRIPTION, "Get the selected files");
            putValue(MNEMONIC_KEY, KeyEvent.VK_G);
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
            putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/icon/get.png")));
            putValue(LARGE_ICON_KEY, new ImageIcon(getClass().getResource("/icon/get-tb.png")));
        }
    }

    /**
     * Set up the file chooser dialog.
     */
    private void setUpFileChooserDialog() {
        chooser = new JFileChooser();
        chooser.setDialogTitle("Select the target folder...");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        File targetDir = getTargetFolder();
        if (targetDir == null) {
            return;
        }

        Map<OBEXElement, List<OBEXElement>> filesPerParentFolder = getFilesPerParentFolder();

        if (filesPerParentFolder != null) {
            addGetCommandsToQueue(filesPerParentFolder, targetDir, removeFetchedFiles);
            if (removeFetchedFiles) {
                addReloadCommandsToQueue(filesPerParentFolder);
            }
        }
    }

    /**
     * Retrieve a Map of the selected files in the file system tree organized
     * by its parent folders.
     * @return A Map that contains the selected files. Null is returned when
     * there's no selection in the file system tree.
     */
    private Map<OBEXElement, List<OBEXElement>> getFilesPerParentFolder() {
        log.debug("Organizing the selected files");
        Map<OBEXElement, List<OBEXElement>> filesPerParentFolder = new HashMap<OBEXElement, List<OBEXElement>>();

        List<OBEXElement> selected = treeHolder.getSelectedElements();

        if (selected == null) {
            log.debug("No selected files");
            return null;
        }

        for (OBEXElement current : selected) {
            OBEXElement currentParent = current.getParent();
            List<OBEXElement> children = filesPerParentFolder.get(currentParent);

            if (children == null) {
                children = new LinkedList<OBEXElement>();
                filesPerParentFolder.put(currentParent, children);
            }

            children.add(current);
        }

        return filesPerParentFolder;
    }

    /**
     * Display the dialog which the user selects the target directory where
     * the files will be downloaded.
     * @return Selected file or null, if no files where selected. It returns
     * null if the user cancel the operation.
     */
    private File getTargetFolder() {
        if (chooser.showOpenDialog(getParentWindow()) == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        }

        return null;
    }

    /**
     * Add the get commands to queue.
     * @param filesPerParentFolder Files to be downloaded, organized by its parent
     * folders.
     * @param targetDir Local directory where the files will be saved.
     * @param delete Whether those files should be deleted.
     */
    private void addGetCommandsToQueue(Map<OBEXElement, List<OBEXElement>> filesPerParentFolder, File targetDir, boolean delete) {
        Iterator<OBEXElement> keyIterator = filesPerParentFolder.keySet().iterator();

        while (keyIterator.hasNext()) {
            OBEXElement current = keyIterator.next();
            List<OBEXElement> children = filesPerParentFolder.get(current);

            try {
                log.debug("Creating a GetFilesCommand object");
                GetFilesCommand getFilesCommand = new GetFilesCommand(configHolder, targetDir, current, children, delete);

                log.debug("Adding the GetFilesCommand object to the queue");
                queue.append(getFilesCommand);
            } catch (Exception exc) {
                log.warn("The action cannot be executed successfully due to some error in it\'s underlying commands", exc);
            }
        }
    }

    /**
     * Add reload commands to queue. This method is called when some files was deleted, so the
     * file system tree should be updated.
     * @param filesPerParentFolder List of files to reload in the file system tree.
     */
    private void addReloadCommandsToQueue(Map<OBEXElement, List<OBEXElement>> filesPerParentFolder) {
        Iterator<OBEXElement> keyIterator = filesPerParentFolder.keySet().iterator();

        while (keyIterator.hasNext()) {
            OBEXElement current = keyIterator.next();

            try {
                log.debug("Creating a ReloadNodeCommand object");
                ReloadNodeCommand reloadNodeCommand = new ReloadNodeCommand(configHolder, current, treeHolder);

                log.debug("Adding the ReloadNodeCommand object to the queue");
                queue.append(reloadNodeCommand);
            } catch (Exception exc) {
                log.warn("The action cannot be executed successfully due to some error in it\'s underlying commands", exc);
            }
        }
    }
}