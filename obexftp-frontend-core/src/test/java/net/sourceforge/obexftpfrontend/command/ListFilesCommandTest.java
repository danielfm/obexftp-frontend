package net.sourceforge.obexftpfrontend.command;

import java.io.BufferedReader;
import java.util.LinkedList;
import java.util.List;
import net.sourceforge.obexftpfrontend.model.Configuration;
import net.sourceforge.obexftpfrontend.model.ConfigurationHolder;
import net.sourceforge.obexftpfrontend.model.OBEXElement;
import net.sourceforge.obexftpfrontend.model.TransportType;
import net.sourceforge.obexftpfrontend.obexftp.JDOMOBEXResponseParser;
import net.sourceforge.obexftpfrontend.obexftp.OBEXResponseParser;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * ListFilesCommand test cases.
 * @author Daniel F. Martins
 */
public class ListFilesCommandTest {

    private Mockery context = new Mockery() {

        {
            setImposteriser(ClassImposteriser.INSTANCE);
        }
    };

    @Test(expected = IllegalArgumentException.class)
    public void testCreateWithNullFolder() {
        final ConfigurationHolder configHolder = context.mock(ConfigurationHolder.class);

        context.checking(new Expectations() {

            {
                one(configHolder).getConfiguration();
            }
        });

        new ListFilesCommand(new JDOMOBEXResponseParser(), configHolder, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateWithNullParser() {
        final ConfigurationHolder configHolder = context.mock(ConfigurationHolder.class);

        context.checking(new Expectations() {

            {
                one(configHolder).getConfiguration();
            }
        });

        new ListFilesCommand(null, configHolder, new OBEXElement("something"));
    }

    @Test
    public void testGetFileList() throws Exception {
        final OBEXResponseParser parser = context.mock(OBEXResponseParser.class);
        final OBEXFTPCommandLineRunner runner = context.mock(OBEXFTPCommandLineRunner.class);
        final ConfigurationHolder configHolder = context.mock(ConfigurationHolder.class);
        final Process process = context.mock(Process.class);

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
                will(returnValue(new Configuration("obexftp", TransportType.USB, "dev", 0, true, true)));

                one(runner).run(null, expectedParams, 0L);
                will(returnValue(process));

                one(process).getInputStream();

                one(parser).parseResponse(with(aNonNull(BufferedReader.class)), with(equal(new OBEXElement("/"))));
            }
        });

        ListFilesCommand command = new ListFilesCommand(parser, configHolder, new OBEXElement("/"));
        command.setCommandLineRunner(runner);

        command.execute();
        context.assertIsSatisfied();
    }

    @Test
    public void testGetFolder() {
        final ConfigurationHolder configHolder = context.mock(ConfigurationHolder.class);

        context.checking(new Expectations() {

            {
                one(configHolder).getConfiguration();
            }
        });

        OBEXElement element = new OBEXElement("/");
        ListFilesCommand command = new ListFilesCommand(new JDOMOBEXResponseParser(), configHolder, element);

        assertSame(element, command.getFolder());
    }
}