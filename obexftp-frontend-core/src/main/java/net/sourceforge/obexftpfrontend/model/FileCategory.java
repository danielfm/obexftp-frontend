package net.sourceforge.obexftpfrontend.model;

import javax.swing.ImageIcon;

/**
 * All file categories currently supported by the application.
 * @author Daniel F. Martins
 */
public enum FileCategory {

    /** Documents (e.g. PDF, XLS, DOC, TXT) */
    DOCUMENT("document"),
    /** Musics (e.g. MP3, MP4, WAV, OGG) */
    MUSIC("music"),
    /** Videos (e.g. MP3, WMV, AVI, 3GP) */
    VIDEO("video"),
    /** Images (e.g. PNG, JPG, GIF, BMP) */
    IMAGE("picture"),
    /** Themes (e.g. THM) */
    THEME("theme"),
    /** Applications (e.g. JAR) */
    APPLICATION("application");

    private ImageIcon icon;

    /**
     * Create a new FileCategory element.
     * @param iconName Icon file that represents this file category.
     */
    FileCategory(String iconName) {
        icon = new ImageIcon(getClass().getResource("/icon/" + iconName + ".png"));
    }

    /**
     * Get the Icon that represents this file category.
     * @return Icon that represents this file category.
     */
    public ImageIcon getIcon() {
        return icon;
    }
}