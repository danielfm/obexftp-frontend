package net.sourceforge.obexftpfrontend.gui.model;

import net.sourceforge.obexftpfrontend.model.*;
import javax.swing.tree.DefaultTreeModel;

/**
 * Custom TreeModel that used to create a representation of the device's file
 * system.
 * @author Daniel F. Martins
 */
public class OBEXFileSystemTreeModel extends DefaultTreeModel {

    /**
     * Create a new instance of FileSystemTreeModel.
     */
    public OBEXFileSystemTreeModel() {
        super(new OBEXElement("/")); // root node

    }

    @Override
    public boolean isLeaf(Object element) {
        if (element == null || !(element instanceof OBEXElement)) {
            return true;
        }

        OBEXElement node = (OBEXElement) element;
        return node.getType() != OBEXElement.OBEXElementType.FOLDER;
    }
}