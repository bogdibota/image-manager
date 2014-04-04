package imageManager

import static java.awt.RenderingHints.KEY_INTERPOLATION
import static java.awt.RenderingHints.VALUE_INTERPOLATION_BICUBIC

import imageManager.utils.ImageManipulationUtils

import java.awt.Color
import java.awt.image.BufferedImage

import javax.imageio.ImageIO

class ImageRetrievalService {

    static transactional = false

    private static final Color WHITE0 = new Color(255, 255, 255, 0)

    String getFullSizeImagePath(Image image) {
        findOrCreateFullSizeImage(image).path
    }

    BufferedImage getFullSizeImage(Image image) {
        ImageIO.read(findOrCreateFullSizeImage(image))
    }

    String getScaledImagePath(Image image, int width, int height) {
        findOrCreateScaledImage(image, width, height).path
    }

    BufferedImage getScaledImage(Image image, int width, int height) {
        ImageIO.read(findOrCreateScaledImage(image, width, height))
    }

    String getScaledImagePath(Image image, int width, int height, Color backgroundColor) {
        findOrCreateScaledImage(image, width, height, backgroundColor).path
    }

    BufferedImage getScaledImage(Image image, int width, int height, Color backgroundColor) {
        ImageIO.read(findOrCreateScaledImage(image, width, height, backgroundColor))
    }

    String getScaledImagePath(Image image, float scale) {
        findOrCreateScaledImage(image, (int) image.imageWidth * scale, (int) image.imageHeight * scale).path
    }

    BufferedImage getScaledImage(Image image, float scale) {
        ImageIO.read(findOrCreateScaledImage(image, (int) image.imageWidth * scale, (int) image.imageHeight * scale))
    }

    BufferedImage getScaledImage(Image image, float scale, Color backgroundColor) {
        ImageIO.read(findOrCreateScaledImage(image, (int) image.imageWidth * scale, (int) image.imageHeight * scale, backgroundColor))
    }

    String getFixedWidthImagePath(Image image, int newWidth) {
        findOrCreateScaledImage(image, newWidth, (int) newWidth * image.imageHeight / image.imageWidth).path
    }

    BufferedImage getFixedWidthImage(Image image, int newWidth) {
        ImageIO.read(findOrCreateScaledImage(image, newWidth, (int) newWidth * image.imageHeight / image.imageWidth))
    }

    BufferedImage getFixedWidthImage(Image image, int newWidth, Color backgroundColor) {
        ImageIO.read(findOrCreateScaledImage(image, newWidth, (int) newWidth * image.imageHeight / image.imageWidth, backgroundColor))
    }

    String getFixedHeightImagePath(Image image, int newHeight) {
        findOrCreateScaledImage(image, (int) newHeight * image.imageWidth / image.imageHeight, newHeight).path
    }

    BufferedImage getFixedHeightImage(Image image, int newHeight) {
        ImageIO.read(findOrCreateScaledImage(image, (int) newHeight * image.imageWidth / image.imageHeight, newHeight))
    }

    BufferedImage getFixedHeightImage(Image image, int newHeight, Color backgroundColor) {
        ImageIO.read(findOrCreateScaledImage(image, (int) newHeight * image.imageWidth / image.imageHeight, newHeight, backgroundColor))
    }

    private static File findOrCreateFullSizeImage(Image image) {
        return findOrCreateScaledImage(image, image.imageHeight, image.imageWidth)
    }

    private static File findOrCreateScaledImage(Image image, int width, int height, Color backgroundColor = WHITE0) {
        String fileName = getAssociatedTemporaryFileName(image, width, height)

        File tmpFile = new File(ImageManagerPluginConstants.TEMPORARY_FILES_LOCATION + fileName)

        if (!tmpFile.exists()) {
            def size = calculateNewSize(image.imageWidth, image.imageHeight, width, height)
            BufferedImage originalImage = ImageManipulationUtils.bytesToBufferedImage(image.imageData.data)
            BufferedImage newImage = resize(originalImage, [width: width, height: height], size, backgroundColor)
            if (!tmpFile.getParentFile().exists()) {
                tmpFile.getParentFile().mkdirs()
            }
            ImageIO.write(newImage, image.getExt(), tmpFile)
        }

        return tmpFile
    }

    private static BufferedImage resize(BufferedImage original, originalSize, newSize, Color color) {
        return new BufferedImage((int) originalSize.width, (int) originalSize.height, original.type).with { i ->
            createGraphics().with {
                setRenderingHint(KEY_INTERPOLATION, VALUE_INTERPOLATION_BICUBIC)
                if (originalSize.width == newSize.width) {
                    int offset = (originalSize.height - newSize.height) / 2
                    setColor color
                    fillRect(0, 0, originalSize.width, offset)
                    drawImage(original, 0, offset, newSize.width, newSize.height, null)
                    fillRect(0, originalSize.height - offset, originalSize.width, offset)
                } else {
                    int offset = (originalSize.width - newSize.width) / 2
                    setColor color
                    fillRect(0, 0, offset, originalSize.height)
                    drawImage(original, offset, 0, newSize.width, newSize.height, null)
                    fillRect(originalSize.width - offset, 0, offset, originalSize.height)
                }
                dispose()
            }
            return i
        }
    }

    private static String getAssociatedTemporaryFileName(Image image, int width, int height) {
        "${image.id}_${width}-${height}.${image.imageType.split("/")[1]}"
    }

    private static calculateNewSize(int oldWidth, int oldHeight, int newWidth, int newHeight) {
        if (oldHeight == newHeight && oldWidth == newWidth) {
            return [width: oldWidth, height: oldHeight]
        }
        float ration = oldWidth / oldHeight
        if (newHeight * ration <= newWidth) {
            return [width: (int) (newHeight * ration), height: newHeight]
        }

        return [width: newWidth, height: (int) (newWidth / ration)]
    }
}
