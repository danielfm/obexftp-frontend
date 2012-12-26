package net.sourceforge.obexftpfrontend.command;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import net.sourceforge.obexftpfrontend.model.Configuration;
import net.sourceforge.obexftpfrontend.model.ConfigurationHolder;
import net.sourceforge.obexftpfrontend.model.OBEXElement;
import net.sourceforge.obexftpfrontend.model.TransportType;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * SendFilesCommand test cases.
 * @author Daniel F. Martins
 */
public class SendFilesCommandTest {

    private Mockery context = new Mockery();

    @Test
    public void testCreate() {
        final ConfigurationHolder configHolder = context.mock(ConfigurationHolder.class);

        List<File> elements = new LinkedList<File>();
        elements.add(File.listRoots()[0]);

        context.checking(new Expectations() {

            {
                one(configHolder).getConfiguration();
            }
        });

        new SendFilesCommand(configHolder, new OBEXElement("/"), elements);
        context.assertIsSatisfied();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateWithNullFiles() {
        final ConfigurationHolder configHolder = context.mock(ConfigurationHolder.class);

        context.checking(new Expectations() {

            {
                one(configHolder).getConfiguration();
            }
        });

        new SendFilesCommand(configHolder, new OBEXElement("/"), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateWithEmptyFileList() {
        final ConfigurationHolder configHolder = context.mock(ConfigurationHolder.class);

        List<File> elements = new LinkedList<File>();

        context.checking(new Expectations() {

            {
                one(configHolder).getConfiguration();
            }
        });

        new SendFilesCommand(configHolder, new OBEXElement("/"), elements);
    }

    @Test
    public void testSendFiles() throws Exception {
        final OBEXFTPCommandLineRunner runner = context.mock(OBEXFTPCommandLineRunner.class);
        final ConfigurationHolder configHolder = context.mock(ConfigurationHolder.class);

        final List<String> expectedParams = new LinkedList<String>();
        expectedParams.add("obexftp");
        expectedParams.add("--usb");
        expectedParams.add("dev");
        expectedParams.add("--chdir");
        expectedParams.add("/");
        expectedParams.add("--put");

        List<OBEXElement> elements = new LinkedList<OBEXElement>();
        elements.add(new OBEXElement("somefile"));

        context.checking(new Expectations() {

            {
                one(configHolder).getConfiguration();
                will(returnValue(new Configuration("obexftp", TransportType.USB, "dev", 0, true, true)));
                one(runner).run(null, expectedParams, 0L);
            }
        });

        List<File> files = new LinkedList<File>();
        files.add(File.createTempFile("_temp_file", null));

        expectedParams.add(files.get(0).getAbsolutePath());

        SendFilesCommand command = new SendFilesCommand(configHolder, new OBEXElement("/"), files);
        command.setCommandLineRunner(runner);

        Object result = command.execute();
        assertNull(result);
        context.assertIsSatisfied();
    }

    @Test
    public void testSendInexistentFiles() throws Exception {
        final OBEXFTPCommandLineRunner runner = context.mock(OBEXFTPCommandLineRunner.class);
        final ConfigurationHolder configHolder = context.mock(ConfigurationHolder.class);

        final List<String> expectedParams = new LinkedList<String>();
        expectedParams.add("obexftp");
        expectedParams.add("--usb");
        expectedParams.add("dev");
        expectedParams.add("--chdir");
        expectedParams.add("/");
        expectedParams.add("--put");

        List<OBEXElement> elements = new LinkedList<OBEXElement>();
        elements.add(new OBEXElement("somefile"));

        context.checking(new Expectations() {

            {
                one(configHolder).getConfiguration();
                will(returnValue(new Configuration("obexftp", TransportType.USB, "dev", 0, true, true)));
            }
        });

        List<File> files = new LinkedList<File>();
        files.add(new File(".some.nonsense.file"));

        expectedParams.add(files.get(0).getAbsolutePath());

        SendFilesCommand command = new SendFilesCommand(configHolder, new OBEXElement("/"), files);
        command.setCommandLineRunner(runner);

        Object result = command.execute();
        assertNull(result);
        context.assertIsSatisfied();
    }
}