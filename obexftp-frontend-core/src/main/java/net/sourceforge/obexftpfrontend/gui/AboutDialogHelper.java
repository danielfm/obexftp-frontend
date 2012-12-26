package net.sourceforge.obexftpfrontend.gui;

import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.net.URI;
import org.apache.log4j.Logger;

/**
 * AboutDialog UI helper class.
 * @author Daniel F. Martins
 */
public class AboutDialogHelper extends AbstractUIHelper<AboutDialog> implements ClipboardOwner {

    /** Logger. */
    private static final Logger log = Logger.getLogger(AboutDialogHelper.class);

    /**
     * Create a new instance of AboutDialogHelper.
     * @param window AboutDialog window.
     */
    public AboutDialogHelper(AboutDialog window) {
        super(window);
    }

    @Override
    public void prepareWindow() {
        log.debug("Restoring the initial dialog state before show the dialog to the user");
        window.getRootPane().setDefaultButton(window.getOkButton());
    }

    @Override
    public void lostOwnership(Clipboard clipboard, Transferable contents) {
        /* do nothing */
    }

    /**
     * Open an URL in the system's default application
     * @param address URL address to be opened.
     */
    public void openURL(String address) {
        String prefix = "http";
        String fullAddr = prefix + "://" + address;

        try {
            log.debug("Trying to open '" + fullAddr + "' in the system's default app");
            Desktop.getDesktop().browse(new URI(prefix, address, null));
        } catch (Exception exc) {
            copyTextToClipboard(fullAddr);
            log.error("Cannot open " + fullAddr, exc);
            showErrorMessage("<html><p>Cannot open the the following URL:</p><p><strong>" + fullAddr + "</strong></p><p><br/>" + "The address has been copied to the clipboard.</p></strong>");
        }
    }

    /**
     * Copy the given text to the system clipboard.
     * @param text Text to copy to the system clipboard.
     */
    private void copyTextToClipboard(String text) {
        StringSelection selection = new StringSelection(text);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, this);
    }
}