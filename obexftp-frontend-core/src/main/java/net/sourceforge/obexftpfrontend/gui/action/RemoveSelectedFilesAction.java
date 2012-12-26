package net.sourceforge.obexftpfrontend.gui.action;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import net.sourceforge.obexftpfrontend.gui.FileSystemTreeHolder;
import net.sourceforge.obexftpfrontend.model.ConfigurationHolder;
import net.sourceforge.obexftpfrontend.model.OBEXElement;
import net.sourceforge.obexftpfrontend.command.OBEXFTPCommandQueue;
import net.sourceforge.obexftpfrontend.command.ReloadNodeCommand;
import net.sourceforge.obexftpfrontend.command.RemoveFilesCommand;
import org.apache.log4j.Logger;

/**
 * Action that is responsible to create and add RemoveFilesCommand objects to
 * the command queue.
 * @author Daniel F. Martins
 */
public class RemoveSelectedFilesAction extends DefaultAction {

    /** Logger. */
    private static final Logger log = Logger.getLogger(RemoveSelectedFilesAction.class);
    /** Object that manages the user's preferences. */
    private ConfigurationHolder configHolder;
    /** Command queue to be used. */
    private OBEXFTPCommandQueue queue;
    /** Object that manages the file system tree component. */
    private FileSystemTreeHolder treeHolder;

    /**
     * Create a new instance of RemoveSelectedFilesAction.
     * @param configHolder Object that manages the user's preferences.
     * @param queue Command queue to be used.
     * @param parent Parent frame
     * @param treeHolder Object that manages the file system tree component.
     */
    public RemoveSelectedFilesAction(ConfigurationHolder configHolder, OBEXFTPCommandQueue queue, Window parentWindow, FileSystemTreeHolder treeHolder) {
        super(parentWindow);

        this.configHolder = configHolder;
        this.queue = queue;
        this.treeHolder = treeHolder;

        /* Action info */
        putValue(NAME, "Remove");
        putValue(SHORT_DESCRIPTION, "Remove the selected files");
        putValue(MNEMONIC_KEY, KeyEvent.VK_R);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
        putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/icon/remove.png")));
        putValue(LARGE_ICON_KEY, new ImageIcon(getClass().getResource("/icon/remove-tb.png")));
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (JOptionPane.showConfirmDialog(getParentWindow(), "<html><p><strong>This operation may corrupt the device's file system</strong>.</p><p>Continue anyway?</p></html>", "Remove files", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) != JOptionPane.YES_OPTION) {
            log.debug("Remove operation aborted");
            return;
        }

        List<OBEXElement> remove = getFilesToRemove();

        if (remove == null) {
            log.debug("No files to remove");
            return;
        }

        Map<OBEXElement, List<OBEXElement>> update = optimizeRequest(remove);

        addRemoveCommandsToQueue(update);
        addReloadCommandsToQueue(update);
    }

    /**
     * Get the list of files that the user wants to remove. This method
     * already handles redundant delete operations in order to optimize performance.
     * @return A List that contains the elements that should be removed.
     */
    private List<OBEXElement> getFilesToRemove() {
        if (treeHolder.getSelectedElements() == null) {
            return null;
        }

        List<OBEXElement> filesToRemove = treeHolder.getSelectedElements();
        for (OBEXElement file : treeHolder.getSelectedElements()) {
            removeRedundantOperations(file, filesToRemove);
        }

        return filesToRemove;
    }

    /**
     * Optimize the list of files to remove by discarding redundant operations (like
     * two operations that handles a parent and a child file, for example).
     * @param file File to check.
     * @param filesToRemove List of files selected by the user.
     */
    private void removeRedundantOperations(OBEXElement file, List<OBEXElement> filesToRemove) {
        OBEXElement parentFolder = file;
        log.debug("Removing redundant operations");

        while ((parentFolder = parentFolder.getParent()) != null) {

            if (filesToRemove.contains(parentFolder)) {
                filesToRemove.remove(file);
                file = parentFolder;
            }
        }
    }

    /**
     * Optimize the list of files to remove by grouping operations by parent
     * folder.
     * @param filesToRemove List of files to remove that was returned by the
     * removeRedundantOperations() method.
     * @return A Map that contains the selected files in a organized structure.
     */
    private Map<OBEXElement, List<OBEXElement>> optimizeRequest(List<OBEXElement> filesToRemove) {
        if (filesToRemove == null) {
            log.debug("No nodes to update");
            return null;
        }

        log.debug("Organizing the selected files by its parent folders");
        Map<OBEXElement, List<OBEXElement>> filesPerParentFolder = new HashMap<OBEXElement, List<OBEXElement>>();

        for (OBEXElement file : filesToRemove) {

            OBEXElement parentFolder = file.getParent();
            List<OBEXElement> children = filesPerParentFolder.get(parentFolder);

            if (children == null) {
                children = new LinkedList<OBEXElement>();
                filesPerParentFolder.put(parentFolder, children);
            }
            children.add(file);
        }

        return filesPerParentFolder;
    }

    /**
     * Add to queue the commands needed to remove the selected files.
     * @param filesPerParentFolder Files to remove organized by its parent folders.
     */
    private void addRemoveCommandsToQueue(Map<OBEXElement, List<OBEXElement>> filesPerParentFolder) {
        if (filesPerParentFolder == null) {
            return;
        }

        Iterator<OBEXElement> keyIterator = filesPerParentFolder.keySet().iterator();
        while (keyIterator.hasNext()) {
            OBEXElement parentFolder = keyIterator.next();
            try {
                log.debug("Adding a RemoveFilesCommand object to the queue");
                RemoveFilesCommand removeFilesCommand = new RemoveFilesCommand(configHolder, parentFolder, filesPerParentFolder.get(parentFolder));
                queue.append(removeFilesCommand);
            } catch (Exception exc) {
                log.warn("The action cannot be executed successfully due to some error in it\'s underlying commands", exc);
            }
        }
    }

    /**
     * Add to queue the commands needed to reload the folders affected by the remove operation.
     * @param filesPerParentFolder Elements to reload.
     */
    private void addReloadCommandsToQueue(Map<OBEXElement, List<OBEXElement>> filesPerParentFolder) {
        Iterator<OBEXElement> keyIterator = filesPerParentFolder.keySet().iterator();
        while (keyIterator.hasNext()) {
            OBEXElement parentFolder = keyIterator.next();

            try {
                log.debug("Adding a ReloadNodeCommand object to the queue");

                ReloadNodeCommand reloadNodeCommand = new ReloadNodeCommand(configHolder, parentFolder, treeHolder);
                queue.append(reloadNodeCommand);
            } catch (Exception exc) {
                log.warn("The action cannot be executed successfully due to some error in it's underlying commands", exc);
            }
        }
    }
}