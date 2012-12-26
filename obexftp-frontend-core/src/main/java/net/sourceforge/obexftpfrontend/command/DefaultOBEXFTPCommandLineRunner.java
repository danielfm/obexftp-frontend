package net.sourceforge.obexftpfrontend.command;

import java.io.File;
import java.io.IOException;
import java.util.List;
import net.sourceforge.obexftpfrontend.obexftp.OBEXFTPException;
import net.sourceforge.obexftpfrontend.obexftp.OBEXFTPNotFoundException;
import org.apache.log4j.Logger;

/**
 * Default implementation of the OBEXFTPCommandLineRunner inteface.
 * @author Daniel F. Martins
 */
public class DefaultOBEXFTPCommandLineRunner implements OBEXFTPCommandLineRunner {

    /** Logger. */
    private static final Logger log = Logger.getLogger(DefaultOBEXFTPCommandLineRunner.class);

    /**
     * Create a new instance of DefaultOBEXFTPCommandLineRunner.
     */
    public DefaultOBEXFTPCommandLineRunner() {
        super();
    }

    @Override
    public Process run(File workingFolder, List<String> params, long timeout) throws OBEXFTPException, InterruptedException {
        if (params == null || params.size() == 0) {
            log.debug("Cannot run a empty/null command line");
            throw new IllegalArgumentException("Cannot run a empty/null command line");
        }

        if (workingFolder != null && (!workingFolder.exists() || !workingFolder.isDirectory())) {
            log.debug("Invalid working folder");
            throw new IllegalArgumentException("Invalid working folder");
        }

        ProcessBuilder processBuilder = new ProcessBuilder(params);
        processBuilder = ((workingFolder != null) ? processBuilder.directory(workingFolder) : processBuilder);

        Process process = null;
        int result = Integer.MIN_VALUE;

        log.debug("Starting the process");
        log.debug("Command line: " + params + ", working folder: " + workingFolder);

        try {
            process = processBuilder.start();
        } catch (IOException exc) {
            throw new OBEXFTPNotFoundException("Cannot start an obexftp process", exc);
        }

        if (timeout <= 0) {
            log.debug("Waiting the the process ending");
            result = process.waitFor();
        } else {
            log.debug("Waiting for the process timeout");

            timeout *= 10;
            for (int i = 0; i < timeout; i++) {
                try {
                    result = process.exitValue();
                    break;
                } catch (IllegalThreadStateException exc) {
                    log.debug("Process has not yet terminated");
                }
                Thread.sleep(100);
            }

            if (result == Integer.MIN_VALUE) {
                log.info("Process timeout");
                destroyProcess(process);

                throw new OBEXFTPException("Process aborted");
            }
        }

        if (result != 0) {
            throw new OBEXFTPException("The process' return value is != 0");
        }

        return process;
    }

    /**
     * Destroy the given process.
     * @param process Process instance to destroy.
     */
    protected void destroyProcess(Process process) {
        log.debug("Destroying the process");
        process.destroy();
    }
}