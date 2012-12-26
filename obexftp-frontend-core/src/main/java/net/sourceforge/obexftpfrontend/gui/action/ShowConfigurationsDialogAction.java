package net.sourceforge.obexftpfrontend.gui.action;

import java.awt.EventQueue;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;
import net.sourceforge.obexftpfrontend.gui.ConfigurationDialog;
import net.sourceforge.obexftpfrontend.model.ConfigurationListener;
import net.sourceforge.obexftpfrontend.model.Configuration;
import net.sourceforge.obexftpfrontend.model.ConfigurationHolder;
import net.sourceforge.obexftpfrontend.obexftp.DevInfoFileParser;

/**
 * Action that is responsible to create and show the 'configuration' dialog.
 * @author Daniel F. Martins
 */
public class ShowConfigurationsDialogAction extends DefaultAction implements ConfigurationHolder {

    /**
     * Create a new instance of ShowConfigurationsDialogAction.
     * @param parent Parent frame.
     * @param devInfoFileParser Object used to parse the device information
     * file.
     */
    public ShowConfigurationsDialogAction(Window parent, DevInfoFileParser devInfoFileParser) {
        super(new ConfigurationDialog(parent, devInfoFileParser));

        /* Action info */
        putValue(NAME, "Configuration");
        putValue(SHORT_DESCRIPTION, "Show the configuration dialog");
        putValue(MNEMONIC_KEY, KeyEvent.VK_C);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0));
        putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/icon/configurations.png")));
        putValue(LARGE_ICON_KEY, new ImageIcon(getClass().getResource("/icon/configurations-tb.png")));
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                getParentWindow().setVisible(true);
            }
        });
    }

    @Override
    public void addConfigurationListener(ConfigurationListener listener) {
        ((ConfigurationDialog) getParentWindow()).addConfigurationListener(listener);
    }

    @Override
    public void removeConfigurationListener(ConfigurationListener listener) {
        ((ConfigurationDialog) getParentWindow()).removeConfigurationListener(listener);
    }

    @Override
    public Configuration getConfiguration() {
        return ((ConfigurationDialog) getParentWindow()).getConfiguration();
    }
}