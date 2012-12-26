package net.sourceforge.obexftpfrontend.obexftp;

import java.io.File;
import java.io.IOException;
import net.sourceforge.obexftpfrontend.model.DevInfo;
import net.sourceforge.obexftpfrontend.persistence.MetaConfigurationReader;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * DefaultDevInfoFileParser test cases.
 * @author Daniel F. Martins
 */
public class DefaultDevInfoFileParserTest {

    private DefaultDevInfoFileParser parser;
    private MetaConfigurationReader propertiesReader;
    private Mockery context = new Mockery();

    @Before
    public void setUp() {
        propertiesReader = context.mock(MetaConfigurationReader.class);
        parser = new DefaultDevInfoFileParser(propertiesReader);
    }

    @Test
    public void testParseInexistentFile() {
        try {
            context.checking(new Expectations() {

                {
                    one(propertiesReader).getDevInfoFile();
                    will(returnValue(new File("something.here")));
                }
            });

            parser.parseFile();
            fail();
        } catch (OBEXFTPException exc) {
        }
    }

    @Test
    public void testParseInvalidFile() throws IOException {
        final File tempFile = File.createTempFile("tmpfile", "tmp");

        context.checking(new Expectations() {

            {
                one(propertiesReader).getDevInfoFile();
                will(returnValue(tempFile));
            }
        });

        DevInfo info = parser.parseFile();
        assertEquals("", info.getFirmwareVersion());
        assertEquals("", info.getFirmwareDate());
        assertEquals("", info.getHardwareVersion());
        assertEquals("", info.getManufacturer());
        assertEquals("", info.getModel());
        assertEquals("", info.getSerialNumber());
    }

    @Test
    public void testParseFile() throws Exception {
        final File embeddedDevInfoFile = new File(getClass().getClassLoader().getResource("devinfo.txt").toURI());
        context.checking(new Expectations() {

            {
                one(propertiesReader).getDevInfoFile();
                will(returnValue(embeddedDevInfoFile));
            }
        });

        DevInfo info = parser.parseFile();
        assertEquals("prgCXC125978_EU_1_KR", info.getFirmwareVersion());
        assertEquals("20070803T120000", info.getFirmwareDate());
        assertEquals("proto", info.getHardwareVersion());
        assertEquals("Sony Ericsson", info.getManufacturer());
        assertEquals("K300i", info.getModel());
        assertEquals("355796555090717", info.getSerialNumber());
    }
}
