package net.sourceforge.obexftpfrontend.command;

/**
 * This application uses an abstraction called 'command consumer' which is
 * an object that consumes command objects and handles them in a separate
 * thread.
 * @see net.sourceforge.obexftpfrontend.command.OBEXFTPCommandQueue
 * @author Daniel F. Martins
 */
public interface OBEXFTPCommandConsumer extends Runnable {

    /**
     * Whether this consumer is processing a command.
     * @return Whether the thread is processing a command.
     */
    boolean isWorking();
}