package net.sourceforge.obexftpfrontend.command;

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
 * CreateFolderCommand test cases.
 * @author Daniel F. Martins
 */
public class CreateFolderCommandTest {

    private Mockery context = new Mockery();

    @Test(expected = IllegalArgumentException.class)
    public void testCreateWithInvalidFolder() {
        new CreateFolderCommand(null, null, null);
    }

    @Test
    public void testCreateFolder() throws Exception {
        final OBEXFTPCommandLineRunner runner = context.mock(OBEXFTPCommandLineRunner.class);
        final ConfigurationHolder configHolder = context.mock(ConfigurationHolder.class);

        final List<String> expectedParams = new LinkedList<String>();
        expectedParams.add("obexftp");
        expectedParams.add("--usb");
        expectedParams.add("dev");
        expectedParams.add("--chdir");
        expectedParams.add("/");
        expectedParams.add("--mkdir");
        expectedParams.add("folder");

        context.checking(new Expectations() {

            {
                one(configHolder).getConfiguration();
                will(returnValue(new Configuration("obexftp", TransportType.USB, "dev", 0, true, true)));
                one(runner).run(null, expectedParams, 0L);
            }
        });

        CreateFolderCommand command = new CreateFolderCommand(configHolder, new OBEXElement("/"), new OBEXElement("folder"));
        command.setCommandLineRunner(runner);

        Object result = command.execute();
        assertNull(result);
        context.assertIsSatisfied();
    }
}