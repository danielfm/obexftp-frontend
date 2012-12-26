package net.sourceforge.obexftpfrontend.command;

/**
 * All classes that want to listen to the command queue events must implement
 * this interface.
 * @see net.sourceforge.obexftpfrontend.command.OBEXFTPCommandQueue
 * @author Daniel F. Martins
 */
public interface OBEXFTPCommandQueueListener {

    /**
     * This method is executed when the anyone adds a command to the queue.
     * @param event Event object.
     */
    void commandAdded(OBEXFTPCommandLifecycleEvent event);

    /**
     * This method is executed when the command consumer removes a command
     * from the queue.
     * @param event Event object.
     */
    void commandRemoved(OBEXFTPCommandLifecycleEvent event);

    /**
     * This method is executed when the command consumer finishes to execute
     * a command.
     * @param event Event object.
     */
    void commandExecuted(OBEXFTPCommandLifecycleEvent event);
}