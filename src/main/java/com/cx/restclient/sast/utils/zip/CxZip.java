package com.cx.restclient.sast.utils.zip;


import com.cx.restclient.dto.PathFilter;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;


public class CxZip {

    public static final Logger log = LoggerFactory.getLogger(CxZip.class);

    private static int numOfZippedFiles;


    public static void zipWorkspaceFolder(File baseDir, PathFilter filter, long maxZipSize, ByteArrayOutputStream byteArrayOutputStream) throws IOException {
        numOfZippedFiles = 0;
        log.info("Zipping workspace: '" + baseDir + "'");
        if (!isProjectDirectoryValid(baseDir.getAbsolutePath())) {
            return;
        }
        ZipListener zipListener = new ZipListener() {
            public void updateProgress(String fileName, long size) {
                numOfZippedFiles++;
                log.info("Zipping (" + FileUtils.byteCountToDisplaySize(size) + "): " + fileName);
            }
        };

        try {
            try {
                new Zipper().zip(baseDir, filter.getIncludes(), filter.getExcludes(), byteArrayOutputStream, maxZipSize, zipListener);
            } catch (Zipper.MaxZipSizeReached e) {
                throw new IOException("Reached maximum upload size limit of " + FileUtils.byteCountToDisplaySize(maxZipSize));
            } catch (Zipper.NoFilesToZip e) {
                throw new IOException("No files to zip");
            }

            log.info("Zipping complete with " + numOfZippedFiles + " files, total compressed size: " +
                    FileUtils.byteCountToDisplaySize(byteArrayOutputStream.size()));
        } catch (Exception e) {
            log.error("Error occurred during zipping source files. Error message: " + e.getMessage(), e);
        }
    }

    private static boolean isProjectDirectoryValid(String location) {
        File projectDir = new File(location);
        if (!projectDir.exists()) {
            log.error("Project directory [" + location + "] does not exist.");
            return false;
        }

        if (!projectDir.isDirectory()) {
            log.error("Project path [" + location + "] should point to a directory.");
            return false;
        }
        return true;
    }

}
