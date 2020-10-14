package com.cx.restclient.sast.utils.zip;


import com.cx.restclient.configuration.CxScanConfig;
import com.cx.restclient.dto.PathFilter;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;

import static com.cx.restclient.sast.utils.SASTParam.MAX_ZIP_SIZE_BYTES;


/**
 * CxZipUtils generates the patterns used for zipping the workspace folder
 */
public abstract class CxZipUtils {

    public static byte[] getZippedSources(CxScanConfig config, PathFilter filter, String sourceDir, Logger log) throws IOException {
        byte[] zipFile = config.getZipFile() != null ? FileUtils.readFileToByteArray(config.getZipFile()) : null;
        if (zipFile == null) {
            log.info("Zipping sources");
            Long maxZipSize = config.getMaxZipSize() != null ? config.getMaxZipSize() * 1024 * 1024 : MAX_ZIP_SIZE_BYTES;

            zipFile = CxZip.zipWorkspaceFolder(new File(sourceDir), filter, maxZipSize, log);
            log.debug("The sources were zipped successfully");
        }
        return zipFile;
    }

}

