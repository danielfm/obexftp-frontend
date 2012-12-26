package net.sourceforge.obexftpfrontend.persistence;

import net.sourceforge.obexftpfrontend.model.Configuration;

/**
 * This interface encapsulates operations related to the user preference's 
 * persistence.
 * @author Daniel F. Martins
 */
public interface ConfigurationPersistence {

    /**
     * Save the given user's preferences object.
     * @param config Configuration object that contains the user's preferences.
     */
    void saveConfiguration(Configuration config);

    /**
     * Load the user's preferences from the disk.
     * @return Configuration object that contains the user's preferences.
     */
    Configuration loadConfiguration();

    /**
     * Whether the configuration file alrady exists.
     * @return Whether the configuration file already exists.
     */
    boolean configurationExists();
}