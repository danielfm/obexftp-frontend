package net.sourceforge.obexftpfrontend.command;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import net.sourceforge.obexftpfrontend.model.Configuration;
import net.sourceforge.obexftpfrontend.model.ConfigurationHolder;
import net.sourceforge.obexftpfrontend.model.DevInfo;
import net.sourceforge.obexftpfrontend.model.TransportType;
import net.sourceforge.obexftpfrontend.obexftp.DefaultDevInfoFileParser;
import net.sourceforge.obexftpfrontend.obexftp.DevInfoFileParser;
import net.sourceforge.obexftpfrontend.persistence.DefaultMetaConfigurationReader;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * TestConnectionCommand test cases.
 * @author Daniel F. Martins
 */
public class TestConnectionCommandTest {

    private Mockery context = new Mockery();

    @Test(expected = IllegalArgumentException.class)
    public void testCreateWithInvalidDevInfoParser() {
        new TestConnectionCommand(null, null);
    }

    @Test
    public void testSimpleConnection() throws Exception {
        final OBEXFTPCommandLineRunner runner = context.mock(OBEXFTPCommandLineRunner.class);
        final ConfigurationHolder configHolder = context.mock(ConfigurationHolder.class);

        final List<String> expectedParams = new LinkedList<String>();
        expectedParams.add("obexftp");
        expectedParams.add("--usb");
        expectedParams.add("dev");
        expectedParams.add("--chdir");
        expectedParams.add("/");
        expectedParams.add("--list");

        context.checking(new Expectations() {

            {
                one(configHolder).getConfiguration();
                will(returnValue(new Configuration("obexftp", TransportType.USB, "dev", 0, false, false)));
                one(runner).run(null, expectedParams, 0L);
            }
        });

        DevInfoFileParser parser = new DefaultDevInfoFileParser();
        TestConnectionCommand command = new TestConnectionCommand(configHolder, parser);
        command.setCommandLineRunner(runner);

        DevInfo result = command.execute();

        assertNull(result);
        context.assertIsSatisfied();
    }

    @Test
    public void testConnectionWithDevInfo() throws Exception {
        final OBEXFTPCommandLineRunner runner = context.mock(OBEXFTPCommandLineRunner.class);
        final ConfigurationHolder configHolder = context.mock(ConfigurationHolder.class);

        final List<String> expectedParams = new LinkedList<String>();
        expectedParams.add("obexftp");
        expectedParams.add("--usb");
        expectedParams.add("dev");
        expectedParams.add("--get");
        expectedParams.add("telecom/devinfo.txt");

        File devInfo = new File(getClass().getClassLoader().getResource("devinfo.txt").toURI());
        final File file = DefaultMetaConfigurationReader.getInstance().getDevInfoFile();

        copyFile(devInfo, file);

        context.checking(new Expectations() {

            {
                one(configHolder).getConfiguration();
                will(returnValue(new Configuration("obexftp", TransportType.USB, "dev", 0, true, true)));
                one(runner).run(file.getParentFile(), expectedParams, 0L);
            }
        });

        DevInfoFileParser parser = new DefaultDevInfoFileParser();
        TestConnectionCommand command = new TestConnectionCommand(configHolder, parser);
        command.setCommandLineRunner(runner);

        DevInfo result = command.execute();

        assertNotNull(result);
        assertFalse("".equals(result.getSerialNumber()));
        assertFalse("".equals(result.getFirmwareVersion()));
        assertFalse("".equals(result.getHardwareVersion()));
        assertFalse("".equals(result.getManufacturer()));
        assertFalse("".equals(result.getModel()));

        context.assertIsSatisfied();
    }

    /**
     * Copy a file.
     * @param from File to copy.
     * @param to New file.
     */
    private void copyFile(File from, File to) throws IOException {
        FileInputStream input = new FileInputStream(from);
        FileOutputStream output = new FileOutputStream(to);

        byte[] buffer = new byte[1024];
        int count = 0;

        while ((count = input.read(buffer)) > 0) {
            output.write(buffer, 0, count);
        }

        output.close();
        input.close();
    }
}