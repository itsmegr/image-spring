package orted.imagepro.services;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;

@Service
public class FilesStorageServiceImpl implements FilesStorageService {

    private final Path root = Paths.get("uploads");
    private static final Logger logger= LoggerFactory.getLogger(FilesStorageServiceImpl.class);

    @Autowired
    ImgResizer imgResizer;

    @Autowired
    public AmazonS3 s3;

    @Override
    public void init() {
        try {
            if( !Files.exists(root)){
                Files.createDirectory(root);
                logger.info("Initialized folder {}", root.toAbsolutePath());
            }
            else {
                logger.info("Path already exists, {}", root.toAbsolutePath());
            }

        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }
    private static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth) {
        return Scalr.resize(originalImage, Scalr.Method.QUALITY, targetWidth);
    }

    @Override
    public void save(MultipartFile img) {
        try {
            BufferedImage outputImage = imgResizer.resize(img.getInputStream());

            String imgNewName = ImgNamer.getNewName(img.getOriginalFilename());
            String imgType = ImgNamer.getOldFileType(img.getOriginalFilename());


            ByteArrayOutputStream os = new ByteArrayOutputStream();

            ImageIO.write(outputImage, imgType, os);

            byte[] buffer = os.toByteArray();

            InputStream is = new ByteArrayInputStream(buffer);

            ObjectMetadata meta = new ObjectMetadata();

            meta.setContentLength(buffer.length);

            meta.setContentType("image/jpeg");

            s3.putObject("image-pro", imgNewName, is, meta);

//            ImageIO.write(outputImage, imgType, os)
        }
        catch (FileAlreadyExistsException e ){
            logger.info("File {} already exists",  img.getOriginalFilename());
        }
        catch (Exception e) {
            logger.info("Could not store the img. Error: {}", e.getMessage());
        }
    }


    @Override
    public FileResource download(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                InputStream is =  resource.getInputStream();
                return new FileResource(is, resource.contentLength());
            }
            else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }


    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }
}
