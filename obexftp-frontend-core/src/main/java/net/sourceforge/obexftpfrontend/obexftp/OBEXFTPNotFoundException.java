package net.sourceforge.obexftpfrontend.obexftp;

/**
 * This exception is thrown when the 'obexftp' program cannot be found by the
 * application.
 * @author Daniel F. Martins
 */
public class OBEXFTPNotFoundException extends OBEXFTPException {

    /**
     * Create a new instance of OBEXFTPNotFoundException.
     * @param message Exception message.
     * @param throwable Exception cause.
     */
    public OBEXFTPNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }
}