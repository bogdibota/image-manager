package imageManager.utils

import imageManager.ImageManagerPluginConstants

/**
 * Created by Bogdan Bota on 03/04/14.
 */
class ImageFilesManagementUtils {

    static void deleteTemporaryFiles(Long imageId) {
        File tmpDir = new File(ImageManagerPluginConstants.TEMPORARY_FILES_LOCATION)
        if (tmpDir.exists()) {
            tmpDir.listFiles(new FilenameFilter() {
                @Override
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
