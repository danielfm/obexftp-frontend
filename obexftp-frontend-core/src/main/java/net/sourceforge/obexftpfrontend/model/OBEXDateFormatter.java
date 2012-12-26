package net.sourceforge.obexftpfrontend.model;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * This date formatter is used to display dates in the application. It handles
 * properly the date format defined by the OBEX specification.
 * @author Daniel F. Martins
 */
public class OBEXDateFormatter extends SimpleDateFormat {

    /** ISO date formatter. */
    private DateFormat isoFormatter;
    /** UTC date formatter. */
    private DateFormat utcFormatter;

    /**
     * Create a new instance of OBEXDateFormatter.
     */
    public OBEXDateFormatter() {
        super();

        isoFormatter = new SimpleDateFormat("yyyyMMdd'T'HHmmss");

        utcFormatter = new SimpleDateFormat("yyyyMMdd'T'HHmmssZ");
        utcFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    @Override
    public Date parse(String strDate) throws ParseException {
        Format format = null;

        if (strDate == null || "".equals(strDate.trim())) {
            return null;
        }

        if (strDate.endsWith("Z")) {
            strDate = strDate.replaceAll("Z", "UTC");
            format = utcFormatter;
        } else {
            format = isoFormatter;
        }

        return (Date) format.parseObject(strDate);
    }
}
