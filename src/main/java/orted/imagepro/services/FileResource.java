package orted.imagepro.services;

import java.io.IOException;
import java.io.InputStream;

public class FileResource {
    private InputStream inputStream;
    private long contentLength;

    public FileResource() {
    }

    public FileResource(InputStream inputStream, long contentLength) {
        this.inputStream = inputStream;
        this.contentLength = contentLength;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }


    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }

    public InputStream getInputStream() throws IOException {
        return inputStream;
    }

    public long getContentLength() throws IOException {
        return contentLength;
    }
}
