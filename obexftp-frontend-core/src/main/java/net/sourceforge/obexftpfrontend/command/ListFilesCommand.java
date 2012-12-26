package net.sourceforge.obexftpfrontend.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import net.sourceforge.obexftpfrontend.model.ConfigurationHolder;
import net.sourceforge.obexftpfrontend.model.OBEXElement;
import net.sourceforge.obexftpfrontend.obexftp.OBEXException;
import net.sourceforge.obexftpfrontend.obexftp.OBEXFTPException;
import net.sourceforge.obexftpfrontend.obexftp.OBEXResponseParser;
import org.apache.log4j.Logger;

/**
 * The application creates this command and adds it to the command queue when
 * the user wants to list the files of a given folder.
 * @author Daniel F. Martins
 */
public class ListFilesCommand extends AbstractOBEXFTPCommand<OBEXElement> {

    /** Logger. */
    private static final Logger log = Logger.getLogger(ListFilesCommand.class);
    /** Object used to parse 'obexftp' response. */
    private OBEXResponseParser parser;
    /** Folder to list the files. */
    private OBEXElement folder;

    /**
     * Create a new instance of ListFilesCommand.
     * @param parser OBEXResponseParser object.
     * @param configHolder Component that manages the configuration.
     * @param folder Folder to use.
     * @throws IllegalArgumentException if some argument is invalid.
     */
    public ListFilesCommand(OBEXResponseParser parser, ConfigurationHolder configHolder, OBEXElement folder) {
        super(configHolder, "Listing files" + ((folder != null) ? " under the " + folder.getName() + " folder" : ""));

        if (parser == null) {
            throw new IllegalArgumentException("The parser argument cannot be null");
        }

        if (folder == null) {
            throw new IllegalArgumentException("The folder argument cannot be null");
        }

        this.parser = parser;
        this.folder = folder;
    }

    @Override
    public OBEXElement execute() throws OBEXFTPException, OBEXException, InterruptedException {
        List<String> args = convertPath(folder);
        args.add("--list");

        log.info("Trying to list files from a path");
        Thread.sleep(1);
        Process process = runCommand(null, args, false);

        Thread.sleep(1);
        BufferedReader stream = new BufferedReader(new InputStreamReader(process.getInputStream()));

        log.debug("Parsing the obexftp response XML");
        OBEXElement element = parser.parseResponse(stream, folder);

        try {
            stream.close();
        } catch (IOException exc) {
            log.info("Exception thrown when trying to close the process output stream", exc);
        }

        Thread.sleep(1);
        return element;
    }

    /**
     * Get the folder used in this command.
     * @return Folder used in this command.
     */
    public OBEXElement getFolder() {
        return folder;
    }
}
