package net.sourceforge.obexftpfrontend.command;

import net.sourceforge.obexftpfrontend.gui.FileSystemTreeHolder;
import net.sourceforge.obexftpfrontend.model.ConfigurationHolder;
import net.sourceforge.obexftpfrontend.model.OBEXElement;
import net.sourceforge.obexftpfrontend.obexftp.OBEXException;
import net.sourceforge.obexftpfrontend.obexftp.OBEXFTPException;
import org.apache.log4j.Logger;

/**
 * The application creates this command and adds it to the command queue when
 * the user wants to reload the file list of a given folder.
 * @author Daniel F. Martins
 */
public class ReloadNodeCommand extends AbstractOBEXFTPCommand<OBEXElement> {

    /** Logger. */
    private static final Logger log = Logger.getLogger(ReloadNodeCommand.class);
    /** Folder to reload. */
    private OBEXElement folder;
    /** Component that manages the file system tree component. */
    private FileSystemTreeHolder treeHolder;

    /**
     * Create a new instance of ReloadNodeCommand.
     * @param configHolder Component that manages the configuration.
     * @param folder Folder to reload.
     * @param treeHolder Component that manages the file system tree component.
     * @throws IllegalArgumentException if some argument is invalid.
     */
    public ReloadNodeCommand(ConfigurationHolder configHolder, OBEXElement folder, FileSystemTreeHolder treeHolder) {
        super(configHolder, "Reloading" + ((folder != null) ? " " + folder.getName() + " tree node" : ""));

        if (treeHolder == null) {
            throw new IllegalArgumentException("The treeHolder argument cannot be null");
        }

        this.folder = folder;
        this.treeHolder = treeHolder;
    }

    @Override
    public OBEXElement execute() throws OBEXFTPException, OBEXException, InterruptedException {
        if (folder == null) {
            log.debug("No folder to reload");
            return null;
        }

        log.info("Reloading the list of files from a folder");
        treeHolder.reloadNodeInTree(folder);
        Thread.sleep(1);

        return null;
    }
}