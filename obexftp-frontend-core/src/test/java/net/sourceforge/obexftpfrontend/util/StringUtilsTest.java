package net.sourceforge.obexftpfrontend.util;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * StringUtils test cases.
 * @author Daniel F. Martins
 */
public class StringUtilsTest {

    @Test
    public void testIsFileExtensionMatch() {
        String extensionList = ".mp3 .mid .wav";

        assertFalse(StringUtils.isFileExtensionMatch(null, null));
        assertFalse(StringUtils.isFileExtensionMatch("myfile.mp3", ""));
        assertFalse(StringUtils.isFileExtensionMatch("myfile.mp3", "   "));
        assertFalse(StringUtils.isFileExtensionMatch("myfile.mp3", null));
        assertFalse(StringUtils.isFileExtensionMatch(null, extensionList));
        assertFalse(StringUtils.isFileExtensionMatch("myfile.mp4", extensionList));
        assertFalse(StringUtils.isFileExtensionMatch("myfile.MP4", extensionList));
        assertFalse(StringUtils.isFileExtensionMatch("myfilemp3", extensionList));

        assertTrue(StringUtils.isFileExtensionMatch("somefile.mp3", extensionList));
        assertTrue(StringUtils.isFileExtensionMatch("somefile.MP3", extensionList));
    }

    @Test
    public void testGetCompactFileSize() {
        assertEquals("1023 bytes", StringUtils.getCompactFileSize(1023));
        assertEquals("1 kb", StringUtils.getCompactFileSize(1024));
        assertEquals("1 mb", StringUtils.getCompactFileSize(1024 * 1024));
        assertEquals("1024 mb", StringUtils.getCompactFileSize(1024 * 1024 * 1024));
    }
}
