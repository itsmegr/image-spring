package orted.imagepro.services;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
@Primary
public class FileStorageServiceS3Impl implements FilesStorageService {

    private static final Logger logger= LoggerFactory.getLogger(FileStorageServiceS3Impl.class);

    @Autowired
    ImgResizer imgResizer;

    @Autowired
    public AmazonS3 s3;

    @Override
    public void save(MultipartFile img) throws IOException {
        /**/
        long startTime = System.currentTimeMillis();
        BufferedImage outputImage = imgResizer.resize(img.getInputStream());
        long endTime = System.currentTimeMillis();
        logger.info("Time for resizing the image --> {} ms", endTime-startTime);
        /**/

        /**/
        String imgNewName = ImgNamer.getNewName(img.getOriginalFilename());
        String imgType = ImgNamer.getOldFileType(img.getOriginalFilename());
        /**/

        /**/
        long startTime1 = System.currentTimeMillis();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(outputImage, imgType, os);

        byte[] buffer1 = os.toByteArray();
        InputStream is1 = new ByteArrayInputStream(buffer1);

        byte[] buffer2 = os.toByteArray();
        InputStream is2 = new ByteArrayInputStream(buffer2);

        long endTime1 = System.currentTimeMillis();
        logger.info("Time for internal data transfer --> {} ms", endTime1-startTime1);
        /**/


        /**/
        logger.info("data to put after compression --> {}kb", buffer1.length/1024);
        long startTime2 = System.currentTimeMillis();
        Files.copy(is1, Paths.get("uploads").resolve(imgNewName));
        long endTime2 = System.currentTimeMillis();
        logger.info("Time for uploading to localhost --> {} ms", endTime2-startTime2);
        /**/

        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentLength(buffer2.length);

        meta.setContentType("image/jpeg");

        /**/
        long startTime3 = System.currentTimeMillis();
        PutObjectResult data = s3.putObject("image-pro", imgNewName, is2, meta);
        long endTime3 = System.currentTimeMillis();
        logger.info("Time for uploading to s3 --> {} ms", endTime3-startTime3);
        /**/

    }
}
