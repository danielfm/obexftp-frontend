package net.sourceforge.obexftpfrontend.command;

/**
 * When the user sends a request to 'obexftp', this request is translated to
 * a command object which is added to the command queue. The command consumer
 * object observes the command queue and executes every command added to this
 * queue in order.
 * @see net.sourceforge.obexftpfrontend.command.OBEXFTPCommandConsumer
 * @author Daniel F. Martins
 */
public interface OBEXFTPCommandQueue {

    /**
     * Whether the queue is empty.
     * @return Whether the queue is empty.
     */
    boolean isEmpty();

    /**
     * Register a OBEXFTPCommandQueueListener.
     * @param listener OBEXFTPCommandQueueListener object.
     */
    void addCommandExecutionListener(OBEXFTPCommandQueueListener listener);

    /**
     * Unregister the given OBEXFTPCommandQueueListener.
     * @param listener OBEXFTPCommandQueueListener to remove.
     */
    public void removeCommandExecutionListener(OBEXFTPCommandQueueListener listener);

    /**
     * Notify the registered listeners when a lifecycle event occurs.
     * @param event Event object to send to the listeners.
     */
    void notifyListeners(OBEXFTPCommandLifecycleEvent event);

    /**
     * Put an array of commands into the queue. If the queue is full, the
     * thread will be interrupted until a slot becomes available.
     * @param commands Commands to add to the queue.
     * @throws java.lang.InterruptedException if the thread is interrupted.
     */
    void append(OBEXFTPCommand... commands) throws InterruptedException;

    /**
     * Put an array of commands at the queue's head instead the tail. If the
     * queue is full, the thread will be interrupted until the slots becomes
     * available.
     * @param commands Command to add to the queue.
     * @throws InterruptedException if the thread is interrupted.
     */
    void putAtHead(OBEXFTPCommand... commands) throws InterruptedException;

    /**
     * Remove a command from this queue. If the queue is full, the thread will
     * be interrupted until a slot becomes available.
     * @return Command removed from the queue.
     * @throws InterruptedException if the thread is interrupted.
     */
    public OBEXFTPCommand take() throws InterruptedException;
}