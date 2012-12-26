package net.sourceforge.obexftpfrontend.gui.listener;

import net.sourceforge.obexftpfrontend.gui.*;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.swing.SwingWorker;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import net.sourceforge.obexftpfrontend.model.ConfigurationHolder;
import net.sourceforge.obexftpfrontend.model.OBEXElement;
import net.sourceforge.obexftpfrontend.obexftp.OBEXResponseParser;
import net.sourceforge.obexftpfrontend.command.OBEXFTPCommandLifecycleEvent;
import net.sourceforge.obexftpfrontend.command.OBEXFTPCommandQueueListener;
import net.sourceforge.obexftpfrontend.command.OBEXFTPCommandQueue;
import net.sourceforge.obexftpfrontend.command.ListFilesCommand;
import org.apache.log4j.Logger;
import static net.sourceforge.obexftpfrontend.command.OBEXFTPCommandLifecycleEvent.ExecutionStatus.*;

/**
 * This listener is responsible to manipulate the file system tree dynamically, rendering
 * the device's file system when it's nodes are expanded.
 * @author Daniel F. Martins
 */
public class FetchFileListFromFolderListener implements TreeExpansionListener, OBEXFTPCommandQueueListener {

    /** Logger. */
    private static final Logger log = Logger.getLogger(FetchFileListFromFolderListener.class);
    /** This Set stores all fetched folders. */
    private Set<OBEXElement> fetchedFolders;
    /** Object that manages the user's preferences. */
    private ConfigurationHolder configHolder;
    /** Object that manages the file system tree component. */
    private FileSystemTreeHolder treeHolder;
    /** Object that is able to parse OBEX responses. */
    private OBEXResponseParser parser;
    /** Command queue to be used. */
    private OBEXFTPCommandQueue queue;

    /**
     * Create a new instance of FetchFileListFromFolderListener.
     * @param configHolder Object that manages the user's preferences.
     * @param treeHolder Object that manages the file system tree component.
     * @param parser Object that is able to parse OBEX responses.
     * @param queue Command queue to be used.
     */
    public FetchFileListFromFolderListener(ConfigurationHolder configHolder, FileSystemTreeHolder treeHolder, OBEXResponseParser parser, OBEXFTPCommandQueue queue) {
        this.configHolder = configHolder;
        this.treeHolder = treeHolder;
        this.parser = parser;
        this.queue = queue;

        fetchedFolders = new LinkedHashSet<OBEXElement>();
    }

    @Override
    public void treeExpanded(final TreeExpansionEvent event) {
        OBEXElement folder = (OBEXElement) event.getPath().getLastPathComponent();
        log.debug("Node expanded");

        if (!fetchedFolders.contains(folder)) {

            try {
                queue.append(new ListFilesCommand(parser, configHolder, folder));
            } catch (Exception exc) {
                log.warn("The listener cannot be executed successfully due to some error in it's underlying commands", exc);
            }
        }
    }

    @Override
    public void treeCollapsed(TreeExpansionEvent event) {
        log.debug("Node collapsed");
    }

    @Override
    public void commandAdded(OBEXFTPCommandLifecycleEvent event) {
        log.debug("commandAdded() called");
    }

    @Override
    public void commandRemoved(OBEXFTPCommandLifecycleEvent event) {
        log.debug("commandRemoved() called");
    }

    @Override
    public void commandExecuted(final OBEXFTPCommandLifecycleEvent event) {
        log.debug("commandExecuted() called");

        if (!(event.getCommand() instanceof ListFilesCommand)) {
            return;
        }

        final ListFilesCommand command = (ListFilesCommand) event.getCommand();
        final OBEXElement selectedFolder = command.getFolder();

        if (event.getStatus() == ERROR) {
            treeHolder.collapseNode(selectedFolder);
        }

        if (event.getStatus() == SUCCESS) {

            new SwingWorker() {

                @Override
                public Object doInBackground() {

                    log.debug("Refreshing the node " + selectedFolder.getName());
                    OBEXElement element = (OBEXElement) event.getResult();
                    treeHolder.clearFetchedInfo(selectedFolder);

                    log.debug("Adding the retrieved nodes to the tree");
                    for (OBEXElement child : element.getChildren()) {
                        selectedFolder.add(child);
                    }

                    fetchedFolders.add(selectedFolder);
                    treeHolder.showNode(selectedFolder);

                    return null;
                }
            }.execute();
        }
    }

    /**
     * Get the list of folders that was already fetched.
     * @return List of folders that was already fetched.
     */
    public Set<OBEXElement> getFetchedFolders() {
        return fetchedFolders;
    }

    /**
     * Remove a node and its children from the already-fetched node list.
     * @param element Node to be removed.
     */
    public void removeFetchedFolder(OBEXElement element) {
        for (OBEXElement child : element.getChildren()) {
            removeFetchedFolder(child);
        }
        getFetchedFolders().remove(element);
    }
}