package imageManager

class ImageFilesManagementService {

    static transactional = false

    def deleteTemporaryFiles(Long imageId) {
        File tmpDir = new File(ImageManagerPluginConstants.TEMPORARY_FILES_LOCATION)
        if (tmpDir.exists()) {
            tmpDir.listFiles(new FilenameFilter() {
                @Override
                boolean accept(File dir, String name) {
                    name.startsWith(imageId.toString()+"_")
                }
            }).each {it.delete()}
        }
    }

    def deleteAllTemporaryFiles() {
        File tmpDir = new File(ImageManagerPluginConstants.TEMPORARY_FILES_LOCATION)
        if (tmpDir.exists()) {
            tmpDir.listFiles().each {it.delete()}
        }
    }
}
