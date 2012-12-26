package net.sourceforge.obexftpfrontend.model;

import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This object is used to store information about the connected device.
 * @author Daniel F. Martins
 */
public class DevInfo {

    /** Formatter used to format an "OBEX date". */
    private static final OBEXDateFormatter obexDateFormatter = new OBEXDateFormatter();
    /** Manufacturer string. */
    private String manufacturer = "";
    /** Device model string. */
    private String model = "";
    /** Firmware version string. */
    private String firmwareVersion = "";
    /** Firmware last update date. */
    private String firmwareDate = "";
    /** Hardware version string. */
    private String hardwareVersion = "";
    /** Device's serial number. */
    private String serialNumber = "";

    /**
     * Create a new instance of DevInfo.
     */
    public DevInfo() {
        super();
    }

    /**
     * Get the firmware version string.
     * @return Firmware version string.
     */
    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    /**
     * Set the firmware version string.
     * @param firmwareVersion Firmware version string.
     */
    public void setFirmwareVersion(String firmwareVersion) {
        if (firmwareVersion == null) {
            firmwareVersion = "";
        }
        this.firmwareVersion = firmwareVersion;
    }

    /**
     * Get the firmware date string.
     * @return Firmware date string.
     */
    public String getFirmwareDate() {
        return firmwareDate;
    }

    /**
     * Get the formatted firmware date.
     * @return Formatted firmware date.
     */
    public Date getParsedFirmwareDate() {
        Date date = null;
        try {
            date = obexDateFormatter.parse(firmwareDate);
        } catch (ParseException ex) {
            Logger.getLogger(DevInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return date;
    }

    /**
     * Set the firmware date string.
     * @param firmwareDate Firmware date string.
     */
    public void setFirmwareDate(String firmwareDate) {
        this.firmwareDate = firmwareDate;
    }

    /**
     * Get the hardware version string.
     * @return Hardware version string.
     */
    public String getHardwareVersion() {
        return hardwareVersion;
    }

    /**
     * Set the hardware version string.
     * @param hardwareVersion Hardware version string.
     */
    public void setHardwareVersion(String hardwareVersion) {
        if (hardwareVersion == null) {
            hardwareVersion = "";
        }
        this.hardwareVersion = hardwareVersion;
    }

    /**
     * Get the manufacturer string.
     * @return Manufacturer string.
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * Set the manufacturer string.
     * @param manufacturer Manufacturer string.
     */
    public void setManufacturer(String manufacturer) {
        if (manufacturer == null) {
            manufacturer = "";
        }
        this.manufacturer = manufacturer;
    }

    /**
     * Get the device model string.
     * @return Device model string.
     */
    public String getModel() {
        return model;
    }

    /**
     * Set the device model string.
     * @param model Device model string.
     */
    public void setModel(String model) {
        if (model == null) {
            model = "";
        }
        this.model = model;
    }

    /**
     * Get the serial number.
     * @return Serial number.
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * Set the serial number.
     * @param serialNumber Serial number.
     */
    public void setSerialNumber(String serialNumber) {
        if (serialNumber == null) {
            serialNumber = "";
        }
        this.serialNumber = serialNumber;
    }
}