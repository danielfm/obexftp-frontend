package net.sourceforge.obexftpfrontend.command;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import net.sourceforge.obexftpfrontend.model.ConfigurationHolder;
import net.sourceforge.obexftpfrontend.model.DevInfo;
import net.sourceforge.obexftpfrontend.obexftp.DevInfoFileParser;
import net.sourceforge.obexftpfrontend.obexftp.OBEXException;
import net.sourceforge.obexftpfrontend.obexftp.OBEXFTPException;
import net.sourceforge.obexftpfrontend.persistence.MetaConfigurationReader;
import net.sourceforge.obexftpfrontend.persistence.DefaultMetaConfigurationReader;
import org.apache.log4j.Logger;

/**
 * The application creates this command and adds it to the command queue when
 * the user wants to test the connection with a device. This command can also
 * retrieve information about devices.
 * @author Daniel F. Martins
 */
public class TestConnectionCommand extends AbstractOBEXFTPCommand<DevInfo> {

    /** Logger. */
    private static final Logger log = Logger.getLogger(TestConnectionCommand.class);
    /** Object used to parse the device information file. */
    private DevInfoFileParser devInfoParser;
    /** Object used to retrieve internal configuration. */
    private MetaConfigurationReader configProperties;

    /**
     * Create a new instance of TestConnectionCommand.
     * @param configHolder Component that manages the configuration.
     * @param devInfoParser Object used to parse the device information file.
     */
    public TestConnectionCommand(ConfigurationHolder configHolder, DevInfoFileParser devInfoParser) {
        super(configHolder, "Fetching device info");

        if (devInfoParser == null) {
            throw new IllegalArgumentException("The devInfoParser argument cannot be null");
        }

        this.configProperties = DefaultMetaConfigurationReader.getInstance();
        this.devInfoParser = devInfoParser;
    }

    @Override
    public DevInfo execute() throws OBEXFTPException, OBEXException, InterruptedException {
        List<String> args = new LinkedList<String>();

        File devInfoFile = null;
        File workingFolder = null;

        if (getConfiguration() != null && getConfiguration().isFetchDeviceInfo()) {
            log.debug("Trying to fetch some information about the connected device");

            args.addAll(Arrays.asList(new String[]{"--get", "telecom/devinfo.txt"}));

            devInfoFile = configProperties.getConfigFile();
            workingFolder = devInfoFile.getParentFile();
        } else {
            log.debug("Trying a simple connection test");

            args.addAll(Arrays.asList(new String[]{"--chdir", "/", "--list"}));
        }

        Thread.sleep(1);
        runCommand(workingFolder, args, false);

        if (devInfoFile != null) {
            return devInfoParser.parseFile();
        } else {
            return null;
        }
    }
}