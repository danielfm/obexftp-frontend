package net.sourceforge.obexftpfrontend.gui.model;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import net.sourceforge.obexftpfrontend.model.Configuration;
import net.sourceforge.obexftpfrontend.model.FileMapping;

/**
 * Table model used to configure the file types table.
 * @author Daniel F. Martins
 */
public class FileTypeExtensionsTableModel extends AbstractTableModel {

    /** User's preferences. */
    private Configuration config;

    /**
     * Create a new instance of FileTypeExtensionsTableModel.
     */
    public FileTypeExtensionsTableModel() {
        super();
        config = new Configuration();
    }

    @Override
    public int getRowCount() {
        return config.getFileMapping().size();
    }

    @Override
    public int getColumnCount() {
        /* category, extensions */
        return 2;
    }

    @Override
    public Object getValueAt(int row, int col) {
        List<FileMapping> fileMappingList = config.getFileMapping();

        if (row >= 0 && row < fileMappingList.size()) {
            FileMapping mapping = fileMappingList.get(row);
            if (col == 0) {
                return mapping.getName();
            } else if (col == 1) {
                return mapping.getExtensions();
            }
        }

        return "";
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        String v = (String) value;
        List<FileMapping> mappingList = config.getFileMapping();

        if (row >= 0 && row < mappingList.size() && col == 1) {
            FileMapping mapping = mappingList.get(row);
            mapping.setExtensions(v);
        }
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        /* only file extensions column is editable */
        return col == 1;
    }

    public void setConfiguration(Configuration config) {
        this.config.replaceFileMapping(config.getFileMapping());
    }

    public Configuration getConfiguration() {
        return config;
    }
}