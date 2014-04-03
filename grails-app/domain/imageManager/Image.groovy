package imageManager

import imageManager.validation.ImageTypeValidator

class Image {

    String imageType
    LargeData imageData

    int imageWidth
    int imageHeight

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
