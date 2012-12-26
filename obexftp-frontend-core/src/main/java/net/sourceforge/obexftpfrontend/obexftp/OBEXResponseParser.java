package net.sourceforge.obexftpfrontend.obexftp;

import java.io.BufferedReader;
import net.sourceforge.obexftpfrontend.model.ConfigurationListener;
import net.sourceforge.obexftpfrontend.model.OBEXElement;

/**
 * This interface defines all operations that a 'obexftp' response parser must
 * be able to perform. Most OBEX requests made by 'obexftp' returns a XML file
 * that must be parsed by any class that implements this interface.
 * @author Daniel F. Martins
 */
public interface OBEXResponseParser extends ConfigurationListener {

    /**
     * Parse the OBEX response XML document.
     * @param stream Stream that must be used to read the response.
     * @param parentFolder Folder from which the OBEX request was sent.
     * @return Element that represents the parsed XML document.
     * @throws OBEXException if some communication error occurs.
     */
    OBEXElement parseResponse(BufferedReader stream, OBEXElement parentFolder) throws OBEXException;
}