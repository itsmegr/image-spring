package orted.imagepro.services;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.web.multipart.MultipartFile;

public interface FilesStorageService {
    public void init();

    public void save(MultipartFile file);

    public FileResource download(String path);

    public void deleteAll();

    public Stream<Path> loadAll();
}
