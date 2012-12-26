package net.sourceforge.obexftpfrontend.command;

/**
 * This is an object that holds information about a command's lifecycle.
 * @see net.sourceforge.obexftpfrontend.command.OBEXFTPCommandConsumer
 * @author Daniel F. Martins
 */
public class OBEXFTPCommandLifecycleEvent {

    /** Command related to the lifecycle event. */
    private OBEXFTPCommand command;
    /** Object returned by the command. */
    private Object result;
    /** Command's status. */
    private ExecutionStatus status;

    /**
     * Create a new instance of OBEXFTPCommandLifecycleEvent.
     * @param command Command related to this event.
     * @param result Object returned by the command.
     * @param status Command's status.
     */
    public OBEXFTPCommandLifecycleEvent(OBEXFTPCommand command, Object result, ExecutionStatus status) {
        this.command = command;
        this.result = result;
        this.status = status;
    }

    /**
     * Get the command object.
     * @return Command object.
     */
    public OBEXFTPCommand getCommand() {
        return command;
    }

    /**
     * Get the Object returned by the command.
     * @return Object returned by the command.
     */
    public Object getResult() {
        return result;
    }

    /**
     * Get the command's status.
     * @return Command's status.
     */
    public ExecutionStatus getStatus() {
        return status;
    }

    /**
     * Enumeration that specifies all posible events.
     * @author Daniel F. Martins
     */
    public enum ExecutionStatus {

        /** When a command was added to the queue */
        ADDED,
        /** When a command was removed from the queue */
        REMOVED,
        /** When a command processing was interrupted */
        INTERRUPTED,
        /** When a command executed with errors */
        ERROR,
        /** When a command executed successfully */
        SUCCESS
    }
}