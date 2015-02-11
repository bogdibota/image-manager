package com.dvk.imageManager

import com.dvk.imageManager.Image

import java.awt.*

class ImageRenderingTagLib {

    static namespace = "dvkImage"

    def imageRetrievalService

    /**
     * Displays an image object.
     *
     * @attrs imageId REQUIRED id of the image
     * @attrs width width of the image in pixels
     * @attrs height height of the image in pixels
     * @attrs backgroundColor bgColor of the image !!only when both width and height are specified
     * @attrs scale scale of the image
     * @attrs class the class attribute of the img tag
     */
    def display = { attrs ->

        Long imageId = attrs.imageId as Long
        if (!imageId) {
            throwTagError("ImageId must be specified!")
        }

        Integer width = attrs.width as Integer
        Integer height = attrs.height as Integer
        String backgroundColor = attrs.backgroundColor
        Float scale = attrs.scale as Float

        out << """<img src="${
            createLink(controller: "imageRendering", action: "renderImage", params: [imageId: imageId, width: width, height: height, backgroundColor: backgroundColor, scale: scale])
        }" class=\"${attrs.class}\" />"""
    }

    /**
     * Returns tha path to the tmp image file
     *
     * @attrs imageId REQUIRED id of the image
     * @attrs width width of the image in pixels
     * @attrs height height of the image in pixels
     * @attrs backgroundColor bgColor of the image !!only when both width and height are specified
     * @attrs scale scale of the image
     * @attrs class the class attribute of the img tag
     */
    def getImageTempFile = { attrs ->
        Long imageId = attrs.imageId as Long
        Image image = Image.get(imageId)
        if (!imageId || !image) {
            throwTagError("Invalid image!")
        }

        Integer width = attrs.width as Integer
        Integer height = attrs.height as Integer
        String backgroundColor = attrs.backgroundColor
        Float scale = attrs.scale as Float

        String fileName
        if (width && height) {
            if (backgroundColor) {
                fileName = imageRetrievalService.getScaledImageFileName(image, width, height, Color.decode(backgroundColor))
            } else {
                fileName = imageRetrievalService.getScaledImageFileName(image, width, height)
            }
        } else if (width) {
            fileName = imageRetrievalService.getFixedWidthImageFileName(image, width)
        } else if (height) {
            fileName = imageRetrievalService.getFixedHeightImageFileName(image, height)
        } else if (scale) {
            fileName = imageRetrievalService.getScaledImageFileName(image, scale)
        } else {
            fileName = imageRetrievalService.getFullSizeImageFileName(image)
        }

        out << resource(dir: ImageManagerPluginConstants.TEMPORARY_FILES_RELATIVE_LOCATION, file: fileName)
    }
}
