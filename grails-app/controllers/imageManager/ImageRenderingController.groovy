package imageManager

import imageManager.exceptions.ImageRenderingException
import imageManager.utils.ImageManipulationUtils

import java.awt.Color
import java.awt.image.BufferedImage

class ImageRenderingController {
    def imageRetrievalService

    def index() { }

    def renderImage(Long imageId, Integer width, Integer height, String backgroundColor, Float scale) {
        Image image = Image.get(imageId)
        if(!image)
            throw new ImageRenderingException("Invalid image!")

        Color parsedBackgroundColor = backgroundColor ? Color.decode(backgroundColor) : null

        BufferedImage bufferedImage
        if(width && height)
            if(backgroundColor)
                bufferedImage = imageRetrievalService.getScaledImage(image, width, height, parsedBackgroundColor)
            else
                bufferedImage = imageRetrievalService.getScaledImage(image, width, height)
        else if(width)
            bufferedImage = imageRetrievalService.getFixedWidthImage(image, width)
        else if(height)
            bufferedImage = imageRetrievalService.getFixedHeightImage(image, height)
        else if(scale)
            bufferedImage = imageRetrievalService.getScaledImage(image, scale)
        else
            bufferedImage = imageRetrievalService.getFullSizeImage(image)

        response.setContentType(image.imageType)
        byte[] bufferedImageToBytes = ImageManipulationUtils.bufferedImageToBytes(bufferedImage, image.ext)
        response.setContentLength(bufferedImageToBytes.size())
        OutputStream out = response.getOutputStream();
        out.write(bufferedImageToBytes);
        out.close();
    }
}
