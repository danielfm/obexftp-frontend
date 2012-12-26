package net.sourceforge.obexftpfrontend.command;

import java.io.File;
import java.util.List;
import net.sourceforge.obexftpfrontend.model.ConfigurationHolder;
import net.sourceforge.obexftpfrontend.model.OBEXElement;
import net.sourceforge.obexftpfrontend.obexftp.OBEXException;
import net.sourceforge.obexftpfrontend.obexftp.OBEXFTPException;
import org.apache.log4j.Logger;

/**
 * The application creates this command and adds it to the queue when the user
 * whants to download files from the device.
 * @author Daniel F. Martins
 */
public class GetFilesCommand extends AbstractOBEXFTPCommand<Object> {

    private static final Logger log = Logger.getLogger(GetFilesCommand.class);
    private File localFolder;
    private OBEXElement folder;
    private List<OBEXElement> files;
    private boolean delete;

    /**
     * Create a new instance of GetFilesCommand.
     * @param configHolder Component that manages the configuration.
     * @param localFolder Local folder to save the files.
     * @param parentFolder Parent folder.
     * @param files Files to download.
     * @param delete Whether the selected files should be removed after
     * the download.
     * @throws IllegalArgumentException if some argument is invalid.
     */
    public GetFilesCommand(ConfigurationHolder configHolder, File localFolder, OBEXElement parentFolder, List<OBEXElement> files, boolean delete) {
        super(configHolder, "Getting files" + ((parentFolder != null) ? " from the " + parentFolder.getName() + " folder" : ""));

        if (parentFolder == null) {
            throw new IllegalArgumentException("The folder argument cannot be null");
        }

        if (files == null || files.size() == 0) {
            throw new IllegalArgumentException("The files argument cannot be null or empty");
        }

        if (localFolder == null || (localFolder != null && !localFolder.isDirectory())) {
            throw new IllegalArgumentException("The localFolder argument should point to a directory");
        }

        this.localFolder = localFolder;
        this.folder = parentFolder;
        this.files = files;
        this.delete = delete;
    }

    @Override
    public Object execute() throws OBEXFTPException, OBEXException, InterruptedException {
        List<String> args = convertPath(folder);
        for (OBEXElement file : files) {
            String command = "--get";
            if (delete) {
                command += "delete";
            }
            args.add(command);
            args.add(file.getName());
        }

        Thread.sleep(1);
        log.info("Trying to download a file from the device");
        runCommand(localFolder, args, true);

        restoreLastModifiedDate();

        return null;
    }

    /**
     * This method is called after fetch the files from the device in order to
     * restore the last modified attribute.
     */
    private void restoreLastModifiedDate() {
        for (OBEXElement file : files) {
            File localFile = new File(localFolder + "/" + file.getName());
            if (localFile.exists() && file.getModified() != null) {
                localFile.setLastModified(file.getModified().getTime());
            }
        }
    }
}