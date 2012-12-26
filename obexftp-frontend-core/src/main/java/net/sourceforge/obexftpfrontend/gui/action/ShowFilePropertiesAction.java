package net.sourceforge.obexftpfrontend.gui.action;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.KeyStroke;
import javax.swing.tree.TreePath;
import net.sourceforge.obexftpfrontend.gui.FilePropertiesDialog;
import net.sourceforge.obexftpfrontend.gui.FileSystemTreeHolder;
import net.sourceforge.obexftpfrontend.model.OBEXElement;
import org.apache.log4j.Logger;

/**
 * Action that is responsible to create and show the 'file properties' dialog.
 * @author Daniel F. Martins
 */
public class ShowFilePropertiesAction extends DefaultAction {

    /** Logger. */
    private static final Logger log = Logger.getLogger(ShowFilePropertiesAction.class);
    /** Object that manages the file system tree component. */
    private FileSystemTreeHolder treeHolder;

    /**
     * Create a new instance of ShowFilePropertiesAction.
     * @param parent Parent frame.
     * @param treeHolder Object that manages the file system tree component.
     */
    public ShowFilePropertiesAction(Window parent, FileSystemTreeHolder treeHolder) {
        super(new FilePropertiesDialog(parent));

        this.treeHolder = treeHolder;

        /* Action info */
        putValue(NAME, "File properties...");
        putValue(SHORT_DESCRIPTION, "Show file's properties");
        putValue(MNEMONIC_KEY, KeyEvent.VK_P);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0));
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        TreePath path = treeHolder.getFileSystemTree().getSelectionPath();

        if (path != null) {
            OBEXElement element = (OBEXElement) path.getLastPathComponent();
            ((FilePropertiesDialog) getParentWindow()).setFile(element);
            getParentWindow().setVisible(true);
        } else {
            log.debug("Cannot display properties when no file is selected");
        }
    }
}