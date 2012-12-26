package net.sourceforge.obexftpfrontend.model;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * OBEXDateFormatter test cases.
 * @author Daniel F. Martins
 */
public class OBEXDateFormatterTest {

    private OBEXDateFormatter format = new OBEXDateFormatter();

    @Test
    public void testParseNullDate() throws Exception {
        assertNull(format.parse(null));
    }

    @Test
    public void testParseEmptyDate() throws Exception {
        assertNull(format.parse(""));
    }

    @Test
    public void testParseInvalidDate() throws Exception {
        try {
            format.parse("20070101153000");
            fail();
        } catch (Exception exc) {
        }

        try {
            format.parse("20070101153000Z");
            fail();
        } catch (Exception exc) {
        }

        try {
            format.parse("2007T0101153000");
            fail();
        } catch (Exception exc) {
        }

        try {
            format.parse("070101T153000");
            fail();
        } catch (Exception exc) {
        }
    }

    @Test
    public void testParseValidDate() throws Exception {
        Date dateUTC = null;
        Date date = null;

        dateUTC = format.parse("20070101T153000Z");
        date = format.parse("20070101T153000");

        assertFalse(dateUTC.equals(date));

        Calendar calUTC = null;
        Calendar cal = null;

        calUTC = Calendar.getInstance();
        calUTC.setTimeZone(TimeZone.getTimeZone("UTC"));
        calUTC.setTime(dateUTC);

        cal = Calendar.getInstance();
        cal.setTime(date);

        assertEquals(cal.get(Calendar.YEAR), calUTC.get(Calendar.YEAR));
        assertEquals(cal.get(Calendar.MONTH), calUTC.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.DATE), calUTC.get(Calendar.DATE));
    }
}
