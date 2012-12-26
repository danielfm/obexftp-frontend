package net.sourceforge.obexftpfrontend.model;

/**
 * This interface must be implemented by the classes that want to manage the
 * configuration object.
 * @author Daniel F. Martins
 */
public interface ConfigurationHolder {

    /**
     * Get the configuration object.
     * @return Configuration object.
     */
    Configuration getConfiguration();

    /**
     * Register a listener to be notified when the configurations changes.
     * @param listener Listener object to register.
     */
    void addConfigurationListener(ConfigurationListener listener);

    /**
     * Unregister a listener that is observing changes on the configurations.
     * @param listener Listener object to unregister.
     */
    void removeConfigurationListener(ConfigurationListener listener);
}