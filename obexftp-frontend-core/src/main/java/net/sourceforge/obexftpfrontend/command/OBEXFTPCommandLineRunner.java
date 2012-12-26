package net.sourceforge.obexftpfrontend.command;

import java.io.File;
import java.util.List;
import net.sourceforge.obexftpfrontend.obexftp.OBEXFTPException;

/**
 * This interface is used to define how this application should deal with
 * command-line stuff, specially with 'obexftp'.
 * @see net.sourceforge.obexftpfrontend.command.OBEXFTPCommand
 * @author Daniel F. Martins
 */
public interface OBEXFTPCommandLineRunner {

    /**
     * Executes a given command-line.
     * @param workingFolder Folder from which the command must run.
     * @param params String values that represent the command line to execute
     * and its arguments.
     * @param timeout The timeout value. Use 0 if you want to wait the process
     * to finish or a higher value (in seconds) if you want to wait for that
     * many time.
     * @return The process used to monitor the execution of the given
     * command-line.
     * @throws OBEXFTPException if some error raise during the execution of
     * 'obexftp'.
     * @throws InterruptedException if an interruption occurs.
     * @throws IllegalArgumentException if some argument is invalid.
     */
    Process run(File workingFolder, List<String> params, long timeout) throws OBEXFTPException, InterruptedException;
}