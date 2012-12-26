package net.sourceforge.obexftpfrontend.persistence;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 * Default implementation of ConfigurationPropeopertiesReader that relies
 * on a properties file to store the meta configuration.
 * @author Daniel F. Martins
 */
public class DefaultMetaConfigurationReader implements MetaConfigurationReader {

    /** Logger. */
    private static Logger log = Logger.getLogger(DefaultMetaConfigurationReader.class);
    /** Configuration file. */
    private File configFile;
    /** Device information file. */
    private File devInfoFile;
    /** Singleton object. */
    private static final MetaConfigurationReader _instance;
    

    static {
        _instance = new DefaultMetaConfigurationReader();
    }

    /**
     * Create a new instance of DefaultMetaConfigurationReader.
     */
    private DefaultMetaConfigurationReader() {
        try {
            Properties properties = new Properties();
            properties.load(getClass().getClassLoader().getResourceAsStream("config.properties"));

            String userHome = System.getProperty("user.home") + System.getProperty("file.separator");

            configFile = new File(userHome + properties.getProperty("config.path"));
            devInfoFile = new File(userHome + properties.getProperty("devinfo.path"));
        } catch (IOException exc) {
            log.error("Error while loading the configuration properties file", exc);
        }
    }

    /**
     * Get the single instance of DefaultMetaConfigurationReader.
     * @return Single instance of DefaultMetaConfigurationReader.
     */
    public static MetaConfigurationReader getInstance() {
        return _instance;
    }

    @Override
    public File getConfigFile() {
        return configFile;
    }

    @Override
    public File getDevInfoFile() {
        return devInfoFile;
    }
}