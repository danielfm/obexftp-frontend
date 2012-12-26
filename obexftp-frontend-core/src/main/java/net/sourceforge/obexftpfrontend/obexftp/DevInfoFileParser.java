package net.sourceforge.obexftpfrontend.obexftp;

import net.sourceforge.obexftpfrontend.model.DevInfo;

/**
 * This interface defines all operations that a device info file parser must
 * be able to perform. The device info file is the IrMC Telecom devinfo.txt
 * file.
 * @author Daniel F. Martins
 */
public interface DevInfoFileParser {

    /**
     * Parse the device info file.
     * @return The object that represent the device info file.
     * @throws OBEXFTPException if some error occurs while reading or parsing
     * the given file.
     */
    DevInfo parseFile() throws OBEXFTPException;
}