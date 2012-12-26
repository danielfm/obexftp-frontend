package net.sourceforge.obexftpfrontend.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Main configuration object, which is used to store the user's preferences.
 * @author Daniel F. Martins
 */
public class Configuration implements Serializable {

    /** location of 'obexftp' program. */
    private String obexftpLocation = "";
    /** Transport type used to connect to the device. */
    private TransportType transport = TransportType.IRDA;
    /** Connection line used to specify how to find the device. */
    private String connectionLine = "";
    /** 'obexftp' timeout, in seconds. */
    private int obexftpTimeout = 5;
    /** Whether the OBEX response document should be validated.  */
    private boolean validateListing = true;
    /** Whether the device info file can be fetched. */
    private boolean fetchDeviceInfo = true;
    /** File extension mapping list. */
    private List<FileMapping> fileMapping;

    /**
     * Create a new instance of Configuration.
     */
    public Configuration() {
        super();
        fileMapping = new ArrayList<FileMapping>();
    }

    /**
     * Create a new instance of Configuration.
     * @param obexftpLocation Location of 'obexftp' program
     * @param transport Transport type used to connect to the device.
     * @param connectionLine Connection line used to specify how to find the
     * device.
     * @param obexftpTimeout 'obexftp' timeout, in seconds.
     * @param validateListing Whether the OBEX response document should be
     * validated.
     * @param fetchDeviceInfo Whether the device info file can be fetched.
     */
    public Configuration(String obexftpLocation, TransportType transport, String connectionLine, int obexftpTimeout, boolean validateListing, boolean fetchDeviceInfo) {
        this();
        setObexftpLocation(obexftpLocation);
        setTransport(transport);
        setConnectionLine(connectionLine);
        setObexftpTimeout(obexftpTimeout);
        setValidateListing(validateListing);
        setFetchDeviceInfo(fetchDeviceInfo);
    }

    /**
     * Get the location of 'obexftp' program.
     * @return Location of 'obexftp' program.
     */
    public String getObexftpLocation() {
        return obexftpLocation;
    }

    /**
     * Set the location of 'obexftp' program.
     * @param obexftpLocation Location of 'obexftp' program.
     */
    public void setObexftpLocation(String obexftpLocation) {
        this.obexftpLocation = obexftpLocation;
    }

    /**
     * Get the transport type used to connect to the device.
     * @return Transport type used to connect to the device.
     */
    public TransportType getTransport() {
        return transport;
    }

    /**
     * Set the transport type used to connect to the device.
     * @param transport Transport type used to connect to the device.
     */
    public void setTransport(TransportType transport) {
        if (transport == null) {
            transport = TransportType.IRDA;
        }
        this.transport = transport;
    }

    /**
     * Get the connection line used to specify how to find the device.
     * @return Connection line used to specify how to find the device.
     */
    public String getConnectionLine() {
        return connectionLine;
    }

    /**
     * Set the connection line used to specify how to find the device.
     * @param connectionLine Connection line used to specify how to find the
     * device.
     */
    public void setConnectionLine(String connectionLine) {
        this.connectionLine = connectionLine;
    }

    /**
     * Get the 'obexftp' timeout, in seconds.
     * @return Timeout interval.
     */
    public int getObexftpTimeout() {
        return obexftpTimeout;
    }

    /**
     * Set the 'obexftp' timeout, in seconds.
     * @param obexftpTimeout Timeout interval.
     */
    public void setObexftpTimeout(int obexftpTimeout) {
        this.obexftpTimeout = obexftpTimeout;
    }

    /**
     * Get whether the OBEX response document should be validated.
     * @return Whether the OBEX response document should be validated.
     */
    public boolean isValidateListing() {
        return validateListing;
    }

    /**
     * Set whether the OBEX response document should be validated.
     * @param validateListing Whether the OBEX response document should be
     * validated.
     */
    public void setValidateListing(boolean validateListing) {
        this.validateListing = validateListing;
    }

    /**
     * Get whether the device info file can be fetched.
     * @return Whether the device info file can be fetched.
     */
    public boolean isFetchDeviceInfo() {
        return fetchDeviceInfo;
    }

    /**
     * Set whether the device info file can be fetched.
     * @param fetchDeviceInfo Whether the device info file can be fetched.
     */
    public void setFetchDeviceInfo(boolean fetchDeviceInfo) {
        this.fetchDeviceInfo = fetchDeviceInfo;
    }

    /**
     * Get the file extension mapping list.
     * @return File extension mapping list.
     */
    public List<FileMapping> getFileMapping() {
        return Collections.unmodifiableList(fileMapping);
    }

    /**
     * Add a file extension mapping to the list.
     * @param mapping Mapping to add.
     */
    public void addFileMapping(FileMapping mapping) {
        fileMapping.add(mapping);
    }

    /**
     * Replace the current file mapping list by the given one.
     * @param mapping List of mappings to replace the mappings of this
     * object.
     */
    public void replaceFileMapping(List<FileMapping> mapping) {

        fileMapping.clear();
        for (FileMapping fm : mapping) {
            fileMapping.add(fm.copy());
        }
    }

    /**
     * Return a default configuration file. This method is used to create
     * a default configuration file when it cannot be found.
     * @return Default configuration file.
     */
    public static Configuration createDefaultConfiguration() {
        Configuration config = new Configuration();

        config.addFileMapping(new FileMapping(FileCategory.APPLICATION, "Application", ""));
        config.addFileMapping(new FileMapping(FileCategory.DOCUMENT, "Document", ".doc .xls .ppt .odf .pdf"));
        config.addFileMapping(new FileMapping(FileCategory.IMAGE, "Image", ".jpg .jpeg .png .gif .bmp"));
        config.addFileMapping(new FileMapping(FileCategory.MUSIC, "Music", ".mp3 .mid .amr .wav"));
        config.addFileMapping(new FileMapping(FileCategory.THEME, "Theme", ""));
        config.addFileMapping(new FileMapping(FileCategory.VIDEO, "Video", ".avi .divx .3gp .mpg .mpeg"));

        config.setObexftpLocation("/usr/bin/obexftp");
        return config;
    }
}