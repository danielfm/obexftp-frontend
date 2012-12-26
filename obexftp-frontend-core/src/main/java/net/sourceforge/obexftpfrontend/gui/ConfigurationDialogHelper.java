package net.sourceforge.obexftpfrontend.gui;

import net.sourceforge.obexftpfrontend.gui.model.FileTypeColumnModel;
import net.sourceforge.obexftpfrontend.gui.model.FileTypeExtensionsTableModel;
import javax.swing.event.ChangeEvent;
import net.sourceforge.obexftpfrontend.model.ConfigurationListener;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableRowSorter;
import net.sourceforge.obexftpfrontend.command.OBEXFTPCommandLifecycleEvent;
import net.sourceforge.obexftpfrontend.command.OBEXFTPCommandQueueListener;
import net.sourceforge.obexftpfrontend.command.OBEXFTPCommandQueue;
import net.sourceforge.obexftpfrontend.command.TestConnectionCommand;
import net.sourceforge.obexftpfrontend.command.DefaultOBEXFTPCommandConsumer;
import net.sourceforge.obexftpfrontend.command.DefaultOBEXFTPCommandQueue;
import net.sourceforge.obexftpfrontend.model.Configuration;
import net.sourceforge.obexftpfrontend.model.ConfigurationEvent;
import net.sourceforge.obexftpfrontend.model.ConfigurationHolder;
import net.sourceforge.obexftpfrontend.model.DevInfo;
import net.sourceforge.obexftpfrontend.model.TransportType;
import net.sourceforge.obexftpfrontend.persistence.ConfigurationPersistence;
import net.sourceforge.obexftpfrontend.persistence.XMLConfigurationPersistence;
import net.sourceforge.obexftpfrontend.obexftp.DevInfoFileParser;
import net.sourceforge.obexftpfrontend.obexftp.OBEXFTPException;
import net.sourceforge.obexftpfrontend.obexftp.OBEXFTPNotFoundException;
import org.apache.log4j.Logger;

/**
 * ConfigurationDialog UI helper class.
 * @author Daniel F. Martins
 */
public class ConfigurationDialogHelper extends AbstractUIHelper<ConfigurationDialog> implements ConfigurationHolder, OBEXFTPCommandQueueListener {

    /** Logger. */
    private static final Logger log = Logger.getLogger(ConfigurationDialogHelper.class);
    /** Registered configuration listeners. */
    private List<ConfigurationListener> configListenerList;
    /** ConfigurationPersistence implementation to be used. */
    private ConfigurationPersistence persistence;
    /** Command used to test the connection. */
    private TestConnectionCommand connectCommand;
    /** Command queue to be used. */
    private OBEXFTPCommandQueue queue;
    /** File chooser dialog used to select 'obexftp' program. */
    private JFileChooser obexftpChooser;
    /** Table model that displays the file extension mapping. */
    private FileTypeExtensionsTableModel extensionsTableModel;

    /**
     * Create a new instance of ConfigurationDialogHelper.
     * @param dialog ConfigurationDialog window.
     */
    public ConfigurationDialogHelper(ConfigurationDialog dialog) {
        super(dialog);

        obexftpChooser = new JFileChooser();
        extensionsTableModel = new FileTypeExtensionsTableModel();
    }

    /**
     * Initialize the Configuration dialog.
     * @param devInfoFileParser Object used to parse the device information.
     */
    public void initialize(DevInfoFileParser devInfoFileParser) {
        setupTabbedPaneDescriptionListener();

        setupConfiguration();
        setupTestConnectionCommand(devInfoFileParser);

        setupFileTypeTableModel();

        setupFileChooser();

        loadConfiguration();
        setupConnectionTypeListener();
    }

    @Override
    public void addConfigurationListener(ConfigurationListener listener) {
        configListenerList.add(listener);
        log.debug("ConfigurationListener added");
    }

    @Override
    public void removeConfigurationListener(ConfigurationListener listener) {
        configListenerList.remove(listener);
        log.debug("ConfigurationListener removed");
    }

    @Override
    public void commandAdded(OBEXFTPCommandLifecycleEvent event) {
        log.debug("commandAdded() called");
        enableTestConnectionComponents(false);
    }

    @Override
    public void commandRemoved(OBEXFTPCommandLifecycleEvent event) {
        log.debug("commandRemoved() called");
    }

    @Override
    public void commandExecuted(OBEXFTPCommandLifecycleEvent event) {
        log.debug("commandExecuted() called");
        enableTestConnectionComponents(true);

        switch (event.getStatus()) {
            case ERROR:
                if (event.getResult() instanceof OBEXFTPNotFoundException) {
                    log.warn("Obexftp application not found");
                    showErrorMessage("Obexftp application not found");
                } else if (event.getResult() instanceof OBEXFTPException) {
                    log.warn("Cannot connect to the device. It might not be a connection error, but an error while trying to fetch the device info file.");

                    String errorMessage = "<html>Connection error. ";
                    if (window.getDeviceInfoCheckbox().isSelected()) {
                        errorMessage += "Disable the <strong>device info fetching</strong> and try again.";
                    }
                    errorMessage += "</html>";

                    showErrorMessage(errorMessage);
                }

                break;
            case SUCCESS:
                log.info("Connection established");
                String successMsg = "Connection established.";

                if (event.getResult() != null) {
                    DevInfo info = (DevInfo) event.getResult();
                    successMsg = "<html>Device <strong>" + info.getManufacturer() + " " + info.getModel() + "</strong> detected.</html>";
                }
                showSuccessMessage(successMsg);

                break;
        }
    }

