package net.sourceforge.obexftpfrontend.gui.action;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.tree.TreePath;
import net.sourceforge.obexftpfrontend.gui.FileSystemTreeHolder;
import net.sourceforge.obexftpfrontend.model.OBEXElement;
import org.apache.log4j.Logger;

/**
 * Action that is responsible to fetch the device's root files and update the
 * file system tree component.
 * @author Daniel F. Martins
 */
public class ShowRootFilesAction extends DefaultAction {

    /** Logger. */
    private static final Logger log = Logger.getLogger(ShowRootFilesAction.class);
    /** Object that manages the file system tree component. */
    private FileSystemTreeHolder treeHolder;

    /**
     * Create a new instance of ShowRootFilesAction.
     * @param treeHolder Object that manages the file system tree component.
     */
    public ShowRootFilesAction(FileSystemTreeHolder treeHolder) {
        super(null);

        this.treeHolder = treeHolder;

        /* Action info */
        putValue(NAME, "Query root files");
        putValue(SHORT_DESCRIPTION, "Show the device's root files");
        putValue(MNEMONIC_KEY, KeyEvent.VK_Q);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
        putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/icon/query-root-files.png")));
        putValue(LARGE_ICON_KEY, new ImageIcon(getClass().getResource("/icon/query-root-files-tb.png")));
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        JTree fileSystemTree = treeHolder.getFileSystemTree();
        OBEXElement root = (OBEXElement) fileSystemTree.getModel().getRoot();
        TreePath rootPath = new TreePath(root.getPath());

        log.info("Querying for the device's root files");
        treeHolder.clearFetchedInfo(root);

        fileSystemTree.collapsePath(rootPath);
        fileSystemTree.fireTreeExpanded(rootPath);
    }
}