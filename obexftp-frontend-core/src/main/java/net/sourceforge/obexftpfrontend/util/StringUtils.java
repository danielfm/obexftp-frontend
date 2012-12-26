package net.sourceforge.obexftpfrontend.util;

/**
 * String util methods.
 * @author Daniel F. Martins
 */
public class StringUtils {

    /**
     * Create a new instance of StringUtils.
     */
    private StringUtils() {
        super();
    }

    /**
     * Check if the file name matches with one of the file extensions inside
     * the extensions parameter.
     * @param fileName File name to check.
     * @param fileExtensions String that contains all file extensions
     * separated by spaces.
     * @return Whether the file name matches with one or more of the file
     * extensions.
     */
    public static boolean isFileExtensionMatch(String fileName, String fileExtensions) {
        if (fileExtensions == null || fileName == null) {
            return false;
        }

        String[] arr = fileExtensions.toUpperCase().split(" ");
        fileName = fileName.toUpperCase();

        for (String elem : arr) {
            if (!"".equals(elem) && fileName.endsWith(elem)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Get a String that contains a friendly representation of the element
     * size.
     * @return String that contains a friendly representation of the element
     * size.
     */
    public static String getCompactFileSize(long bytes) {
        String sizeMeasurement = " bytes";

        if (bytes >= 1024) {
            if (bytes < 1048576) {
                sizeMeasurement = " kb";
                bytes /= 1024;
            } else {
                sizeMeasurement = " mb";
                bytes /= 1048576;
            }
        }

        return bytes + sizeMeasurement;
    }
}