package net.sourceforge.obexftpfrontend.gui;

import java.util.List;
import javax.swing.JTree;
import net.sourceforge.obexftpfrontend.model.OBEXElement;

/**
 * The component that manages the file system tree component must
 * implement this interface.
 * @author Daniel F. Martins
 */
public interface FileSystemTreeHolder {

    /**
     * Get the instance of the file system tree component itself.
     * @return JTree instance that represents the device's
     * file system.
     */
    JTree getFileSystemTree();

    /**
     * Remove the cached information about the given file.
     * @param element File to be removed from the cache.
     */
    void clearFetchedInfo(OBEXElement element);

    /**
     * Get the tree's root element.
     * @return Root element of the tree.
     */
    OBEXElement getRoot();

    /**
     * Get the selected element in the tree.
     * @return Selected element in the tree.
     */
    OBEXElement getSelectedElement();

    /**
     * Get the selected elements in the tree.
     * @return Selected elements in the tree.
     */
    List<OBEXElement> getSelectedElements();

    /**
     * Reload the given node in the tree.
     * @param element Node to reload.
     */
    void reloadNodeInTree(OBEXElement element);

    /**
     * Reload and display the given node in the tree.
     * @param element Element to be shown.
     */
    void showNode(OBEXElement element);

    /**
     * Collapse the given node in the tree.
     * @param element Node to collapse.
     */
    void collapseNode(OBEXElement element);

    /**
     * Show the wait cursor when performing some time-demanding task.
     * @param b Whether the cursor should be the default cursor or the
     * wait cursor.
     */
    void showWaitCursor(boolean b);
}