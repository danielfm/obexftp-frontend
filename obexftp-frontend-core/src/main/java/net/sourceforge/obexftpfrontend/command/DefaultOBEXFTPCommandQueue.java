package net.sourceforge.obexftpfrontend.command;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import org.apache.log4j.Logger;
import static net.sourceforge.obexftpfrontend.command.OBEXFTPCommandLifecycleEvent.ExecutionStatus.*;

/**
 * Default implementation of the OBEXFTPCommandQueue interface.
 * @author Daniel F. Martins
 */
public class DefaultOBEXFTPCommandQueue extends ArrayBlockingQueue<OBEXFTPCommand> implements OBEXFTPCommandQueue {

    /** Logger. */
    private static final Logger log = Logger.getLogger(DefaultOBEXFTPCommandQueue.class);
    /** List of registered listeners. */
    private List<OBEXFTPCommandQueueListener> listenerList;

    /**
     * Create a new instance of DefaultOBEXFTPCommandQueue that contains 50 slots.
     */
    public DefaultOBEXFTPCommandQueue() {
        this(50);
    }

    /**
     * Create a new instance of DefaultOBEXFTPCommandQueue.
     * @param capacity Queue capacity.
     */
    public DefaultOBEXFTPCommandQueue(int capacity) {
        super(capacity);
        listenerList = new LinkedList<OBEXFTPCommandQueueListener>();
    }

    @Override
    public void append(OBEXFTPCommand... commands) throws InterruptedException {
        if (commands != null) {
            for (OBEXFTPCommand command : commands) {
                if (command != null) {
                    log.debug("Trying to put a command at the queue's tail");
                    super.put(command);

                    log.debug("Command added to the queue");
                    notifyListeners(new OBEXFTPCommandLifecycleEvent(command, size(), ADDED));
                }
            }
        }
    }

    @Override
    public void putAtHead(OBEXFTPCommand... commands) throws InterruptedException {
        List<OBEXFTPCommand> tempList = new LinkedList<OBEXFTPCommand>();

        if (commands == null) {
            return;
        }
        log.debug("Trying to put a command at the queue's head");
        while (!isEmpty()) {
            tempList.add(super.take());
        }

        for (OBEXFTPCommand command : commands) {
            if (command != null) {
                super.put(command);
            }
        }
        for (OBEXFTPCommand c : tempList) {
            super.put(c);
        }

        for (OBEXFTPCommand command : commands) {
            if (command != null) {
                log.debug("Command added to the queue");
                notifyListeners(new OBEXFTPCommandLifecycleEvent(command, size(), ADDED));
            }
        }
    }

    @Override
    public OBEXFTPCommand take() throws InterruptedException {
        log.debug("Trying to take a command from the queue's head");
        OBEXFTPCommand command = super.take();

        log.debug("Command taken from the queue's head");
        notifyListeners(new OBEXFTPCommandLifecycleEvent(command, size(), REMOVED));

        return command;
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }

    @Override
    public void addCommandExecutionListener(OBEXFTPCommandQueueListener listener) {
        if (listener == null) {
            return;
        }

        log.debug("Adding a CommandExecutionListener");
        listenerList.add(listener);
    }

    @Override
    public void removeCommandExecutionListener(OBEXFTPCommandQueueListener listener) {
        if (listener == null) {
            return;
        }

        log.debug("Removing a CommandExecutionListener");
        listenerList.remove(listener);
    }

    @Override
    public void notifyListeners(OBEXFTPCommandLifecycleEvent event) {
        log.debug("Notifying the registered CommandExecutionListeners");

        for (OBEXFTPCommandQueueListener listener : listenerList) {
            switch (event.getStatus()) {
                case ADDED:
                    listener.commandAdded(event);
                    break;
                case REMOVED:
                    listener.commandRemoved(event);
                    break;
                case SUCCESS:
                case INTERRUPTED:
                case ERROR:
                    listener.commandExecuted(event);
                    break;
            }
        }
    }

    /**
     * Get the list of registered CommandExecutionListeners. Used for
     * testing only.
     * @return List of registered COmmandExecutionListeners.
     */
    protected List<OBEXFTPCommandQueueListener> getCommandExecutionListeners() {
        return listenerList;
    }
}