package net.sourceforge.obexftpfrontend.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * OBEXElement test cases.
 * @author Daniel F. Martins
 */
public class OBEXElementTest {

    private OBEXElement element;

    @Before
    public void setUp() {
        this.element = new OBEXElement("/");
    }

    @Test
    public void testSetNullName() {
        element.setName(null);
        assertEquals("", element.getName());
    }

    @Test
    public void testSetNotNullName() {
        element.setName("test");
        assertEquals("test", element.getName());
    }

    @Test
    public void testSetNullType() {
        element.setType(null);
        assertEquals(OBEXElement.OBEXElementType.FOLDER, element.getType());
    }

    @Test
    public void testSetValidType() {
        element.setType(OBEXElement.OBEXElementType.FILE);
        assertEquals(OBEXElement.OBEXElementType.FILE, element.getType());
    }

    @Test
    public void testSetNegativeSize() {
        element.setSize(-5);
        assertEquals(0L, element.getSize());
    }

    @Test
    public void testSetPositiveSize() {
        element.setSize(5);
        assertEquals(5L, element.getSize());
    }

    @Test
    public void testSetNullGroup() {
        element.setGroup(null);
        assertEquals("", element.getGroup());
    }

    @Test
    public void testSetValidGroup() {
        element.setGroup("group");
        assertEquals("group", element.getGroup());
    }

    @Test
    public void testSetNullOwner() {
        element.setOwner(null);
        assertEquals("", element.getOwner());
    }

    @Test
    public void testSetValidOwner() {
        element.setOwner("owner");
        assertEquals("owner", element.getOwner());
    }

    @Test
    public void testSetNullGroupPerm() {
        element.setGroupPerm(null);
        assertEquals("", element.getGroupPerm());
    }

    @Test
    public void testSetInvalidGroupPerm() {
        element.setGroupPerm("rrrrr");
        assertEquals("R", element.getGroupPerm());

        element.setGroupPerm("rrrw");
        assertEquals("R", element.getGroupPerm());

        element.setGroupPerm("rrw");
        assertEquals("RW", element.getGroupPerm());

        element.setGroupPerm("rww");
        assertEquals("RW", element.getGroupPerm());

        element.setGroupPerm("abc");
        assertEquals("", element.getGroupPerm());

        element.setGroupPerm("aw");
        assertEquals("W", element.getGroupPerm());

        element.setGroupPerm("awrd");
        assertEquals("WR", element.getGroupPerm());
    }

    @Test
    public void testSetValidGroupPerm() {
        element.setGroupPerm("r");
        assertEquals("R", element.getGroupPerm());

        element.setGroupPerm("rw");
        assertEquals("RW", element.getGroupPerm());

        element.setGroupPerm("wr");
        assertEquals("WR", element.getGroupPerm());

        element.setGroupPerm("rwd");
        assertEquals("RWD", element.getGroupPerm());

        element.setGroupPerm("rDw");
        assertEquals("RDW", element.getGroupPerm());
    }

    @Test
    public void testSetNullOwnerPerm() {
        element.setOwnerPerm(null);
        assertEquals("", element.getOwnerPerm());
    }

    @Test
    public void testSetValidOwnerPerm() {

        element.setOwnerPerm("rDw");
        assertEquals("RDW", element.getOwnerPerm());
    }

    @Test
    public void testSetNullOtherPerm() {
        element.setOtherPerm(null);
        assertEquals("", element.getOtherPerm());
    }

    @Test
    public void testSetValidOtherPerm() {
        element.setOtherPerm("rDw");
        assertEquals("RDW", element.getOtherPerm());
    }

    @Test
    public void testSetValidCreated() {
        element.setCreated("20070101T153000Z");
        assertNotNull(element.getCreated());
    }

    @Test
    public void testSetInvalidCreated() {
        element.setCreated("abc");
        assertNull(element.getCreated());
    }

    @Test
    public void testSetNullCreated() {
        element.setCreated("20070101T153000Z");
        assertNotNull(element.getCreated());

        element.setCreated(null);
        assertNull(element.getCreated());
    }

    @Test
    public void testSetValidModified() {
        element.setModified("20070101T153000Z");
        assertNotNull(element.getModified());
    }

    @Test
    public void testSetInvalidModified() {
        element.setModified("abc");
        assertNull(element.getModified());
    }

    @Test
    public void testSetNullModified() {
        element.setModified("20070101T153000Z");
        assertNotNull(element.getModified());

        element.setModified(null);
        assertNull(element.getModified());
    }

    @Test
    public void testSetValidAccessed() {
        element.setAccessed("20070101T153000Z");
        assertNotNull(element.getAccessed());
    }

    @Test
    public void testSetInvalidAccessed() {
        element.setAccessed("abc");
        assertNull(element.getAccessed());
    }

    @Test
    public void testSetNullAccessed() {
        element.setAccessed("20070101T153000Z");
        assertNotNull(element.getAccessed());

        element.setAccessed(null);
        assertNull(element.getAccessed());
    }

    @Test
    public void getStringPath() {
        OBEXElement subElement = new OBEXElement("test");
        element.add(subElement);

        assertArrayEquals(new String[]{"/"}, element.getStringPath());
        assertArrayEquals(new String[]{"/", "test"}, subElement.getStringPath());
    }

    @Test
    public void testGetLeafChildren() {
        assertEquals(0, element.getChildren().size());
    }

    @Test
    public void testGetNodeChildren() {
        OBEXElement subElement = new OBEXElement("test");
        element.add(subElement);

        assertEquals(1, element.getChildren().size());
    }
}
