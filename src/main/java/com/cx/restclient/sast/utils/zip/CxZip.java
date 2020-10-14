package com.cx.restclient.sast.utils.zip;


import com.cx.restclient.dto.PathFilter;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;


public class CxZip {

    private static final long defaultMaxZipSize = 209715200L;
    private static int numOfZippedFiles;

    public static byte[] zipWorkspaceFolder(File baseDir, PathFilter filter, Long maxZipSizeInBytes, Logger log) throws IOException {
        log.info("Zipping workspace: '" + baseDir + "'");
        numOfZippedFiles = 0;
        long maxZipSize = maxZipSizeInBytes != null ? maxZipSizeInBytes : defaultMaxZipSize;

        byte[] zipFileBA;
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            try {
                ZipListener zipListener = new ZipListener() {
                    public void updateProgress(String fileName, long size) {
                        numOfZippedFiles++;
                        log.info("Zipping (" + FileUtils.byteCountToDisplaySize(size) + "): " + fileName);
                    }
                };
                new Zipper(log).zip(baseDir, filter.getExcludes(), byteArrayOutputStream, maxZipSize, zipListener);
            } catch (Zipper.MaxZipSizeReached e) {
                throw new IOException("Reached maximum upload size limit of " + FileUtils.byteCountToDisplaySize(maxZipSize));
            } catch (Zipper.NoFilesToZip e) {
                throw new IOException("No files to zip");
            }

            log.info("Zipping complete with " + numOfZippedFiles + " files, total compressed size: " +
                    FileUtils.byteCountToDisplaySize(byteArrayOutputStream.size()));

            zipFileBA = byteArrayOutputStream.toByteArray();
        }
        return zipFileBA;
    }

}
