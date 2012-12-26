package net.sourceforge.obexftpfrontend.gui.action;

import java.awt.Window;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

/**
 *
 * @author Daniel F. Martins
 */
public abstract class DefaultAction extends AbstractAction {

    private Window parentWindow;

    public DefaultAction(Window window) {
        super();

        this.parentWindow = window;
    }

    /**
     * Get the parent window.
     * @return Parent window.
     */
    protected Window getParentWindow() {
        return parentWindow;
    }

    /**
     * Show an error message to the user.
     * @param message Message to display.
     */
    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(parentWindow, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Show a success message to the user.
     * @param message Message to display.
     */
    public void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(parentWindow, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Show a success message to the user.
     * @param message Message to display.
     */
    public String showInputMessage(String message) {
        return JOptionPane.showInputDialog(parentWindow, message, "Input", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Show a confirmation message to the user.
     * @param message Message to display.
     * @param title Title of the message.
     * @return Whether the users clicked agreed with the message.
     */
    public boolean showConfirmMessage(String message, String title) {
        return JOptionPane.showConfirmDialog(parentWindow, message, title, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }
}
