package net.sourceforge.obexftpfrontend.gui.action;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;
import net.sourceforge.obexftpfrontend.gui.FileSystemTreeHolder;
import net.sourceforge.obexftpfrontend.model.ConfigurationHolder;
import net.sourceforge.obexftpfrontend.model.OBEXElement;
import net.sourceforge.obexftpfrontend.command.OBEXFTPCommandQueue;
import net.sourceforge.obexftpfrontend.command.ReloadNodeCommand;
import org.apache.log4j.Logger;

/**
 * Action that is responsible to create and add ReloadNodeCommand objects to
 * the command queue.
 * @author Daniel F. Martins
 */
public class RefreshSelectedFoldersAction extends DefaultAction {

    /** Logger. */
    private static final Logger log = Logger.getLogger(RefreshSelectedFoldersAction.class);
    /** Object that manages the user's preferences. */
    private ConfigurationHolder configHolder;
    /** Command queue to be used. */
    private OBEXFTPCommandQueue queue;
    /** Object that manages the file system tree component. */
    private FileSystemTreeHolder treeHolder;

    /**
     * Create a new instance of RefreshSelectedFoldersActions.
     * @param configHolder Object that manages the user's preferenes.
     * @param queue Command queue to be used.
     * @param treeHolder Object that manages the file system tree component.
     */
    public RefreshSelectedFoldersAction(ConfigurationHolder configHolder, OBEXFTPCommandQueue queue, FileSystemTreeHolder treeHolder) {
        super(null);

        this.configHolder = configHolder;
        this.queue = queue;
        this.treeHolder = treeHolder;

        /* Action info */
        putValue(NAME, "Refresh selected folders");
        putValue(SHORT_DESCRIPTION, "Refresh the selected folders");
        putValue(MNEMONIC_KEY, KeyEvent.VK_S);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F5, InputEvent.CTRL_MASK));
        putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/icon/refresh.png")));
        putValue(LARGE_ICON_KEY, new ImageIcon(getClass().getResource("/icon/refresh-tb.png")));
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        for (OBEXElement element : treeHolder.getSelectedElements()) {
            if (element.getType() == OBEXElement.OBEXElementType.FOLDER) {
                try {
                    log.debug("Adding a new ReloadNodeCommand object to the queue");
                    queue.append(new ReloadNodeCommand(configHolder, element, treeHolder));
                } catch (Exception exc) {
                    log.warn("The action cannot be executed successfully due to some error in it\'s underlying commands", exc);
                }
            }
        }
    }
}