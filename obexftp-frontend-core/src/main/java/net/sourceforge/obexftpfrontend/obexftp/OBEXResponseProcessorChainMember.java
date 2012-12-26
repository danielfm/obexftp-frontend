package net.sourceforge.obexftpfrontend.obexftp;

import java.io.BufferedReader;

/**
 * This interfaces defines a chain of processor objects which can be used to
 * process an OBEX response document. Usually, these processors are used to
 * correct some issues in the returned document or add extra-information to its.
 * @author Daniel F. Martins
 */
public interface OBEXResponseProcessorChainMember {

    /**
     * Process the OBEX response document.
     * @param parser OBEX response parser.
     * @param stream Stream that contains the OBEX response document.
     * @return The modified stream.
     * @throws OBEXException if some error occurs.
     */
    BufferedReader processResponse(OBEXResponseParser parser, BufferedReader stream) throws OBEXException;

    /**
     * This method returns the next processor of the chain. It returns null if
     * this is the last processor of the chain.
     * @return The next processor of the chain or null if this is the last
     * processor of the chain.
     */
    OBEXResponseProcessorChainMember getNextProcessor();

    /**
     * Set the next processor of the chain.
     * @param processor Next processor of the chain.
     * @return this.
     */
    OBEXResponseProcessorChainMember setNextProcessor(OBEXResponseProcessorChainMember processor);
}