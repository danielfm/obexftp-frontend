package net.sourceforge.obexftpfrontend.util;

import javax.swing.ImageIcon;

/**
 * This class provides all ImageIcon objects used by the application to
 * each supported file type.
 * @author Daniel F. Martins
 */
public class FileCategoryUtils {

    private static FileCategoryUtils instance;
    

    static {
        instance = new FileCategoryUtils();
    }
    /* image representation of each file category */
    public final ImageIcon FOLDER_COLLAPSED_ICON = new ImageIcon(this.getClass().getResource("/icon/closed-folder.png"));
    public final ImageIcon FOLDER_EXPANDED_ICON = new ImageIcon(this.getClass().getResource("/icon/opened-folder.png"));
    public final ImageIcon UNKNOWN_ICON = new ImageIcon(this.getClass().getResource("/icon/unknown.png"));

    /**
     * Create a new instance of FileCategoryUtils.
     */
    private FileCategoryUtils() {
        super();
    }

    /**
     * Get the single instance of FileCategoryUtils.
     * @return Single instance of FileCategoryUtils.
     */
    public static FileCategoryUtils getInstance() {
        return instance;
    }
}