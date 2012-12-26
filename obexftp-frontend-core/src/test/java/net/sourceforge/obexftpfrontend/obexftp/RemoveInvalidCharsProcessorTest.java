package net.sourceforge.obexftpfrontend.obexftp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * RemoveInvalidCharsProcessor test cases.
 * @author Daniel F. Martins
 */
public class RemoveInvalidCharsProcessorTest {

    private RemoveInvalidCharsProcessor processor;

    @Before
    public void setUp() {
        processor = new RemoveInvalidCharsProcessor();
    }

    @Test
    public void testDoProcess() throws Exception {
        Reader invalidDocument = new InputStreamReader(getClass().getResourceAsStream("/invalid-document-3.xml"));
        BufferedReader result = processor.doProcess(new BufferedReader(invalidDocument));

        assertNotNull(result);

        String line;
        while ((line = result.readLine()) != null) {
            if (line.indexOf('\u0000') != -1) {
                fail("The document still contains invalid characters");
            }
        }
    }
}
