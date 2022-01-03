package orted.imagepro.services;

import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

@Service
public class ImgSclrResizer implements ImgResizer {
    private static final int IMAGE_WIDTH = 1080;

    private static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth) {
        int targetHeight = (int) (((double)1080/(double)originalImage.getWidth()) * (double)originalImage.getHeight());
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        graphics2D.dispose();
        return resizedImage;

//        return Scalr.resize(originalImage, Scalr.Method.QUALITY, targetWidth);
    }

    @Override
    public BufferedImage resize(InputStream is) throws IOException {
        BufferedImage originalImage = ImageIO.read(is);
        BufferedImage outputImage = resizeImage(originalImage, IMAGE_WIDTH);
        return outputImage;
    }
}
