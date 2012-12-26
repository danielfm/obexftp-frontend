package net.sourceforge.obexftpfrontend.gui;

import net.sourceforge.obexftpfrontend.util.FileCategoryUtils;
import net.sourceforge.obexftpfrontend.model.ConfigurationListener;
import java.awt.Component;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import net.sourceforge.obexftpfrontend.model.Configuration;
import net.sourceforge.obexftpfrontend.model.ConfigurationEvent;
import net.sourceforge.obexftpfrontend.model.ConfigurationHolder;
import net.sourceforge.obexftpfrontend.model.FileMapping;
import net.sourceforge.obexftpfrontend.model.OBEXElement;
import net.sourceforge.obexftpfrontend.util.StringUtils;
import org.apache.log4j.Logger;

/**
 * Custom TreeCellRenderer that is used to render the device's file system.
 * @author Daniel F. Martins
 */
public class FileSystemCellRenderer extends DefaultTreeCellRenderer implements ConfigurationListener {

    /** Logger. */
    private static final Logger log = Logger.getLogger(FileSystemCellRenderer.class);
    /** User's preferences. */
    private Configuration config;
    /** Related tree component. */
    private JTree jtree;

    /**
     * Create a new instance of FileSystemCellRenderer.
     * @param holder Object that manages the user's preferences.
     */
    public FileSystemCellRenderer(ConfigurationHolder holder) {
        super();

        this.config = holder.getConfiguration();
        holder.addConfigurationListener(this);
    }

    @Override
    public void configurationChanged(ConfigurationEvent event) {
        this.config = event.getConfiguration();
        refreshJTable();
    }

    /**
     * Request the full rendering of the JTable component after a configuration
     * change event.
     */
    private void refreshJTable() {
        if (jtree != null) {
            log.debug("Refreshing the file system tree");
            jtree.repaint();
        }
    }

    @Override
    public Component getTreeCellRendererComponent(JTree jtree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(jtree, value, sel, expanded, leaf, row, hasFocus);
        this.jtree = jtree;

        if (value instanceof OBEXElement) {
            OBEXElement element = (OBEXElement) value;

            if (element.getType() == OBEXElement.OBEXElementType.FOLDER) {
                if (expanded) {
                    setIcon(FileCategoryUtils.getInstance().FOLDER_EXPANDED_ICON);
                } else {
                    setIcon(FileCategoryUtils.getInstance().FOLDER_COLLAPSED_ICON);
                }
            } else {
                String name = element.getName();
                boolean iconChanged = false;

                for (FileMapping mapping : config.getFileMapping()) {
                    if (StringUtils.isFileExtensionMatch(name, mapping.getExtensions())) {
                        setIcon(mapping.getCategory().getIcon());
                        iconChanged = true;
                        break;
                    }
                }

                if (!iconChanged) {
                    setIcon(FileCategoryUtils.getInstance().UNKNOWN_ICON);
                }
            }

            /* this is necessary to make Swing re-calculate the size of the tree node being rendered */
            setText("");
            setText(element.toString());
        }
        return this;
    }
}