package net.sourceforge.obexftpfrontend.command;

import java.io.File;
import java.io.IOException;
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
 * GetFilesCommand test cases.
 * @author Daniel F. Martins
 */
public class GetFilesCommandTest {

    private Mockery context = new Mockery();

    @Test(expected = IllegalArgumentException.class)
    public void testCreateWithNullLocalFolder() {
        final ConfigurationHolder configHolder = context.mock(ConfigurationHolder.class);

        context.checking(new Expectations() {

            {
                one(configHolder).getConfiguration();
            }
        });

        List<OBEXElement> elements = new LinkedList<OBEXElement>();
        elements.add(new OBEXElement("somefile"));

        new GetFilesCommand(configHolder, null, new OBEXElement("/"), elements, false);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateWithInvalidLocalFolder() throws IOException {
        final ConfigurationHolder configHolder = context.mock(ConfigurationHolder.class);

        context.checking(new Expectations() {

            {
                one(configHolder).getConfiguration();
            }
        });

        List<OBEXElement> elements = new LinkedList<OBEXElement>();
        elements.add(new OBEXElement("somefile"));

        new GetFilesCommand(configHolder, File.createTempFile("_something_", "tmp"), new OBEXElement("/"), elements, false);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateWithEmptyFileList() {
        final ConfigurationHolder configHolder = context.mock(ConfigurationHolder.class);

        context.checking(new Expectations() {

            {
                one(configHolder).getConfiguration();
            }
        });

        List<OBEXElement> elements = new LinkedList<OBEXElement>();
        new GetFilesCommand(configHolder, new File(System.getProperty("user.home")), new OBEXElement("/"), elements, false);
    }

    @Test
    public void testCreateWithNullFileList() {
        final ConfigurationHolder configHolder = context.mock(ConfigurationHolder.class);

        context.checking(new Expectations() {

            {
                one(configHolder).getConfiguration();
            }
        });

        try {
            new GetFilesCommand(configHolder, new File(System.getProperty("user.home")), new OBEXElement("/"), null, false);
            fail();
        } catch (IllegalArgumentException exc) {
        }
    }

    @Test
    public void testCreate() {
        final ConfigurationHolder configHolder = context.mock(ConfigurationHolder.class);

        context.checking(new Expectations() {

            {
                one(configHolder).getConfiguration();
            }
        });


        List<OBEXElement> elements = new LinkedList<OBEXElement>();
        elements.add(new OBEXElement("something"));

        new GetFilesCommand(configHolder, new File(System.getProperty("user.home")), new OBEXElement("/"), elements, false);
        context.assertIsSatisfied();
    }

    @Test
    public void testGet() throws Exception {
        final OBEXFTPCommandLineRunner runner = context.mock(OBEXFTPCommandLineRunner.class);
        final ConfigurationHolder configHolder = context.mock(ConfigurationHolder.class);
        final File workingFolder = new File(".");

        final List<String> expectedParams = new LinkedList<String>();
        expectedParams.add("obexftp");
        expectedParams.add("--usb");
        expectedParams.add("dev");
        expectedParams.add("--chdir");
        expectedParams.add("/");
        expectedParams.add("--get");
        expectedParams.add("somefile");

        List<OBEXElement> elements = new LinkedList<OBEXElement>();
        elements.add(new OBEXElement("somefile"));

        context.checking(new Expectations() {

            {
                one(configHolder).getConfiguration();
                will(returnValue(new Configuration("obexftp", TransportType.USB, "dev", 0, true, true)));
                one(runner).run(workingFolder, expectedParams, 0L);
            }
        });

        GetFilesCommand command = new GetFilesCommand(configHolder, workingFolder, new OBEXElement("/"), elements, false);
        command.setCommandLineRunner(runner);

        Object result = command.execute();
        assertNull(result);
        context.assertIsSatisfied();
    }

    @Test
    public void testGetAndRemove() throws Exception {
        final OBEXFTPCommandLineRunner runner = context.mock(OBEXFTPCommandLineRunner.class);
        final ConfigurationHolder configHolder = context.mock(ConfigurationHolder.class);

        final File dir = new File(System.getProperty("user.dir"));

        final List<String> expectedParams = new LinkedList<String>();
        expectedParams.add("obexftp");
        expectedParams.add("--usb");
        expectedParams.add("dev");
        expectedParams.add("--chdir");
        expectedParams.add("/");
        expectedParams.add("--getdelete");
        expectedParams.add("somefile");

        List<OBEXElement> elements = new LinkedList<OBEXElement>();
        elements.add(new OBEXElement("somefile"));

        context.checking(new Expectations() {

            {
                one(configHolder).getConfiguration();
                will(returnValue(new Configuration("obexftp", TransportType.USB, "dev", 0, true, true)));
                one(runner).run(dir, expectedParams, 0L);
            }
        });

        GetFilesCommand command = new GetFilesCommand(configHolder, dir, new OBEXElement("/"), elements, true);
        command.setCommandLineRunner(runner);

        Object result = command.execute();
        assertNull(result);
        context.assertIsSatisfied();
    }
}