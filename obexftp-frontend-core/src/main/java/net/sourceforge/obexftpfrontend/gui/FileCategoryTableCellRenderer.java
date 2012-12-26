package net.sourceforge.obexftpfrontend.gui;

import net.sourceforge.obexftpfrontend.gui.model.FileTypeExtensionsTableModel;
import java.awt.Component;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import net.sourceforge.obexftpfrontend.model.FileCategory;
import net.sourceforge.obexftpfrontend.model.FileMapping;

/**
 * This cell renderer is responsible to render the 'category' column of the
 * file extension mapping table.
 * @author Daniel F. Martins
 */
public class FileCategoryTableCellRenderer extends DefaultTableCellRenderer {

    /**
     * Create a new instance of FileTypeTableCellRenderer.
     */
    public FileCategoryTableCellRenderer() {
        super();
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

        FileTypeExtensionsTableModel model = (FileTypeExtensionsTableModel) table.getModel();
        List<FileMapping> mapping = model.getConfiguration().getFileMapping();

        row = table.getRowSorter().convertRowIndexToModel(row);

        if (row >= 0 && row < mapping.size()) {
            FileCategory category = mapping.get(row).getCategory();
            setIcon(category.getIcon());
        }

        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
    }
}