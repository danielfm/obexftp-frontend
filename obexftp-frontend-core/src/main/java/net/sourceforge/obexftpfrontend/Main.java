package net.sourceforge.obexftpfrontend;

import java.awt.EventQueue;
import java.util.logging.Level;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import net.sourceforge.obexftpfrontend.gui.MainFrame;
import org.apache.log4j.Logger;

/**
 * Application's main class.
 * @author Daniel F. Martins
 */
public class Main {

    private static final Logger log = Logger.getLogger(Main.class);

    /**
     * Create a new instance of Main.
     */
    public Main() {
        super();
    }

    /**
     * Application's main method.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        new Main().startup(args);
    }

    /**
     * Startup method.
     * @param args Command line arguments.
     */
    public void startup(final String[] args) {

        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception ex) {
                }
                new MainFrame().setVisible(true);
            }
        });
    }
}