package net.sourceforge.obexftpfrontend.persistence;

import com.thoughtworks.xstream.XStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import net.sourceforge.obexftpfrontend.model.Configuration;
import org.apache.log4j.Logger;

/**
 * Default implementation of the ConfigurationPersistence interface, which
 * enables the application to persist its configuration in XML files.
 * @author Daniel F. Martins
 */
public class XMLConfigurationPersistence implements ConfigurationPersistence {

    /** Logger. */
    private static final Logger log = Logger.getLogger(XMLConfigurationPersistence.class);
    /** XStream object used to read and write the XML files. */
    private XStream xstream;
    /* Meta configuration. */
    private MetaConfigurationReader configProperties;

    /**
     * Create a new instance of XMLConfigurationPersistence.
     */
    public XMLConfigurationPersistence() {
        super();

        this.xstream = new XStream();
        this.configProperties = DefaultMetaConfigurationReader.getInstance();
    }

    @Override
    public void saveConfiguration(Configuration config) {
        try {
            log.info("Saving the Configuration object");
            PrintWriter out = new PrintWriter(new FileOutputStream(configProperties.getConfigFile()));
            xstream.toXML(config, out);
            out.close();
        } catch (Exception exc) {
            log.error("Error while trying to save the configuration", exc);
        }
    }

    @Override
    public Configuration loadConfiguration() {
        Configuration config = null;

        try {
            log.debug("Trying to load the configuration");
            FileInputStream in = new FileInputStream(configProperties.getConfigFile());
            config = (Configuration) xstream.fromXML(in);
            in.close();
        } catch (Exception exc) {
            log.error("Error while trying to load the configuration", exc);
        }

        return config;
    }

    /**
     * Whether the configuration file exists.
     * @return Whether the configuration file exists.
     */
    @Override
    public boolean configurationExists() {
        return configProperties.getConfigFile().exists();
    }
}