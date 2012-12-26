package net.sourceforge.obexftpfrontend.obexftp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import net.sourceforge.obexftpfrontend.model.Configuration;
import net.sourceforge.obexftpfrontend.model.ConfigurationEvent;
import net.sourceforge.obexftpfrontend.model.OBEXElement;
import net.sourceforge.obexftpfrontend.model.TransportType;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.EntityResolver;
import static org.junit.Assert.*;

/**
 * JDOMOBEXResponseParser test cases.
 * @author Daniel F. Martins
 */
public class JDOMOBEXResponseParserTest {

    private JDOMOBEXResponseParser parser;

    @Before
    public void setUp() {
        parser = new JDOMOBEXResponseParser();
    }

    @Test
    public void testParseInvalidOBEXFileListingResponse() {
        Reader invalidDocument = new InputStreamReader(getClass().getResourceAsStream("/invalid-document.xml"));
        OBEXElement parent = new OBEXElement("/");
        try {
            parser.parseResponse(null, null);
            fail();
        } catch (Exception exc) {
        }

        try {
            parser.parseResponse(null, parent);
            fail();
        } catch (Exception exc) {
        }

        try {
            parser.parseResponse(new BufferedReader(invalidDocument), parent);
            fail();
        } catch (Exception exc) {
        }
    }

    @Test
    public void testParseOBEXFileListingResponse() throws Exception {
        Reader validDocument = new InputStreamReader(getClass().getResourceAsStream("/valid-document.xml"));
        OBEXElement element = parser.parseResponse(new BufferedReader(validDocument), new OBEXElement("/"));

        assertEquals(5, element.getChildCount());

        OBEXElement child = (OBEXElement) element.getChildAt(4);
        assertEquals("Other", child.getName());
        assertEquals(200L, child.getSize());
        assertEquals("someguy", child.getOwner());
        assertEquals("somegroup", child.getGroup());
        assertEquals("RWD", child.getOwnerPerm());
        assertEquals("RWD", child.getGroupPerm());
        assertEquals("RW", child.getOtherPerm());
        assertNotNull(child.getCreated());
        assertNotNull(child.getAccessed());
        assertNotNull(child.getModified());
    }

    @Test
    public void testConfigurationChanged() {
        Configuration config = new Configuration("obexftp", TransportType.BLUETOOTH, "123456", 0, true, true);
        EntityResolver resolver = null;

        parser.configurationChanged(new ConfigurationEvent(config));
        resolver = parser.getBuilder().getEntityResolver();

        assertTrue(parser.getBuilder().getValidation());

        config.setValidateListing(false);
        parser.configurationChanged(new ConfigurationEvent(config));

        assertNotSame(parser.getBuilder().getEntityResolver(), resolver);
        assertFalse(parser.getBuilder().getValidation());
    }
}
