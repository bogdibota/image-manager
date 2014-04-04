package imageManager.rendering

class ImageRenderingTagLib {

    static namespace = "image"

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

        String style = ""
        if (width && height) {
            style = "style=\"width: ${width}px; height: ${height}px\""
        }

        out << """<img src="${createLink(controller: "imageRendering", action: "renderImage", params: [imageId: imageId, width: width, height: height, backgroundColor: backgroundColor, scale: scale])}" ${attrs.class} $style/>"""
    }
}
