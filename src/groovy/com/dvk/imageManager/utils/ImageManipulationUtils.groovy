package com.dvk.imageManager.utils

import javax.imageio.ImageIO
import java.awt.image.BufferedImage

/**
 * @author Bogdan Bota
 */
class ImageManipulationUtils {

    static byte[] bufferedImageToBytes(BufferedImage bufferedImage, String ext) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        ImageIO.write(bufferedImage, ext, baos)
        return baos.toByteArray()
    }

    static BufferedImage bytesToBufferedImage(byte[] bytes) {
        InputStream inputStream = new ByteArrayInputStream(bytes)
        return ImageIO.read(inputStream)
    }
}
