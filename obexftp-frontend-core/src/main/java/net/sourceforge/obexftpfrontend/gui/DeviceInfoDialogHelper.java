package net.sourceforge.obexftpfrontend.gui;

import java.text.DateFormat;
import java.util.Locale;
import net.sourceforge.obexftpfrontend.command.OBEXFTPCommandLifecycleEvent;
import net.sourceforge.obexftpfrontend.command.OBEXFTPCommandQueueListener;
import net.sourceforge.obexftpfrontend.command.OBEXFTPCommandQueue;
import net.sourceforge.obexftpfrontend.command.TestConnectionCommand;
import net.sourceforge.obexftpfrontend.model.ConfigurationHolder;
import net.sourceforge.obexftpfrontend.model.DevInfo;
import net.sourceforge.obexftpfrontend.obexftp.DevInfoFileParser;
import net.sourceforge.obexftpfrontend.obexftp.OBEXFTPException;
import org.apache.log4j.Logger;

/**
 * This frame shows some basic information about the connected device.
 * @author Daniel F. Martins
 */
public class DeviceInfoDialogHelper extends AbstractUIHelper<DeviceInfoDialog> implements OBEXFTPCommandQueueListener {

    /** Logger. */
    private Logger log = Logger.getLogger(DeviceInfoDialogHelper.class);
    /** Object that manages the user's preferences. */
    private ConfigurationHolder configHolder;
    /** Command queue to be used. */
    private OBEXFTPCommandQueue queue;
    /** Object used to parse the device information file. */
    private DevInfoFileParser devInfoParser;
    /** Date formatter. */
    private DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL, Locale.ENGLISH);

    /**
     * Create a new instance of DeviceInfoDialogHelper.
     * @param window DeviceInfoDialog.
     * @param configHolder Object that manages the user's preferences.
     * @param queue Command queue to be used.
     * @param devInfoFileParser Object used to parse the device information
     * file.
     */
    public DeviceInfoDialogHelper(DeviceInfoDialog window, ConfigurationHolder configHolder, OBEXFTPCommandQueue queue, DevInfoFileParser devInfoParser) {
        super(window);

        this.configHolder = configHolder;
        this.devInfoParser = devInfoParser;
        this.queue = queue;

        queue.addCommandExecutionListener(this);
    }

    /**
     * Try to fetch the device info file from the device.
     * @param refresh Whether this operation represents a 'refresh' click
     * in the user interface.
     */
    public void readDevInfoFile(boolean refresh) {
        clearDevInfo();
        if (refresh) {
            try {
                TestConnectionCommand command = new TestConnectionCommand(configHolder, devInfoParser);
                queue.append(command);
            } catch (InterruptedException exc) {
                exc.printStackTrace();
            }
        } else {
            DevInfo file = null;
            try {
                file = devInfoParser.parseFile();
            } catch (OBEXFTPException ex) {
                log.error("Cannot parse the devinfo file", ex);
            }
            setDevInfo(file);
        }
    }

    /**
     * Fill the frame fields using the given DevInfo object.
     * @param file DevInfo object that contains information about the connected
     * device.
     */
    private void setDevInfo(DevInfo file) {
        if (file != null) {
            window.getFirmwareVersionTextField().setText(file.getFirmwareVersion());
            if (file.getParsedFirmwareDate() != null) {
                window.getFirmwareDateTextField().setText(dateFormat.format(file.getParsedFirmwareDate()));
            }

            window.getHardwareVersionTextField().setText(file.getHardwareVersion());
            window.getManufacturerTextField().setText(file.getManufacturer());
            window.getModelTextField().setText(file.getModel());
            window.getSerialNumberTextField().setText(file.getSerialNumber());
        }
    }

    /**
     * Clear the field values.
     */
    private void clearDevInfo() {
        window.getFirmwareVersionTextField().setText("-");
        window.getFirmwareDateTextField().setText("-");
        window.getHardwareVersionTextField().setText("-");
        window.getManufacturerTextField().setText("-");
        window.getModelTextField().setText("-");
        window.getSerialNumberTextField().setText("-");
    }

    @Override
    public void prepareWindow() {
        log.debug("Restoring the initial dialog state before show the dialog to the user");
        readDevInfoFile(false);
        window.getRootPane().setDefaultButton(window.getOkButton());
    }

    @Override
    public void commandAdded(OBEXFTPCommandLifecycleEvent event) {
        /* nothing here */
    }

    @Override
    public void commandRemoved(OBEXFTPCommandLifecycleEvent event) {
        if (event.getCommand() instanceof TestConnectionCommand) {
            window.getRefreshButton().setEnabled(false);
        }
    }

    @Override
    public void commandExecuted(OBEXFTPCommandLifecycleEvent event) {
        if (event.getStatus() == OBEXFTPCommandLifecycleEvent.ExecutionStatus.SUCCESS) {
            if (event.getResult() instanceof DevInfo) {
                setDevInfo((DevInfo) event.getResult());
            }
        }

        if (event.getCommand() instanceof TestConnectionCommand) {
            window.getRefreshButton().setEnabled(true);
        }
    }
}