/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sourceforge.obexftpfrontend.persistence;

import java.io.File;

/**
 * This interface is used when the application needs to read the meta
 * configuration.
 * @see net.sourceforge.obexftpfrontend.persistence.ConfigurationPersistence
 * @author Daniel F. Martins
 */
public interface MetaConfigurationReader {

    /**
     * Get the File object that represents the configuration file.
     * @return File object that represents the configuraiton file.
     */
    File getConfigFile();

    /**
     * Get the File object that represents the devinfo file.
     * @return File object that represents the devinfo file.
     */
    File getDevInfoFile();
}