package com.dvk.imageManager.utils

import com.dvk.imageManager.ImageManagerPluginConstants

/**
 * @author Bogdan Bota
 */
class ImageFilesManagementUtils {

    static void deleteTemporaryFiles(Long imageId) {
        File tmpDir = new File(ImageManagerPluginConstants.TEMPORARY_FILES_LOCATION)
        if (tmpDir.exists()) {
            tmpDir.listFiles(new FilenameFilter() {
                boolean accept(File dir, String name) {
                    name.startsWith(imageId.toString() + "_")
                }
            }).each { it.delete() }
        }
    }

    static void deleteAllTemporaryFiles() {
        File tmpDir = new File(ImageManagerPluginConstants.TEMPORARY_FILES_LOCATION)
        if (tmpDir.exists()) {
            tmpDir.listFiles().each { it.delete() }
        }
    }
}
