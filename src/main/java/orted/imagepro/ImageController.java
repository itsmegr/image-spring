package orted.imagepro;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import orted.imagepro.exceptions.InvalidFileTypeException;
import orted.imagepro.services.FileResource;
import orted.imagepro.services.FilesStorageService;
import orted.imagepro.services.ValidatorFileType;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;

/*
    Class that implements MultiPartFile interface is StandardMultipartFile
 */

@RestController
public class ImageController {

    private static final Logger logger=LoggerFactory.getLogger(ImageController.class);

    @Autowired
    FilesStorageService storageService;

    @PostMapping(value = "/", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    ResponseEntity<String> getData(@RequestPart(value = "postDesc") String name,
                                   @RequestPart(value = "file") MultipartFile file
                                   ) throws InvalidFileTypeException {

        //Check file type
        ValidatorFileType.validateFileType(file.getContentType());

        storageService.save(file);
        return ResponseEntity.ok("Data received");
    }

    @GetMapping("/download/{fileName}")
    public void getDownload(@PathVariable String fileName, HttpServletResponse
            response) {
        InputStream is = null;
        OutputStream os = null;

        try {
            FileResource resource  = storageService.download(fileName);
            is = resource.getInputStream();
            os  = response.getOutputStream();
            response.setContentLength((int)resource.getContentLength());
            response.setHeader("Content-Disposition", "attachment; filename = "
                    + fileName);

            is.transferTo(os);

        }catch (Exception e){
            e.printStackTrace();
            System.out.println();
        }

    }
}
