package net.sourceforge.obexftpfrontend.command;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import net.sourceforge.obexftpfrontend.model.ConfigurationListener;
import net.sourceforge.obexftpfrontend.model.Configuration;
import net.sourceforge.obexftpfrontend.model.ConfigurationEvent;
import net.sourceforge.obexftpfrontend.model.ConfigurationHolder;
import net.sourceforge.obexftpfrontend.model.OBEXElement;
import net.sourceforge.obexftpfrontend.model.TransportType;
import net.sourceforge.obexftpfrontend.obexftp.OBEXException;
import net.sourceforge.obexftpfrontend.obexftp.OBEXFTPException;
import org.apache.log4j.Logger;

/**
 * Abstract implementation of the OBEXFTPCommand interface.
 * @author Daniel F. Martins
 */
public abstract class AbstractOBEXFTPCommand<T> implements OBEXFTPCommand<T>, ConfigurationListener {

    /** Logger. */
    private static final Logger log = Logger.getLogger(AbstractOBEXFTPCommand.class);
    
    /** Object responsible to run command-lines. */
    private OBEXFTPCommandLineRunner commandLineRunner;
    
    /** Application configuration. */
    private Configuration config;
    
    /** Command's description. */
    private String description;

    /**
     * Create a new instance of OBEXFTPAbstractCommand.
     * @param configHolder Component that manages the configuration.
     * @param description Command's description.
     */
    public AbstractOBEXFTPCommand(ConfigurationHolder configHolder, String description) {
        super();

        this.description = description;
        commandLineRunner = new DefaultOBEXFTPCommandLineRunner();

        if (configHolder != null) {
            config = configHolder.getConfiguration();
        }
    }

    @Override
    public void configurationChanged(ConfigurationEvent event) {
        log.debug("configurationChanged method called");
        this.config = event.getConfiguration();
    }

    @Override
    public void setConfiguration(Configuration config) {
        this.config = config;
    }

    protected Configuration getConfiguration() {
        return config;
    }

    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Get the CommandLineRunner object.
     * @return CommandLineRunner object.
     */
    protected OBEXFTPCommandLineRunner getCommandLineRunner() {
        return commandLineRunner;
    }

    /**
     * Set the CommandLineRunner object.
     * @param runner CommandLineRunner object.
     */
    protected void setCommandLineRunner(OBEXFTPCommandLineRunner runner) {
        this.commandLineRunner = runner;
    }

    /**
     * Return a list of obexftp parameters used to "walk" through the device's
     * file system.
     * @param element Element to use in this method.
     * @return A List of Strings that contains the obexftp chdir parameters.
     */
    protected List<String> convertPath(OBEXElement element) {
        List<String> args = new LinkedList<String>();
        if (element == null) {
            return args;
        }

        StringBuffer buffer = new StringBuffer();
        args.add("--chdir");

        int cur = 0;
        for (String folder : element.getStringPath()) {
            if (cur++ > 1) {
                buffer.append("/");
            }
            buffer.append(folder);
        }

        args.add(buffer.toString());
        return args;
    }

    /**
     * Based on the configuration, create an array of Strings which contains
     * the command line parameters that are necessary to connect to the
     * chosen device.
     * @return An array of Strings.
     */
    protected List<String> getDeviceConnectionInfo() {
        List<String> result = new LinkedList<String>();

        if (config == null) {
            return result;
        }

        TransportType transport = config.getTransport();
        result.add(transport.getFlag());

        switch (transport) {
            case BLUETOOTH:
                if (config.getConnectionLine() != null && config.getConnectionLine().trim().length() > 0) {
                    String[] params = config.getConnectionLine().split(",");
                    if (params.length >= 1) {
                        result.add(params[0].trim());
                        if (params.length > 1) {
                            result.add("--channel");
                            result.add(params[1].trim());
                        }
                    }
                }
                break;
            case NETWORK_HOST:
            case TTY:
            case USB:
                if (config.getConnectionLine() != null) {
                    result.add(config.getConnectionLine());
                }
                break;
        }

        return result;
    }

    /**
     * Run the obexftp, passing the necessary input information. This method
     * also get the response stream to process.
     * @param workingFolder Local working folder.
     * @param params Input information to pass to the process.
     * @param ignoreTimeout Whether the command may take longer than expected.
     * @return The process object.
     * @throws OBEXFTPException if the obexftp cannot be executed by some reason.
     * @throws OBEXException if the obexftp returns some error.
     * @throws IllegalArgumentException if some argument is invalid.
     * @throws InterruptedException
     */
    protected Process runCommand(File workingFolder, List<String> params, boolean ignoreTimeout) throws OBEXFTPException, OBEXException, InterruptedException {
        List<String> list = new LinkedList<String>();

        list.add(config.getObexftpLocation());
        list.addAll(getDeviceConnectionInfo());
        list.addAll(params);

        return commandLineRunner.run(workingFolder, list, (ignoreTimeout || config.getObexftpTimeout() == -1) ? 0 : config.getObexftpTimeout());
    }
}