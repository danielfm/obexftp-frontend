package net.sourceforge.obexftpfrontend.gui.action;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;
import net.sourceforge.obexftpfrontend.gui.FileSystemTreeHolder;
import net.sourceforge.obexftpfrontend.model.ConfigurationHolder;
import net.sourceforge.obexftpfrontend.model.OBEXElement;
import net.sourceforge.obexftpfrontend.command.OBEXFTPCommandQueue;
import net.sourceforge.obexftpfrontend.command.CreateFolderCommand;
import net.sourceforge.obexftpfrontend.command.ReloadNodeCommand;
import org.apache.log4j.Logger;

/**
 * Action that is responsible to create and add CreateFolderCommand objects to
 * the command queue.
 * @author Daniel F. Martins
 */
public class CreateFolderAction extends DefaultAction {

    /** Logger. */
    private static final Logger log = Logger.getLogger(CreateFolderAction.class);
    /** Object that manages the user's prefences. */
    private ConfigurationHolder configHolder;
    /** Object that manages the file system tree component. */
    private FileSystemTreeHolder treeHolder;
    /** Command queue to be used. */
    private OBEXFTPCommandQueue queue;

    /**
     * Create a new instance of CreateFolderAction.
     * @param parent Parent frame.
     * @param configHolder Object that manages the user's preferences.
     * @param treeHolder Object that manages the file system tree component.
     * @param queue Command queue to be used.
     */
    public CreateFolderAction(Window parentWindow, ConfigurationHolder configHolder, FileSystemTreeHolder treeHolder, OBEXFTPCommandQueue queue) {
        super(parentWindow);

        this.configHolder = configHolder;
        this.treeHolder = treeHolder;
        this.queue = queue;

        /* Action info */
        putValue(NAME, "Create folder...");
        putValue(SHORT_DESCRIPTION, "Create a folder within the selected folder");
        putValue(MNEMONIC_KEY, KeyEvent.VK_C);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_INSERT, 0));
        putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/icon/create-folder.png")));
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        OBEXElement folder = null;

        folder = treeHolder.getSelectedElement();
        if (folder == null) {
            log.debug("No selected folder, using the root");
            folder = treeHolder.getRoot();
        }

        String folderName = showInputMessage("Type the folder name:");
        if (folderName == null || "".equals(folderName.trim())) {
            log.debug("Invalid folder name. Aborting");
            return;
        }

        try {
            log.debug("Putting the commands in the queue");

            CreateFolderCommand createFolderCommand = new CreateFolderCommand(configHolder, folder, new OBEXElement(folderName));
            ReloadNodeCommand reloadCommand = new ReloadNodeCommand(configHolder, folder, treeHolder);

            queue.append(createFolderCommand, reloadCommand);
        } catch (Exception exc) {
            log.warn("The action cannot be executed successfully due to some error in it's underlying commands", exc);
        }
    }
}