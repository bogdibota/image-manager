package imageManager.utils

import javax.imageio.ImageIO
import java.awt.image.BufferedImage

/**
 * Created with IntelliJ IDEA.
 * User: Bogdan Bota
 * Date: 09/03/14
 * Time: 13:52
 * To change this template use File | Settings | File Templates.
 */
class ImageManipulationUtils {

    public static byte[] bufferedImageToBytes(BufferedImage bufferedImage, String ext) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, ext, baos);
        baos.flush();
        byte[] imageInByte = baos.toByteArray();
        baos.close();
        return imageInByte
    }

    public static BufferedImage bytesToBufferedImage(byte[] bytes) {
        InputStream inputStream = new ByteArrayInputStream(bytes);
        return ImageIO.read(inputStream)
    }
}
