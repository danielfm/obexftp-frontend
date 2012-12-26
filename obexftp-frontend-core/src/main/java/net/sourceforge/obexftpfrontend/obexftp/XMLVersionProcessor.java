package net.sourceforge.obexftpfrontend.obexftp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import org.apache.log4j.Logger;

/**
 * This processor is used to correct the XML declaration line of the OBEX
 * response sent by some devices, which have a leading space in the
 * XML version attribute. This space confuses the XML parser that thinks the
 * whole response document is invalid.
 * @author Daniel F. Martins
 */
public class XMLVersionProcessor extends AbstractResponseProcessorChainMember {

    /** Logger. */
    private static final Logger log = Logger.getLogger(XMLVersionProcessor.class);

    /**
     * Create a new instance of XMLVersionProcessor.
     */
    public XMLVersionProcessor() {
        super();
    }

    @Override
    public BufferedReader doProcess(BufferedReader stream) throws IOException {
        String line = null;
        log.debug("Starting the XMLVersionProcessor");

        StringWriter stringWriter = new StringWriter();
        PrintWriter output = new PrintWriter(stringWriter);

        while ((line = stream.readLine()) != null) {
            if (line.trim().startsWith("<?xml")) {
                line = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
            }
            output.println(line);
        }

        log.debug("XMLVersionProcessor executed");
        return new BufferedReader(new StringReader(stringWriter.getBuffer().toString()));
    }
}