package orted.imagepro.services;

import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

@Service
public class ImgSclrResizer implements ImgResizer {
    private static final int IMAGE_WIDTH = 1080;

    private static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth) {
        return Scalr.resize(originalImage, Scalr.Method.QUALITY, targetWidth);
    }

    @Override
    public BufferedImage resize(InputStream is) throws IOException {
        BufferedImage originalImage = ImageIO.read(is);
        BufferedImage outputImage = resizeImage(originalImage, IMAGE_WIDTH);
        return outputImage;
    }
}
