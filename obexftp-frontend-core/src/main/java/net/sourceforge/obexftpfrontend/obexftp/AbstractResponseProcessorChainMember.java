package net.sourceforge.obexftpfrontend.obexftp;

import java.io.BufferedReader;
import java.io.IOException;
import org.apache.log4j.Logger;

/**
 * Abstract implementation of the ResponseProcessorChain interface.
 * @author Daniel F. Martins
 */
public abstract class AbstractResponseProcessorChainMember implements OBEXResponseProcessorChainMember {

    private static final Logger log = Logger.getLogger(AbstractResponseProcessorChainMember.class);
    private OBEXResponseProcessorChainMember next;

    /**
     * Create a new instance of AbstractResponseProcessorChainMember.
     */
    public AbstractResponseProcessorChainMember() {
        super();
    }

    @Override
    public BufferedReader processResponse(OBEXResponseParser parser, BufferedReader stream) throws OBEXException {
        BufferedReader result = stream;
        AbstractResponseProcessorChainMember member = this;

        try {
            log.debug("Activating the processor chain");
            while (member != null) {
                result = member.doProcess(result);

                log.debug("Retrieving the next processor in the chain");
                member = (AbstractResponseProcessorChainMember) member.getNextProcessor();
            }
        } catch (IOException exc) {
            throw new OBEXException("Error while pre-processing the file listing XML", exc);
        }

        log.debug("Processor chain terminated sucessfully");
        return result;
    }

    @Override
    public OBEXResponseProcessorChainMember getNextProcessor() {
        return next;
    }

    @Override
    public OBEXResponseProcessorChainMember setNextProcessor(OBEXResponseProcessorChainMember next) {
        this.next = next;
        return this;
    }

    /**
     * Process the obexftp response XML document.
     * @param reader Input stream that represents the obexftp response.
     * @return The processed stream.
     * @throws net.sourceforge.obexftpfrontend.obexftp.OBEXException
     */
    protected abstract BufferedReader doProcess(BufferedReader reader) throws IOException;
}
