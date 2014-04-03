package imageManager.validation

import imageManager.exceptions.ImageTypeValidationException

/**
 * Created with IntelliJ IDEA.
 * User: Bogdan Bota
 * Date: 09/03/14
 * Time: 14:08
 * To change this template use File | Settings | File Templates.
 */
class ImageTypeValidator {
    public static final String[] allowedFormats = ['image/png', 'image/jpeg', 'image/gif']
    public static final String[] allowedExtensions = ['png', 'jpeg', 'gif']

    public static boolean isFormatAllowed(String format) {
        return allowedFormats.contains(format)
    }

    public static boolean isExtensionAllowed(String extension) {
        return allowedExtensions.contains(extension)
    }

    public static void validateFormat(String format) {
        if(!isFormatAllowed(format))
            throw new ImageTypeValidationException("Invalid format $format. Must be one of: ${allowedFormats.join(", ")}!")
    }

    public static void validateExtension(String extension) {
        if(!isExtensionAllowed(extension))
            throw new ImageTypeValidationException("Invalid extension $extension. Must be one of: ${allowedExtensions.join(", ")}!")
    }
}
