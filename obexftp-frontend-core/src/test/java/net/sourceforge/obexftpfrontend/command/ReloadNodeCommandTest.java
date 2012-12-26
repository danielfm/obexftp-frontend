package net.sourceforge.obexftpfrontend.command;

import net.sourceforge.obexftpfrontend.gui.FileSystemTreeHolder;
import net.sourceforge.obexftpfrontend.model.ConfigurationHolder;
import net.sourceforge.obexftpfrontend.model.OBEXElement;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * ReloadNodeCommand test cases.
 * @author Daniel F. Martins
 */
public class ReloadNodeCommandTest {

    private Mockery context = new Mockery();

    @Test
    public void testCreate() {
        final FileSystemTreeHolder treeHolder = context.mock(FileSystemTreeHolder.class);
        final ConfigurationHolder configHolder = context.mock(ConfigurationHolder.class);

        context.checking(new Expectations() {

            {
                one(configHolder).getConfiguration();
            }
        });

        new ReloadNodeCommand(configHolder, new OBEXElement("/"), treeHolder);
        context.assertIsSatisfied();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateWithNullTreeHolder() {
        final ConfigurationHolder configHolder = context.mock(ConfigurationHolder.class);

        context.checking(new Expectations() {

            {
                one(configHolder).getConfiguration();
            }
        });

        new ReloadNodeCommand(configHolder, new OBEXElement("/"), null);
    }

    @Test
    public void testReloadNode() throws Exception {
        final FileSystemTreeHolder treeHolder = context.mock(FileSystemTreeHolder.class);
        final ConfigurationHolder configHolder = context.mock(ConfigurationHolder.class);

        final OBEXElement element = new OBEXElement("/");

        context.checking(new Expectations() {

            {
                one(configHolder).getConfiguration();
                one(treeHolder).reloadNodeInTree(element);
            }
        });

        ReloadNodeCommand command = new ReloadNodeCommand(configHolder, element, treeHolder);

        Object result = command.execute();
        assertNull(result);
        context.assertIsSatisfied();
    }

    @Test
    public void testReloadNullNode() throws Exception {
        final FileSystemTreeHolder treeHolder = context.mock(FileSystemTreeHolder.class);
        final ConfigurationHolder configHolder = context.mock(ConfigurationHolder.class);

        context.checking(new Expectations() {

            {
                one(configHolder).getConfiguration();
            }
        });

        ReloadNodeCommand command = new ReloadNodeCommand(configHolder, null, treeHolder);

        Object result = command.execute();
        assertNull(result);
        context.assertIsSatisfied();
    }
}