package com.dvk.imageManager

import com.dvk.imageManager.Image
import com.dvk.imageManager.utils.ImageManipulationUtils

import javax.imageio.ImageIO
import java.awt.*
import java.awt.image.BufferedImage

import static java.awt.RenderingHints.KEY_INTERPOLATION
import static java.awt.RenderingHints.VALUE_INTERPOLATION_BICUBIC

class ImageRetrievalService {

    static transactional = false

    private static final Color BLACK0 = new Color(255, 255, 255, 0)

    String getFullSizeImageFileName(Image image) {
        findOrCreateFullSizeImage(image).name
    }

    BufferedImage getFullSizeImage(Image image) {
        ImageIO.read(findOrCreateFullSizeImage(image))
    }

    String getScaledImageFileName(Image image, int width, int height) {
        findOrCreateScaledImage(image, width, height).name
    }

    BufferedImage getScaledImage(Image image, int width, int height) {
        ImageIO.read(findOrCreateScaledImage(image, width, height))
    }

    String getScaledImageFileName(Image image, int width, int height, Color backgroundColor) {
        findOrCreateScaledImage(image, width, height, backgroundColor).name
    }

    BufferedImage getScaledImage(Image image, int width, int height, Color backgroundColor) {
        ImageIO.read(findOrCreateScaledImage(image, width, height, backgroundColor))
    }

    String getScaledImageFileName(Image image, float scale) {
        findOrCreateScaledImage(image, (float) image.imageWidth * scale, (float) image.imageHeight * scale).name
    }

    BufferedImage getScaledImage(Image image, float scale) {
        ImageIO.read(findOrCreateScaledImage(image, (float) image.imageWidth * scale, (float) image.imageHeight * scale))
    }

    String getFixedWidthImageFileName(Image image, int newWidth) {
        findOrCreateScaledImage(image, newWidth, (float) newWidth * image.imageHeight / image.imageWidth).name
    }

    BufferedImage getFixedWidthImage(Image image, int newWidth) {
        ImageIO.read(findOrCreateScaledImage(image, newWidth, (float) newWidth * image.imageHeight / image.imageWidth))
    }

    String getFixedHeightImageFileName(Image image, int newHeight) {
        findOrCreateScaledImage(image, (float) newHeight * image.imageWidth / image.imageHeight, newHeight).name
    }

    BufferedImage getFixedHeightImage(Image image, int newHeight) {
        ImageIO.read(findOrCreateScaledImage(image, (float) newHeight * image.imageWidth / image.imageHeight, newHeight))
    }

    private static File findOrCreateFullSizeImage(Image image) {
        return findOrCreateScaledImage(image, image.imageHeight, image.imageWidth)
    }

    private
    static File findOrCreateScaledImage(Image image, float width, float height, Color backgroundColor = BLACK0) {
        String fileName = getAssociatedTemporaryFileName(image, (int) width, (int) height)

        File tmpFile = new File(ImageManagerPluginConstants.TEMPORARY_FILES_LOCATION + fileName)

        if (!tmpFile.exists()) {
            def innerSize = calculateNewSize(image.imageWidth, image.imageHeight, width, height)
            BufferedImage originalImage = ImageManipulationUtils.bytesToBufferedImage(image.imageData.data)
            BufferedImage newImage = resize(originalImage, [width: (int) width, height: (int) height], innerSize, backgroundColor)
            if (!tmpFile.getParentFile().exists()) {
                tmpFile.getParentFile().mkdirs()
            }
            ImageIO.write(newImage, image.getExt(), tmpFile)
        }

        return tmpFile
    }

    private static BufferedImage resize(BufferedImage original, outerSize, innerSize, Color color) {
        return new BufferedImage((int) outerSize.width, (int) outerSize.height, original.type).with { i ->
            createGraphics().with {
                setRenderingHint(KEY_INTERPOLATION, VALUE_INTERPOLATION_BICUBIC)
                if (outerSize.width == innerSize.width) {
                    int offset = (outerSize.height - innerSize.height) / 2
                    setColor color
                    fillRect(0, 0, outerSize.width, offset)
                    drawImage(original, 0, offset, innerSize.width, innerSize.height, null)
                    fillRect(0, outerSize.height - offset, outerSize.width, offset)
                } else {
                    int offset = (outerSize.width - innerSize.width) / 2
                    setColor color
                    fillRect(0, 0, offset, outerSize.height)
                    drawImage(original, offset, 0, innerSize.width, innerSize.height, null)
                    fillRect(outerSize.width - offset, 0, offset, outerSize.height)
                }
                dispose()
            }
            return i
        }
    }

    private static String getAssociatedTemporaryFileName(Image image, int width, int height) {
        "${image.id}_${image.cachedHashCode}_${width}-${height}.${image.imageType.split("/")[1]}"
    }

    private static calculateNewSize(int originalWidth, int originalHeight, float newWidth, float newHeight) {
        float ration = (float) originalWidth / originalHeight

        if (newHeight * ration <= newWidth) {
            return [width: (int) (newHeight * ration), height: (int) newHeight]
        }

        return [width: (int) newWidth, height: (int) (newWidth / ration)]
    }
}
