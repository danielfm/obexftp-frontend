package net.sourceforge.obexftpfrontend.command;

import net.sourceforge.obexftpfrontend.model.Configuration;
import net.sourceforge.obexftpfrontend.obexftp.OBEXException;
import net.sourceforge.obexftpfrontend.obexftp.OBEXFTPException;

/**
 * User requests to 'obexftp' are represented by command objects. Using objects
 * as requests gives us more flexibility when dealing with problems, making
 * possible to retry failed commands and keep track of existing commands.
 * @see net.sourceforge.obexftpfrontend.command.OBEXFTPCommandConsumer
 * @see net.sourceforge.obexftpfrontend.command.OBEXFTPCommandQueue
 * @author Daniel F. Martins
 */
public interface OBEXFTPCommand<T> {

    /**
     * This method is responsible to execute whatever the user intended to.
     * @return A command can return an object an arbitrary object.
     * @throws OBEXFTPException if errors during the execution of 'obexftp'
     * occurs.
     * @throws OBEXException if errors during the execution of command occurs.
     * @throws InterruptedException if the command was interrupted.
     */
    T execute() throws OBEXFTPException, OBEXException, InterruptedException;

    /**
     * Set the configuration object which is available to this command.
     * @param config Configuration object.
     */
    void setConfiguration(Configuration config);

    /**
     * Get a friendly description of this command.
     * @return The command description.
     */
    String getDescription();
}