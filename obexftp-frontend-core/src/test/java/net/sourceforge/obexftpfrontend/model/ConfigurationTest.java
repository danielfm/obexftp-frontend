package net.sourceforge.obexftpfrontend.model;

import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Configuration test cases.
 * @author Daniel F. Martins
 */
public class ConfigurationTest {

    @Test
    public void testGetFileMapping() {
        Configuration config = new Configuration();
        config.addFileMapping(new FileMapping(FileCategory.APPLICATION, "app", ".app"));

        List<FileMapping> mapping = config.getFileMapping();

        try {
            mapping.add(new FileMapping());
            fail();
        } catch (Exception exc) {
        }
    }

    @Test
    public void testReplaceFileMapping() {
        Configuration config = new Configuration();
        config.addFileMapping(new FileMapping(FileCategory.APPLICATION, "app", ".app"));

        FileMapping old = config.getFileMapping().get(0);

        config.replaceFileMapping(config.getFileMapping());

        assertNotSame(old, config.getFileMapping());
    }

    @Test
    public void testCreateDefaultConfiguration() {
        Configuration config = Configuration.createDefaultConfiguration();

        assertNotNull(config);
        assertTrue(config.getFileMapping().size() > 0);
    }
}
