package net.sourceforge.obexftpfrontend.command;

import net.sourceforge.obexftpfrontend.obexftp.OBEXException;
import net.sourceforge.obexftpfrontend.obexftp.OBEXFTPException;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * DefaultOBEXFTPCommandConsumer test cases.
 * @author Daniel F. Martins
 */
public class DefaultOBEXFTPCommandConsumerTest {

    private Mockery context = new Mockery();

    @Test(expected = IllegalArgumentException.class)
    public void testCreateWithInvalidQueue() {
        new DefaultOBEXFTPCommandConsumer(null);
    }

    @Test
    public void testGetCurrent() throws InterruptedException {
        OBEXFTPCommandQueue queue = new DefaultOBEXFTPCommandQueue(1);
        DefaultOBEXFTPCommandConsumer consumer = new DefaultOBEXFTPCommandConsumer(queue);

        assertNull(consumer.getCurrent());

        new Thread(consumer).start();
        queue.append(new TestCommand());

        Thread.sleep(500);
        assertTrue(consumer.getCurrent() instanceof TestCommand);

        Thread.sleep(3500);
        assertNull(consumer.getCurrent());
    }

    @Test
    public void testIsWorking() throws InterruptedException {
        OBEXFTPCommandQueue queue = new DefaultOBEXFTPCommandQueue(1);
        DefaultOBEXFTPCommandConsumer consumer = new DefaultOBEXFTPCommandConsumer(queue);

        assertNull(consumer.getCurrent());

        new Thread(consumer).start();
        queue.append(new TestCommand());

        Thread.sleep(500);
        assertTrue(consumer.isWorking());

        Thread.sleep(3500);
        assertFalse(consumer.isWorking());
    }

    @Test
    public void testRunCommandWithErrors() throws InterruptedException {
        OBEXFTPCommandQueue queue = new DefaultOBEXFTPCommandQueue(1);
        DefaultOBEXFTPCommandConsumer consumer = new DefaultOBEXFTPCommandConsumer(queue);

        final OBEXFTPCommandQueueListener listener = context.mock(OBEXFTPCommandQueueListener.class);
        queue.addCommandExecutionListener(listener);

        final OBEXFTPCommand command = new TestCommand(false, true);

        context.checking(new Expectations() {

            {
                one(listener).commandAdded(with(aNonNull(OBEXFTPCommandLifecycleEvent.class)));
                one(listener).commandRemoved(with(aNonNull(OBEXFTPCommandLifecycleEvent.class)));
                one(listener).commandExecuted(with(aNonNull(OBEXFTPCommandLifecycleEvent.class)));
            }
        });

        new Thread(consumer).start();
        queue.append(command);
        Thread.sleep(4000);

        context.assertIsSatisfied();
    }

    @Test
    public void testRunCommandAndInterrupt() throws InterruptedException {
        OBEXFTPCommandQueue queue = new DefaultOBEXFTPCommandQueue(1);
        DefaultOBEXFTPCommandConsumer consumer = new DefaultOBEXFTPCommandConsumer(queue);

        final OBEXFTPCommandQueueListener listener = context.mock(OBEXFTPCommandQueueListener.class);
        queue.addCommandExecutionListener(listener);

        final OBEXFTPCommand command = new TestCommand(true, false);

        context.checking(new Expectations() {

            {
                one(listener).commandAdded(with(aNonNull(OBEXFTPCommandLifecycleEvent.class)));
                one(listener).commandRemoved(with(aNonNull(OBEXFTPCommandLifecycleEvent.class)));
                one(listener).commandExecuted(with(aNonNull(OBEXFTPCommandLifecycleEvent.class)));
            }
        });

        new Thread(consumer).start();
        queue.append(command);
        Thread.sleep(4000);

        context.assertIsSatisfied();
    }

    /**
     * A (slow) mock Command used to test some aspects of the consumer class.
     * @author Daniel F. Martins
     */
    private class TestCommand extends AbstractOBEXFTPCommand<Object> {

        private boolean timeout;
        private boolean error;

        /**
         * Create a new instance of TestCommand.
         */
        public TestCommand() {
            super(null, "My slow test command");
        }

        /**
         * Create a new instance of testCommand.
         * @param timeout Whether this command should throw an InterruptedException
         * exception. This attribute cannot be true if the 'error' attribute is true.
         * @param error Whether this command should throws any exception. This
         * attribute cannot be true if the 'timeout' attribute is true.
         */
        public TestCommand(boolean timeout, boolean error) {
            this();
            this.timeout = timeout;
            this.error = error;
        }

        @Override
        public Object execute() throws OBEXFTPException, OBEXException, InterruptedException {
            if (timeout) {
                throw new InterruptedException();
            }

            if (error) {
                throw new RuntimeException();
            }

            Thread.sleep(3000);
            return null;
        }
    }
}