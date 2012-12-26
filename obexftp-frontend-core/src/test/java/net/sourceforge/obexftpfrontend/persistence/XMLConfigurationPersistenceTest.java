package net.sourceforge.obexftpfrontend.persistence;

import net.sourceforge.obexftpfrontend.model.Configuration;
import net.sourceforge.obexftpfrontend.model.FileCategory;
import net.sourceforge.obexftpfrontend.model.FileMapping;
import net.sourceforge.obexftpfrontend.model.TransportType;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * XMLConfigurationPersistence test cases.
 * @author Daniel F. Martins
 */
public class XMLConfigurationPersistenceTest {

    private ConfigurationPersistence persistence;

    @Before
    public void setUp() {
        persistence = new XMLConfigurationPersistence();
    }

    @Test
    public void testSaveConfiguration() {
        Configuration config = new Configuration();

        config.addFileMapping(new FileMapping(FileCategory.APPLICATION, "app", ".app"));
        config.setObexftpLocation("c");
        config.setObexftpTimeout(1);
        config.setTransport(TransportType.TTY);
        config.setConnectionLine("f");

        persistence.saveConfiguration(config);
        assertTrue(persistence.configurationExists());
    }

    @Test
    public void testLoadConfiguration() {
        Configuration config = persistence.loadConfiguration();

        assertEquals(FileCategory.APPLICATION, config.getFileMapping().get(0).getCategory());
        assertEquals("c", config.getObexftpLocation());
        assertEquals(1, config.getObexftpTimeout());
        assertEquals(TransportType.TTY, config.getTransport());
        assertEquals("f", config.getConnectionLine());

        assertTrue(DefaultMetaConfigurationReader.getInstance().getConfigFile().delete());
    }
}
