package com.dvk.imageManager

import com.dvk.imageManager.utils.ImageFilesManagementUtils
import com.dvk.imageManager.validation.ImageTypeValidator

class Image {

    String imageType
    LargeData imageData

    int imageWidth
    int imageHeight

    int cachedHashCode = 0

    boolean equals(o) {
        if (this.is(o)) return true
        if (!(o instanceof Image)) return false

        Image image = (Image) o

        if (imageHeight != image.imageHeight) return false
        if (imageWidth != image.imageWidth) return false
        if (id != image.id) return false
        if (imageData != image.imageData) return false
        if (imageType != image.imageType) return false

        return true
    }

    int hashCode() {
        int result
        result = imageType.hashCode()
        result = 31 * result + imageData.hashCode()
        result = 31 * result + imageWidth
        result = 31 * result + imageHeight
        result = 31 * result + id.hashCode()
        return result
    }

    def beforeDelete() {
        ImageFilesManagementUtils.deleteTemporaryFiles(id)
    }

    def beforeInsert() {
        cachedHashCode = imageType.hashCode()
        cachedHashCode = 31 * cachedHashCode + imageData.hashCode()
        cachedHashCode = 31 * cachedHashCode + imageWidth
        cachedHashCode = 31 * cachedHashCode + imageHeight
    }

    String getExt() {
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
