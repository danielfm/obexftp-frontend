package net.sourceforge.obexftpfrontend.command;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import net.sourceforge.obexftpfrontend.model.Configuration;
import net.sourceforge.obexftpfrontend.model.ConfigurationEvent;
import net.sourceforge.obexftpfrontend.model.OBEXElement;
import net.sourceforge.obexftpfrontend.model.TransportType;
import net.sourceforge.obexftpfrontend.obexftp.JDOMOBEXResponseParser;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * AbstractOBEXFTPCommand test cases.
 * @author Daniel F. Martins
 */
public class AbstractOBEXFTPCommandTest {

    private Mockery context = new Mockery() {

        {
            setImposteriser(ClassImposteriser.INSTANCE);
        }
    };
    private ListFilesCommand command;

    @Before
    public void setUp() {
        command = new ListFilesCommand(new JDOMOBEXResponseParser(), null, new OBEXElement("/"));
        command.setConfiguration(new Configuration("obexftp", TransportType.USB, "dev", 0, true, true));
    }

    @Test
    public void testConvertNullPath() {
        assertNotNull(command.convertPath(null));
    }

    @Test
    public void testConvertValidPath() {
        OBEXElement root = new OBEXElement("/");
        OBEXElement child = new OBEXElement("dir");
        OBEXElement childchild = new OBEXElement("ano ther");

        root.add(child);
        child.add(childchild);

        List<String> expected = new LinkedList<String>();

        expected.add("--chdir");
        expected.add("/");

        assertEquals(expected, command.convertPath(root));

        expected.clear();
        expected.add("--chdir");
        expected.add("/dir");

        assertEquals(expected, command.convertPath(child));

        expected.clear();
        expected.add("--chdir");
        expected.add("/dir/ano ther");

        assertEquals(expected, command.convertPath(childchild));
    }

    @Test
    public void testGetDeviceConnectionInfoUsingNullConfig() {
        command.setConfiguration(null);

        List<String> emptyList = Collections.emptyList();
        assertEquals(command.getDeviceConnectionInfo(), emptyList);
    }

    @Test
    public void testGetDeviceUSBConnectionInfo() {
        List<String> result = command.getDeviceConnectionInfo();

        assertEquals(2, result.size());
        assertEquals("--usb", result.get(0));
        assertEquals("dev", result.get(1));

        command.setConfiguration(new Configuration("obexftp", TransportType.TTY, "tty", 0, true, true));
        result = command.getDeviceConnectionInfo();

        assertEquals(2, result.size());
        assertEquals("--tty", result.get(0));
        assertEquals("tty", result.get(1));
    }

    @Test
    public void testGetDeviceNetworkConnectionInfo() {
        command.setConfiguration(new Configuration("obexftp", TransportType.NETWORK_HOST, "127.0.0.1", 0, true, true));
        List<String> result = command.getDeviceConnectionInfo();

        assertEquals(2, result.size());
        assertEquals("--network", result.get(0));
        assertEquals("127.0.0.1", result.get(1));
    }

    @Test
    public void testGetDeviceIrDAConnectionInfo() {
        command.setConfiguration(new Configuration("obexftp", TransportType.IRDA, "127.0.0.1", 0, true, true));
        List<String> result = command.getDeviceConnectionInfo();

        assertEquals(1, result.size());
        assertEquals("--irda", result.get(0));
    }

    @Test
    public void testGetDeviceBluetoothConnectionInfo() {
        command.setConfiguration(new Configuration("obexftp", TransportType.BLUETOOTH, null, 0, true, true));
        List<String> result = command.getDeviceConnectionInfo();

        assertEquals(1, result.size());
        assertEquals("--bluetooth", result.get(0));

        command.setConfiguration(new Configuration("obexftp", TransportType.BLUETOOTH, "00:11:22:33:44:55", 0, true, true));
        result = command.getDeviceConnectionInfo();

        assertEquals(2, result.size());
        assertEquals("--bluetooth", result.get(0));
        assertEquals("00:11:22:33:44:55", result.get(1));

        command.setConfiguration(new Configuration("obexftp", TransportType.BLUETOOTH, "00:11:22:33:44:55,10", 0, true, true));
        result = command.getDeviceConnectionInfo();

        assertEquals(4, result.size());
        assertEquals("--bluetooth", result.get(0));
        assertEquals("00:11:22:33:44:55", result.get(1));
        assertEquals("--channel", result.get(2));
        assertEquals("10", result.get(3));

        command.setConfiguration(new Configuration("obexftp", TransportType.BLUETOOTH, "00:11:22:33:44:55 , 10", 0, true, true));
        result = command.getDeviceConnectionInfo();

        assertEquals(4, result.size());
        assertEquals("--bluetooth", result.get(0));
        assertEquals("00:11:22:33:44:55", result.get(1));
        assertEquals("--channel", result.get(2));
        assertEquals("10", result.get(3));
    }

    @Test
    public void testGetDescription() {
        assertNotNull(command.getDescription());
    }

    @Test
    public void testConfigurationChanged() {

        final ConfigurationEvent evt = context.mock(ConfigurationEvent.class);

        context.checking(new Expectations() {

            {
                one(evt).getConfiguration();
            }
        });

        command.configurationChanged(evt);
        context.assertIsSatisfied();
    }
}