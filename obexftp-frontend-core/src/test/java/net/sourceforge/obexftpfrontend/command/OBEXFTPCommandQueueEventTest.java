package net.sourceforge.obexftpfrontend.command;

import org.jmock.Mockery;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * OBEXFTPCommandQueueEvent test cases.
 * @author Daniel F. Martins
 */
public class OBEXFTPCommandQueueEventTest {

    private Mockery context = new Mockery();

    @Test
    public void testCreate() {
        OBEXFTPCommand command = context.mock(OBEXFTPCommand.class);
        OBEXFTPCommandLifecycleEvent evt = new OBEXFTPCommandLifecycleEvent(command, "Some result", OBEXFTPCommandLifecycleEvent.ExecutionStatus.ADDED);

        assertSame(evt.getCommand(), command);
        assertEquals("Some result", evt.getResult());
        assertEquals(OBEXFTPCommandLifecycleEvent.ExecutionStatus.ADDED, evt.getStatus());
    }
}