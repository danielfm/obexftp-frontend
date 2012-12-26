package net.sourceforge.obexftpfrontend.obexftp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import org.apache.log4j.Logger;

/**
 * This processor removes invalid trailing characters that comes with the
 * OBEXFTP response document. This problem happened with a Nokia 6881 cell
 * phone.
 * @author Daniel F. Martins
 */
public class RemoveInvalidCharsProcessor extends AbstractResponseProcessorChainMember {

    /** Logger. */
    private static final Logger log = Logger.getLogger(RemoveInvalidCharsProcessor.class);

    /**
     * Create a new instance of XMLTrailingCharacterProcessor.
     */
    public RemoveInvalidCharsProcessor() {
        super();
    }

    @Override
    public BufferedReader doProcess(BufferedReader stream) throws IOException {
        String line = null;
        log.debug("Starting the RemoveInvalidCharsProcessor");

        StringWriter stringWriter = new StringWriter();
        PrintWriter output = new PrintWriter(stringWriter);

        while ((line = stream.readLine()) != null) {
            output.println(line.replaceAll(Character.toString('\u0000'), ""));
        }

        log.debug("RemoveInvalidCharsProcessor executed");
        return new BufferedReader(new StringReader(stringWriter.getBuffer().toString()));
    }
}