    @Override
    public void prepareWindow() {
        log.debug("Restoring the initial dialog state before show the dialog to the user");
        loadConfiguration();
        enableTestConnectionComponents(true);
        window.getTabbedPane().setSelectedIndex(0);
        window.getRootPane().setDefaultButton(window.getOkButton());
    }

    /**
     * Add a change listener to the tabbed pane in order to modify the
     * description label at the top.
     */
    public void setupTabbedPaneDescriptionListener() {
        window.getTabbedPane().addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent event) {
                switch (window.getTabbedPane().getSelectedIndex()) {
                    case 0:
                        window.getDescriptionLabel().setText("Fill the form below to tell ObexFTP how to access your device");
                        break;
                    case 1:
                        window.getDescriptionLabel().setText("Configure how the device's files are displayed to you");
                        break;
                }
            }
        });

        /* fire a fake 'stateChanged' event */
        for (ChangeListener listener : window.getTabbedPane().getChangeListeners()) {
            ChangeEvent changeEvent = new ChangeEvent(window.getTabbedPane());
            listener.stateChanged(changeEvent);
        }
    }

    @Override
    public Configuration getConfiguration() {
        Configuration config = new Configuration();

        config.setObexftpLocation(window.getObexPathTextField().getText());
        config.setConnectionLine(window.getValueTextField().getText());
        config.setTransport((TransportType) window.getTransportComboBox().getSelectedItem());

        if (window.getTimeoutCheckBox().isSelected()) {
            config.setObexftpTimeout((Integer) window.getTimeoutSpinner().getValue());
        } else {
            config.setObexftpTimeout(-1);
        }

        config.setValidateListing(window.getValidateObexListing().isSelected());
        config.setFetchDeviceInfo(window.getDeviceInfoCheckbox().isSelected());

        Configuration extConfig = extensionsTableModel.getConfiguration();
        config.replaceFileMapping(extConfig.getFileMapping());

        log.debug("Returning the current configuration");
        return config;
    }

    /**
     * Load the configuration info from the disk.
     * @return Saved configuration object.
     */
    public Configuration loadConfiguration() {
        Configuration config = persistence.loadConfiguration();

        if (config == null) {
            config = Configuration.createDefaultConfiguration();
        }

        window.getObexPathTextField().setText(config.getObexftpLocation());
        window.getTransportComboBox().setSelectedItem(config.getTransport());
        window.getValueTextField().setText(config.getConnectionLine());

        int timeout = config.getObexftpTimeout();

        if (timeout == -1) {
            window.getTimeoutCheckBox().setSelected(false);
            window.getTimeoutSpinner().setValue(0);
        } else {
            window.getTimeoutCheckBox().setSelected(true);
            window.getTimeoutSpinner().setValue(timeout);
        }

        window.getDeviceInfoCheckbox().setSelected(config.isFetchDeviceInfo());
        window.getValidateObexListing().setSelected(config.isValidateListing());

        extensionsTableModel.setConfiguration(config);

        log.info("Loaded the configuration from the disk");

        return config;
    }

    /**
     * Setup the configuration persistence manager and the listeners list.
     */
    public void setupConfiguration() {
        configListenerList = new LinkedList<ConfigurationListener>();
        log.debug("Created the ConfigurationListener list object");

        persistence = new XMLConfigurationPersistence();
        log.debug("Created the configuration persistence manager");
    }

    /**
     * Setup the objects that perform the connection test procedure.
     * @param devInfoFileParser Object used to parse the device information
     * file.
     */
    public void setupTestConnectionCommand(DevInfoFileParser devInfoFileParser) {
        log.debug("Created the OBEXResponseParser object");

        queue = new DefaultOBEXFTPCommandQueue(1);
        queue.addCommandExecutionListener(this);
        log.debug("Created the CommandQueue object");

        new Thread(new DefaultOBEXFTPCommandConsumer(queue)).start();
        log.debug("Started the CommandConsumer object");

        connectCommand = new TestConnectionCommand(this, devInfoFileParser);
        log.debug("ConnectionTest command created");
    }

    /**
     * Setup the file category mapping table.
     */
    public void setupFileTypeTableModel() {
        JTable table = window.getFileTypeTable();

        table.setModel(extensionsTableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setColumnModel(new FileTypeColumnModel());
        table.setRowHeight(20);

        table.setRowSorter(new TableRowSorter<FileTypeExtensionsTableModel>(extensionsTableModel));
    }

    /**
     * Save the user's preferences to disk.
     */
    public void saveConfiguration() {
        Configuration config = getConfiguration();
        persistence.saveConfiguration(config);

        log.info("Configuration saved");

        log.debug("Notifying the registered ConfigurationListeners");
        for (ConfigurationListener listener : configListenerList) {
            listener.configurationChanged(new ConfigurationEvent(config));
        }
        log.debug("ConfigurationListeners notified");
    }

    /**
     * Setup the file chooser dialog used to select the 'obexftp' program.
     */
    public void setupFileChooser() {
        obexftpChooser.setDialogTitle("Find obexftp...");
        obexftpChooser.setFileFilter(new OBEXFTPFileFilter());

        log.debug("ObexFTP file chooser configured");
    }

    /**
     * Enable the components during the 'test connection' operation.
     * @param b Enable or disable those components.
     */
    public void enableTestConnectionComponents(boolean b) {
        String state = b ? "Enabling" : "Disabling";
        log.debug(state + " the connection components");

        for (Component comp : window.getObexFTPPanel().getComponents()) {
            comp.setEnabled(b);
        }

        window.getConnectionProgressLabel().setEnabled(true);
        window.getConnectionProgressLabel().setVisible(!b);
    }

    /**
     * Callback method used when the user check or un-check the
     * timeout checkbox in the Configuration dialog.
     * @param evt The event object generated by the user action.
     */
    public void timeoutChanged(ItemEvent evt) {
        boolean selected = window.getTimeoutCheckBox().isSelected();

        window.getTimeoutSpinner().setEnabled(selected);
        window.getTimeoutLabel().setEnabled(selected);
    }

    /**
     * Open the file chooser dialog that allows the user to easily find the
     * 'obexftp' program.
     */
    public void findObexFTP() {
        try {
            File obexftp = new File(window.getObexPathTextField().getText());

            if (obexftp.isDirectory()) {
                obexftpChooser.setCurrentDirectory(obexftp);
            } else {
                obexftpChooser.setCurrentDirectory(obexftp.getParentFile());
                obexftpChooser.setSelectedFile(obexftp);
            }
        } catch (Exception exc) {
            /* nothing to do here... really */
        }

        if (obexftpChooser.showOpenDialog(window) == JFileChooser.APPROVE_OPTION) {
            window.getObexPathTextField().setText(obexftpChooser.getSelectedFile().getAbsolutePath());
        }
    }

    /**
     * Callback method that is invoked when the user activates the connection
     * test.
     */
    public void testConnection() {
        try {
            Configuration config = getConfiguration();

            log.info("Trying to connect to the device");
            connectCommand.setConfiguration(config);
            queue.append(connectCommand);
        } catch (Exception exc) {
            log.warn("Error while executing the connection test command", exc);
        }
    }

    /**
     * Callback method that is invoked when the user activates the 'ok' button.
     */
    public void ok() {
        saveConfiguration();
        cancel();
    }

    /**
     * Callback method that is invoked when the user activates the 'cancel'
     * button.
     */
    public void cancel() {
        sendFakeWindowClosingEvent();
    }

    /**
     * Setup the listener that updates the Help string depending on the
     * selected TransportType.
     */
    private void setupConnectionTypeListener() {
        JTextField value = window.getValueTextField();
        JComboBox transport = window.getTransportComboBox();

        ItemListener listener = new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent event) {
                TransportType type = (TransportType) event.getItem();

                if (type != null) {
                    switch (type) {
                        case BLUETOOTH:
                            setConnectionTypeDescriptions("Pair the device, type its address at the Connection line field.", "<address>[, OBEXFTP channel]");
                            break;
                        case IRDA:
                            setConnectionTypeDescriptions("You don't need to type anything at the Connection line field.", "");
                            break;
                        case NETWORK_HOST:
                            setConnectionTypeDescriptions("Type the host address at the Connection line field.", "<address>");
                            break;
                        case TTY:
                            setConnectionTypeDescriptions("Type the full device path at the Connection line field.", "<device path>");
                            break;
                        case USB:
                            setConnectionTypeDescriptions("Type the USB device number at the Connection line field", "<device number>");
                            break;
                    }
                }
            }
        };

        window.getTransportComboBox().addItemListener(listener);

        String connectionLine = value.getText();
        listener.itemStateChanged(new ItemEvent(transport, 0, transport.getSelectedItem(), 0));
        value.setText(connectionLine);
    }

    /**
     * Display information that explains how to connect to a OBEX-enabled.
     * @param labelText What text should be displayed at the description label.
     * @param connectionLine What text should be displayed at the connection
     * line field.
     */
    private void setConnectionTypeDescriptions(String labelText, String connectionLine) {
        JLabel label = window.getConnectionHelpLabel();
        JTextField value = window.getValueTextField();

        label.setText(labelText);
        value.setText(connectionLine);
    }
}