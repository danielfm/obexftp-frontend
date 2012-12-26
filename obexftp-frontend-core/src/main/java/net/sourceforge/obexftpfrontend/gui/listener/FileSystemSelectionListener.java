package net.sourceforge.obexftpfrontend.gui.listener;

import net.sourceforge.obexftpfrontend.gui.*;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;
import net.sourceforge.obexftpfrontend.model.OBEXElement;

/**
 * Implementation of TreeSelectionListener that is used to update
 * the enablement of application's context menus.
 * @author Daniel F. Martins
 */
public class FileSystemSelectionListener implements TreeSelectionListener {

    /** Object that controls menu item's enablement. */
    private ContextMenuHandler handler;

    /**
     * Create a new instance of FileSystemSelectionListener.
     * @param handler Object that handles the context menu itself.
     */
    public FileSystemSelectionListener(ContextMenuHandler handler) {
        super();
        this.handler = handler;
    }

    @Override
    public void valueChanged(TreeSelectionEvent event) {
        JTree jtree = (JTree) event.getSource();

        boolean hasSelection = jtree.getSelectionCount() > 0;
        boolean hasOneSelection = jtree.getSelectionCount() == 1;

        TreePath[] paths = jtree.getSelectionPaths();

        int folderCount = 0;
        int fileCount = 0;

        if (paths != null) {
            for (TreePath path : paths) {
                if (((OBEXElement) path.getLastPathComponent()).getType() == OBEXElement.OBEXElementType.FOLDER) {
                    folderCount++;
                } else {
                    fileCount++;
                }
            }
        }

        /* change menu item enablement basing on the user selection */
        handler.enableFilePropertiesMenuItem(hasOneSelection);
        handler.enableGetSelectedFilesMenuItem(folderCount == 0 && fileCount > 0);
        handler.enableRemoveSelectedFilesMenuItem(folderCount > 0 || fileCount > 0);
        handler.enableRefreshFolderMenuItem(folderCount > 0);
        handler.enableCreateFolderMenuItems(!hasSelection || folderCount == 1 && fileCount == 0);
        handler.enableSendFilesMenuItem(!hasSelection || folderCount == 1 && fileCount == 0);
    }
}