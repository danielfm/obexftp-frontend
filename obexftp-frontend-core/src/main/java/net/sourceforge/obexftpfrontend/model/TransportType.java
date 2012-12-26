package net.sourceforge.obexftpfrontend.model;

/**
 * 'obexftp' can connect with a device using several transport types.
 * @author Daniel F. Martins
 */
public enum TransportType {

    /** Bluetooth connection */
    BLUETOOTH("Bluetooth", "--bluetooth"),
    /** Network connection */
    NETWORK_HOST("Network Host", "--network"),
    /** IrDA connection */
    IRDA("IrDA", "--irda"),
    /** USB connection */
    USB("USB", "--usb"),
    /** Serial connection */
    TTY("Serial", "--tty");
    /** Transport description. */
    private String description;
    /** 'obexftp' command-line flag. */
    private String flag;

    /**
     * Create the enumeration element.
     * @param description Transport description.
     * @param flag 'obexftp' command-line flag.
     */
    TransportType(String description, String flag) {
        this.description = description;
        this.flag = flag;
    }

    /**
     * Get the transport description.
     * @return Transport description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the 'obexftp' command-line flag.
     * @return 'obexftp' command-line flag.
     */
    public String getFlag() {
        return flag;
    }

    @Override
    public String toString() {
        return description;
    }
}