package imageManager

import imageManager.validation.ImageTypeValidator

import javax.persistence.Transient

class Image {

    @Transient
    def imageFilesManagementService

    String imageType
    LargeData imageData

    int imageWidth
    int imageHeight

    def beforeDelete() {
        imageFilesManagementService.deleteTemporaryFiles(id)
    }

    public String getExt() {
        return imageType.split("/")[1]
    }

    static constraints = {
        imageType blank: false, validator: {return ImageTypeValidator.isFormatAllowed(it)}
        imageWidth min: 1
        imageHeight min: 1
    }

    static mapping = {
        imageData cascade: 'all'
        imageData fetch: 'lazy'
    }
}
