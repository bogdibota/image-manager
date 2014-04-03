package imageManager.rendering

class ImageRenderingTagLib {
    public static namespace = "image"

    /**
     * displays an image object
     * @attrs imageId REQUIRED id of the image
     * @attrs width width of the image in pixels
     * @attrs height height of the image in pixels
     * @attrs backgroundColor bgColor of the image !!only when both width and height are specified
     * @attrs scale scale of the image
     * @attrs class the class attribute of the img tag
     */
    def display = { attrs ->
        def style = ""

        def imageId = attrs.imageId as Long
        if (!imageId)
            throwTagError("ImageId must be specified!")
        def width = attrs.width as Integer
        def height = attrs.height as Integer
        String backgroundColor = attrs.backgroundColor as String
        def scale = attrs.scale as Float

        if (width && height)
            style = "style=\"width: ${width}px; height: ${height}px\""

        out << "<img src=\"${createLink(controller: "imageRendering", action: "renderImage", params: [imageId: imageId, width: width, height: height, backgroundColor: backgroundColor, scale: scale])}\" ${attrs.class} $style/>"
    }
}
