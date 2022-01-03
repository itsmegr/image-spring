package orted.imagepro.services;

import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

@Service
public interface ImgResizer {
    public BufferedImage resize(InputStream is) throws IOException;
}
