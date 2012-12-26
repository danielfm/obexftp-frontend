package net.sourceforge.obexftpfrontend.model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * FileMapping test cases.
 * @author Daniel F. Martins
 */
public class FileMappingTest {

    @Test
    public void testCreate() {
        FileMapping mapping = new FileMapping();

        assertNull(mapping.getCategory());
        assertNull(mapping.getExtensions());
        assertNull(mapping.getName());
    }

    @Test
    public void testCreateWithParameters() {
        FileMapping mapping = new FileMapping(FileCategory.APPLICATION, "Name", "extensions");

        assertEquals(FileCategory.APPLICATION, mapping.getCategory());
        assertEquals("Name", mapping.getName());
        assertEquals("extensions", mapping.getExtensions());
    }

    @Test
    public void testCopy() {
        FileMapping one = new FileMapping(FileCategory.APPLICATION, "Name", "extensions");
        FileMapping two = one.copy();

        assertNotSame(one, two);
    }
}
