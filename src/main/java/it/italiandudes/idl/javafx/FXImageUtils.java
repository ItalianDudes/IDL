package it.italiandudes.idl.javafx;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@SuppressWarnings("unused")
public final class FXImageUtils {

    // Methods
    @NotNull
    public static String fromFXImageToBase64(@NotNull final Image image, @NotNull final String imageExtension) throws IOException {
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, imageExtension, outputStream);
        return Base64.getEncoder().encodeToString(outputStream.toByteArray());
    }
    public static Image fromBase64ToFXImage(@NotNull final String base64image) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64.getDecoder().decode(base64image));
        BufferedImage image = ImageIO.read(inputStream);
        return SwingFXUtils.toFXImage(image, null);
    }
}
