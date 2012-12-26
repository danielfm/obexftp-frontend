package net.sourceforge.obexftpfrontend.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * DevInfo test cases.
 * @author Daniel F. Martins
 */
public class DevInfoTest {

    private DevInfo info;

    @Before
    public void setUp() {
        info = new DevInfo();
    }

    @Test
    public void testSetNullFirmwareVersion() {
        info.setFirmwareVersion(null);
        assertEquals("", info.getFirmwareVersion());
    }

    @Test
    public void testSetNullHardwareVersion() {
        info.setHardwareVersion(null);
        assertEquals("", info.getHardwareVersion());
    }

    @Test
    public void testSetNullManufacturer() {
        info.setManufacturer(null);
        assertEquals("", info.getManufacturer());
    }

    @Test
    public void testSetNullModel() {
        info.setModel(null);
        assertEquals("", info.getModel());
    }

    @Test
    public void testSetNullSerialNumber() {
        info.setSerialNumber(null);
        assertEquals("", info.getSerialNumber());
    }
}
