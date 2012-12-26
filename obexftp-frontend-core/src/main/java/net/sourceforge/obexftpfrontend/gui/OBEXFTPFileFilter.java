package net.sourceforge.obexftpfrontend.gui;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 * Filter use in the JFileChooser that is used to select the 'obexftp' program.
 * @author Daniel F. Martins
 */
public class OBEXFTPFileFilter extends FileFilter {

    /**
     * Create a new instance of OBEXFTPFileFilter.
     */
    public OBEXFTPFileFilter() {
        super();
    }

    @Override
    public boolean accept(File file) {
        return "obexftp".equals(file.getName()) || file.isDirectory();
    }

    @Override
    public String getDescription() {
        return "OBEXFTP application";
    }
}