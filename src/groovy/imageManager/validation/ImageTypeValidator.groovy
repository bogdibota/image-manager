package imageManager.validation

import imageManager.exceptions.ImageTypeValidationException

/**
 * @author Bogdan Bota
 */
class ImageTypeValidator {
    public static final String[] allowedFormats = ['image/png', 'image/jpeg', 'image/gif']
    public static final String[] allowedExtensions = ['png', 'jpeg', 'gif']

    static boolean isFormatAllowed(String format) {
        return allowedFormats.contains(format)
    }

    static boolean isExtensionAllowed(String extension) {
        return allowedExtensions.contains(extension)
    }

    static void validateFormat(String format) {
        if (!isFormatAllowed(format)) {
            throw new ImageTypeValidationException("Invalid format $format. Must be one of: ${allowedFormats.join(", ")}!")
        }
    }

    static void validateExtension(String extension) {
        if (!isExtensionAllowed(extension)) {
            throw new ImageTypeValidationException("Invalid extension $extension. Must be one of: ${allowedExtensions.join(", ")}!")
        }
    }
}
