package net.sourceforge.obexftpfrontend.command;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static net.sourceforge.obexftpfrontend.command.OBEXFTPCommandLifecycleEvent.ExecutionStatus.*;

/**
 * DefaultOBEXFTPCommandQueue test cases.
 * @author Daniel F. Martins
 */
public class DefaultOBEXFTPCommandQueueTest {

    private DefaultOBEXFTPCommandQueue queue;
    private Mockery context = new Mockery();

    @Before
    public void setUp() {
        queue = new DefaultOBEXFTPCommandQueue();
    }

    @Test
    public void testAppendNullCommand() throws InterruptedException {
        queue.append((OBEXFTPCommand) null);
        assertTrue(queue.isEmpty());
    }

    @Test
    public void testAppendValidCommand() throws InterruptedException {
        final OBEXFTPCommand command = context.mock(OBEXFTPCommand.class);
        final OBEXFTPCommandQueueListener listener = context.mock(OBEXFTPCommandQueueListener.class);

        context.checking(new Expectations() {

            {
                one(listener).commandAdded(with(aNonNull(OBEXFTPCommandLifecycleEvent.class)));
            }
        });

        queue.addCommandExecutionListener(listener);

        queue.append(command);
        assertFalse(queue.isEmpty());

        context.assertIsSatisfied();
    }

    @Test
    public void testPutAtHeadNullCommand() throws InterruptedException {
        final OBEXFTPCommand command = context.mock(OBEXFTPCommand.class);
        final OBEXFTPCommandQueueListener listener = context.mock(OBEXFTPCommandQueueListener.class);

        context.checking(new Expectations() {

            {
                one(listener).commandAdded(with(aNonNull(OBEXFTPCommandLifecycleEvent.class)));
                one(listener).commandRemoved(with(aNonNull(OBEXFTPCommandLifecycleEvent.class)));
            }
        });

        queue.addCommandExecutionListener(listener);

        queue.append(command);
        queue.putAtHead((OBEXFTPCommand) null);

        assertSame(command, queue.take());
        assertTrue(queue.isEmpty());

        context.assertIsSatisfied();
    }

    @Test
    public void testPutAtHeadValidCommand() throws InterruptedException {
        final OBEXFTPCommand command = context.mock(OBEXFTPCommand.class, "cmd1");
        final OBEXFTPCommand command2 = context.mock(OBEXFTPCommand.class, "cmd2");
        final OBEXFTPCommandQueueListener listener = context.mock(OBEXFTPCommandQueueListener.class);

        context.checking(new Expectations() {

            {
                exactly(2).of(listener).commandAdded(with(aNonNull(OBEXFTPCommandLifecycleEvent.class)));
                exactly(2).of(listener).commandRemoved(with(aNonNull(OBEXFTPCommandLifecycleEvent.class)));
            }
        });

        queue.addCommandExecutionListener(listener);

        queue.append(command);
        queue.putAtHead(command2);

        assertSame(command2, queue.take());
        assertSame(command, queue.take());

        context.assertIsSatisfied();
    }

    @Test
    public void testTakeValidCommand() throws InterruptedException {
        final OBEXFTPCommand command = context.mock(OBEXFTPCommand.class);
        final OBEXFTPCommandQueueListener listener = context.mock(OBEXFTPCommandQueueListener.class);

        context.checking(new Expectations() {

            {
                one(listener).commandAdded(with(aNonNull(OBEXFTPCommandLifecycleEvent.class)));
                one(listener).commandRemoved(with(aNonNull(OBEXFTPCommandLifecycleEvent.class)));
            }
        });

        queue.addCommandExecutionListener(listener);

        queue.append(command);
        assertFalse(queue.isEmpty());

        OBEXFTPCommand c = queue.take();
        assertNotNull(c);

        context.assertIsSatisfied();
    }

    @Test
    public void testAddNullCommandExecutionListener() {
        queue.addCommandExecutionListener(null);
        assertEquals(0, queue.getCommandExecutionListeners().size());
    }

    @Test
    public void testAddValidCommandExecutionListener() {
        final OBEXFTPCommandQueueListener listener = context.mock(OBEXFTPCommandQueueListener.class);

        queue.addCommandExecutionListener(listener);
        assertEquals(1, queue.getCommandExecutionListeners().size());
    }

    @Test
    public void testRemoveNullCommandExecutionListener() {
        final OBEXFTPCommandQueueListener listener = context.mock(OBEXFTPCommandQueueListener.class);

        queue.addCommandExecutionListener(listener);
        assertEquals(1, queue.getCommandExecutionListeners().size());

        queue.removeCommandExecutionListener(null);
        assertEquals(1, queue.getCommandExecutionListeners().size());
    }

    @Test
    public void testRemoveValidCommandExecutionListener() {
        final OBEXFTPCommandQueueListener listener = context.mock(OBEXFTPCommandQueueListener.class);

        queue.addCommandExecutionListener(listener);
        assertEquals(1, queue.getCommandExecutionListeners().size());

        queue.removeCommandExecutionListener(listener);
        assertEquals(0, queue.getCommandExecutionListeners().size());
    }

    @Test
    public void testNotifyAddedEventListeners() {
        final OBEXFTPCommandQueueListener listener = context.mock(OBEXFTPCommandQueueListener.class);
        final OBEXFTPCommand command = context.mock(OBEXFTPCommand.class);

        context.checking(new Expectations() {

            {
                one(listener).commandAdded(with(aNonNull(OBEXFTPCommandLifecycleEvent.class)));
            }
        });

        queue.addCommandExecutionListener(listener);
        queue.notifyListeners(new OBEXFTPCommandLifecycleEvent(command, 0, ADDED));

        context.assertIsSatisfied();
    }

    @Test
    public void testNotifyRemovedEventListeners() {
        final OBEXFTPCommandQueueListener listener = context.mock(OBEXFTPCommandQueueListener.class);
        final OBEXFTPCommand command = context.mock(OBEXFTPCommand.class);

        context.checking(new Expectations() {

            {
                one(listener).commandRemoved(with(aNonNull(OBEXFTPCommandLifecycleEvent.class)));
            }
        });

        queue.addCommandExecutionListener(listener);
        queue.notifyListeners(new OBEXFTPCommandLifecycleEvent(command, 0, REMOVED));

        context.assertIsSatisfied();
    }

    @Test
    public void testNotifySuccessEventListeners() {
        final OBEXFTPCommandQueueListener listener = context.mock(OBEXFTPCommandQueueListener.class);
        final OBEXFTPCommand command = context.mock(OBEXFTPCommand.class);

        context.checking(new Expectations() {

            {
                one(listener).commandExecuted(with(aNonNull(OBEXFTPCommandLifecycleEvent.class)));
            }
        });

        queue.addCommandExecutionListener(listener);
        queue.notifyListeners(new OBEXFTPCommandLifecycleEvent(command, 0, SUCCESS));

        context.assertIsSatisfied();
    }

    @Test
    public void testNotifyErrorEventListeners() {
        final OBEXFTPCommandQueueListener listener = context.mock(OBEXFTPCommandQueueListener.class);
        final OBEXFTPCommand command = context.mock(OBEXFTPCommand.class);

        context.checking(new Expectations() {

            {
                one(listener).commandExecuted(with(aNonNull(OBEXFTPCommandLifecycleEvent.class)));
            }
        });

        queue.addCommandExecutionListener(listener);
        queue.notifyListeners(new OBEXFTPCommandLifecycleEvent(command, 0, ERROR));

        context.assertIsSatisfied();
    }
}