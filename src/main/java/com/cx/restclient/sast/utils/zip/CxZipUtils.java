package com.cx.restclient.sast.utils.zip;


import com.cx.restclient.configuration.CxScanConfig;
import com.cx.restclient.dto.PathFilter;
import com.cx.restclient.exception.CxClientException;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import static com.cx.restclient.sast.utils.SASTParam.MAX_ZIP_SIZE_BYTES;


/**
 * CxZipUtils generates the patterns used for zipping the workspace folder
 */
public abstract class CxZipUtils {

    public static final Logger log = LoggerFactory.getLogger(CxZipUtils.class);

    public static byte[] getZippedSources(CxScanConfig config, PathFilter filter, String sourceDir) throws IOException {
        byte[] zipFile = config.getZipFile() != null ? FileUtils.readFileToByteArray(config.getZipFile()) : null;
        if (zipFile == null) {
            log.info("Zipping sources");
            Long maxZipSize = config.getMaxZipSize() != null ? config.getMaxZipSize() * 1024 * 1024 : MAX_ZIP_SIZE_BYTES;

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            CxZip.zipWorkspaceFolder(new File(sourceDir), filter, maxZipSize, byteArrayOutputStream);
            validateZippedSources(maxZipSize, byteArrayOutputStream);
            zipFile = byteArrayOutputStream.toByteArray();
        }
        return zipFile;
    }

    private static void validateZippedSources(long maxZipSize, ByteArrayOutputStream byteArrayOutputStream) throws CxClientException {
        // check packed sources size
        if (byteArrayOutputStream == null || byteArrayOutputStream.size() == 0) {
            // if size is greater that restricted value, stop scan
            log.error("Packing sources has failed: empty packed source ");
            throw new CxClientException("Packing sources has failed: empty packed source ");
        }

        if (byteArrayOutputStream.size() > maxZipSize) {
            // if size greater that restricted value, stop scan
            log.error("Packed project size is greater than " + maxZipSize);
            throw new CxClientException("Packed project size is greater than " + maxZipSize);
        }
    }

}

