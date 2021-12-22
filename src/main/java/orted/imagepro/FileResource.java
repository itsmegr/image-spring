package orted.imagepro;

import java.io.IOException;
import java.io.InputStream;

public interface FileResource {
    InputStream getInputStream() throws IOException;
    long getContentLength() throws IOException;
}
