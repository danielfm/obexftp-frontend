package net.sourceforge.obexftpfrontend.command;

import java.util.List;
import net.sourceforge.obexftpfrontend.model.ConfigurationHolder;
import net.sourceforge.obexftpfrontend.model.OBEXElement;
import net.sourceforge.obexftpfrontend.obexftp.OBEXException;
import net.sourceforge.obexftpfrontend.obexftp.OBEXFTPException;
import org.apache.log4j.Logger;

/**
 * The application creates this command and adds it to the command queue when
 * the user wants to remove files from the device.
 * @author Daniel F. Martins
 */
public class RemoveFilesCommand extends AbstractOBEXFTPCommand<Object> {

    /** Logger. */
    private static final Logger log = Logger.getLogger(RemoveFilesCommand.class);
    /** File's parent folder. */
    private OBEXElement folder;
    /** Files to remove. */
    private List<OBEXElement> files;

    /**
     * Create a new instance of RemoveFilesCommand.
     * @param configHolder Component that manages the configuration.
     * @param parentFolder Parent folder of the files.
     * @param files Files to remove.
     * @throws IllegalArgumentException if some argument is invalid.
     */
    public RemoveFilesCommand(ConfigurationHolder configHolder, OBEXElement parentFolder, List<OBEXElement> files) {
        super(configHolder, "Deleting files" + ((parentFolder != null) ? " under the " + parentFolder.getName() + " folder" : ""));

        if (parentFolder == null) {
            throw new IllegalArgumentException("The folder argument cannot be null");
        }

        if (files == null || files.size() == 0) {
            throw new IllegalArgumentException("The files argument cannot be null or empty");
        }

        this.folder = parentFolder;
        this.files = files;
    }

    @Override
    public Object execute() throws OBEXFTPException, OBEXException, InterruptedException {
        List<String> args = convertPath(folder);
        for (OBEXElement file : files) {
            args.add("--delete");
            args.add(file.getName());
        }

        Thread.sleep(1);
        log.info("Trying to delete a file from the device");
        runCommand(null, args, true);

        return null;
    }
}