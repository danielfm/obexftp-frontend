package net.sourceforge.obexftpfrontend.command;

import org.apache.log4j.Logger;
import static net.sourceforge.obexftpfrontend.command.OBEXFTPCommandLifecycleEvent.ExecutionStatus.*;

/**
 * This class is a default implementation of the OBEXFTPCommandConsumer
 * interface.
 * @author Daniel F. Martins
 */
public class DefaultOBEXFTPCommandConsumer implements OBEXFTPCommandConsumer {

    /** Logger. */
    private static final Logger log = Logger.getLogger(DefaultOBEXFTPCommandConsumer.class);
    /** Command being executed. */
    private OBEXFTPCommand current;
    /** Reference to the command queue. */
    private OBEXFTPCommandQueue queue;
    /* Whether this consumer is waiting for a command to execute. */
    private boolean working;

    /**
     * Create a new instance of DefaultOBEXFTPCommandConsumer.
     * @param queue Reference to the command queue.
     * @throws IllegalArgumentException if the argument is invalid.
     */
    public DefaultOBEXFTPCommandConsumer(OBEXFTPCommandQueue queue) {
        if (queue == null) {
            throw new IllegalArgumentException("The 'queue' argument cannot be null");
        }

        this.queue = queue;
    }

    /**
     * Get the OBEXFTPCommand being consumed. This method is used for testing
     * only.
     * @return OBEXFTPCommand being consumed.
     */
    protected OBEXFTPCommand getCurrent() {
        return current;
    }

    @Override
    public boolean isWorking() {
        return working;
    }

    @Override
    public void run() {
        while (true) {
            try {
                log.debug("Trying to get the next command from the queue");
                current = queue.take();
                working = true;

                log.debug("Executing the command");
                Object result = current.execute();

                Thread.sleep(1L);
                queue.notifyListeners(new OBEXFTPCommandLifecycleEvent(current, result, SUCCESS));
            } catch (InterruptedException exc) {
                log.warn("The command was interrupted", exc);
                queue.notifyListeners(new OBEXFTPCommandLifecycleEvent(current, exc, INTERRUPTED));
            } catch (Throwable t) {
                log.error("The command returned an error", t);
                queue.notifyListeners(new OBEXFTPCommandLifecycleEvent(current, t, ERROR));
            } finally {
                current = null;
                working = false;
            }
        }
    }
}