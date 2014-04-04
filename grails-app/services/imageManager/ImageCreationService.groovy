package imageManager

import imageManager.exceptions.ImageCreationException
import imageManager.utils.ImageManipulationUtils
import imageManager.validation.ImageTypeValidator
import org.springframework.web.multipart.commons.CommonsMultipartFile

import java.awt.image.BufferedImage

class ImageCreationService {

    static transactional = false

    Image createImage(BufferedImage bufferedImage, String imageExtension) {
        ImageTypeValidator.validateExtension(imageExtension)
        def data = ImageManipulationUtils.bufferedImageToBytes(bufferedImage, imageExtension)
        new Image(imageData: new LargeData(data: data),
                imageType: "image/$imageExtension",
                imageWidth: bufferedImage.width,
                imageHeight: bufferedImage.height)
    }

    Image createImage(byte[] data, String imageFormat) {
        ImageTypeValidator.validateFormat(imageFormat)
        def img = ImageManipulationUtils.bytesToBufferedImage(data)
        new Image(imageData: new LargeData(data: data),
                imageType: imageFormat,
                imageWidth: img.width,
                imageHeight: img.height)
    }

    Image createImage(CommonsMultipartFile file) {
        if (!file || file.isEmpty()) {
            throw new ImageCreationException("Invalid file: $file")
        }
        createImage(file.bytes, file.contentType)
    }
}
