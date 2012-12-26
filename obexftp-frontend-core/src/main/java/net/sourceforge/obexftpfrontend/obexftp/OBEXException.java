package net.sourceforge.obexftpfrontend.obexftp;

import java.io.IOException;

/**
 * Exception that represent a general OBEX exception.
 * @author Daniel F. Martins
 */
public class OBEXException extends IOException {

    /**
     * Create a new instance of OBEXException.
     * @param message Exception message.
     * @param throwable Exception cause.
     */
    public OBEXException(String message, Throwable throwable) {
        super(message, throwable);
    }
}