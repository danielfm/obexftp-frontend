package net.sourceforge.obexftpfrontend.command;

import java.util.List;
import net.sourceforge.obexftpfrontend.model.ConfigurationHolder;
import net.sourceforge.obexftpfrontend.model.OBEXElement;
import net.sourceforge.obexftpfrontend.obexftp.OBEXException;
import net.sourceforge.obexftpfrontend.obexftp.OBEXFTPException;
import org.apache.log4j.Logger;

/**
 * The application creates this command and adds it to the command queue when
 * the user wants to create a folder in the device's file system.
 * @author Daniel F. Martins
 */
public class CreateFolderCommand extends AbstractOBEXFTPCommand<Object> {

    /** Logger. */
    private static final Logger log = Logger.getLogger(CreateFolderCommand.class);
    /** Parent folder */
    private OBEXElement folder;
    /** Folder to create */
    private OBEXElement newFolder;

    /**
     * Create a new instance of CreateFolderCommand.
     * @param configHolder Component that manages the application configuration.
     * @param folder Parent folder where the folder will be created.
     * @param newFolder New folder to create.
     * @throws IllegalArgumentException if some argument is invalid.
     */
    public CreateFolderCommand(ConfigurationHolder configHolder, OBEXElement folder, OBEXElement newFolder) {
        super(configHolder, "Creating folder" + ((newFolder != null) ? " " + newFolder.getName() : ""));

        if (newFolder == null) {
            throw new IllegalArgumentException("The newFolder argument cannot be null");
        }

        this.folder = folder;
        this.newFolder = newFolder;
    }

    @Override
    public Object execute() throws OBEXFTPException, OBEXException, InterruptedException {
        List<String> args = convertPath(folder);
        args.add("--mkdir");
        args.add(newFolder.getName());

        Thread.sleep(1);
        log.debug("Trying to create a folder in the device");
        runCommand(null, args, false);
        log.info("Folder created");

        return null;
    }
}