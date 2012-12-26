package net.sourceforge.obexftpfrontend.obexftp;

import java.io.IOException;

/**
 * Exception that represent a failure during the execution of 'obexftp'.
 * @author Daniel F. Martins
 */
public class OBEXFTPException extends IOException {

    /**
     * Create a new instance of OBEXFTPException.
     * @param message Exception message.
     */
    public OBEXFTPException(String message) {
        super(message);
    }

    /**
     * Create a new instance of OBEXFTPException.
     * @param message Exception message.
     * @param cause Exception cause.
     */
    public OBEXFTPException(String message, Throwable cause) {
        super(message, cause);
    }
}