package net.sourceforge.obexftpfrontend.gui;

/**
 * This interface represents the expected behavior of all components that
 * must enable and disable application's menu items.
 * @author Daniel F. Martins
 */
public interface ContextMenuHandler {

    /**
     * Enable the 'get selected files' menu item.
     * @param b Whether to enable or disable the corresponding menu.
     */
    void enableGetSelectedFilesMenuItem(boolean b);

    /**
     * Enable the 'remove selected files' menu item.
     * @param b Whether to enable or disable the corresponding menu.
     */
    void enableRemoveSelectedFilesMenuItem(boolean b);

    /**
     * Enable the 'refresh folder' menu item.
     * @param b Whether to enable or disable the corresponding menu.
     */
    void enableRefreshFolderMenuItem(boolean b);

    /**
     * Enable the 'create folder' menu item.
     * @param b Whether to enable or disable the corresponding menu.
     */
    void enableCreateFolderMenuItems(boolean b);

    /**
     * Enable the 'send files' menu item.
     * @param b Whether to enable or disable the corresponding menu.
     */
    void enableSendFilesMenuItem(boolean b);

    /**
     * Enable the 'file properties' menu item.
     * @param b Whether to enable or disable the corresponding menu.
     */
    void enableFilePropertiesMenuItem(boolean b);
}