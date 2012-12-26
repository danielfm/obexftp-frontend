package net.sourceforge.obexftpfrontend.model;

import net.sourceforge.obexftpfrontend.gui.model.OBEXFileSystemTreeModel;
import javax.swing.tree.DefaultTreeModel;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * FileSystemTreeModel test cases.
 * @author Daniel F. Martins
 */
public class FileSystemTreeModelTest {

    @Test
    public void testCreate() {
        DefaultTreeModel model = new OBEXFileSystemTreeModel();
        assertNotNull(model.getRoot());
    }

    @Test
    public void testIsLeaf() {
        DefaultTreeModel model = new OBEXFileSystemTreeModel();

        assertTrue(model.isLeaf(null));
        assertFalse(model.isLeaf(new OBEXElement("/")));
        assertTrue(model.isLeaf("somenonsensehere"));

        OBEXElement leaf = new OBEXElement("/");
        leaf.setType(OBEXElement.OBEXElementType.FILE);

        assertTrue(model.isLeaf(leaf));
    }
}
