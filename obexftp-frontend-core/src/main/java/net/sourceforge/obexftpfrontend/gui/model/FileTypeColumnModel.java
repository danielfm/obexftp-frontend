package net.sourceforge.obexftpfrontend.gui.model;

import net.sourceforge.obexftpfrontend.gui.*;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

/**
 * Column model used to define the columns displayed in the the file types table.
 * @author Daniel F. Martins
 */
public class FileTypeColumnModel extends DefaultTableColumnModel {

    /**
     * Create a new instance of FileTypeColumnModel.
     */
    public FileTypeColumnModel() {
        super();

        TableColumn category = new TableColumn();
        category.setHeaderValue("Category");
        category.setModelIndex(0);
        category.setCellRenderer(new FileCategoryTableCellRenderer());
        category.setPreferredWidth(10);
        category.setResizable(false);
        addColumn(category);

        TableColumn extensions = new TableColumn();
        extensions.setHeaderValue("File extensions");
        extensions.setModelIndex(1);
        extensions.setPreferredWidth(200);
        addColumn(extensions);
    }
}