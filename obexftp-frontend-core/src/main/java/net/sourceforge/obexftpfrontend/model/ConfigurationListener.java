package net.sourceforge.obexftpfrontend.model;

/**
 * All classes that want to be notified when the configuration object changes
 * must implement this interface.
 * @author Daniel F. Martins
 */
public interface ConfigurationListener {

    /**
     * When the configuration changes, the configuration holder notifies
     * its listeners by calling this method.
     * @param event The event object.
     */
    void configurationChanged(ConfigurationEvent event);
}