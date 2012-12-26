package net.sourceforge.obexftpfrontend.obexftp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * XMLVersionProcessor test cases.
 * @author Daniel F. Martins
 */
public class XMLVersionProcessorTest {

    private XMLVersionProcessor processor;

    @Before
    public void setUp() {
        processor = new XMLVersionProcessor();
    }

    @Test
    public void testDoProcess() throws Exception {
        int lineCount = 1;

        Reader invalidDocument = new InputStreamReader(getClass().getClassLoader().getResourceAsStream("invalid-document-2.xml"));
        BufferedReader result = processor.doProcess(new BufferedReader(invalidDocument));

        assertNotNull(result);
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", result.readLine());

        while (result.readLine() != null) {
            lineCount++;
        }

        assertEquals(8, lineCount);
    }
}
