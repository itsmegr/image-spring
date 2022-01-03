package orted.imagepro.services;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.web.multipart.MultipartFile;

public interface FilesStorageService {

    public void save(MultipartFile file) throws IOException;

//    public FileResource download(String path);
//
//    public void deleteAll();
//
//    public Stream<Path> loadAll();
}
