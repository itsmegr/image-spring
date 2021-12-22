package orted.imagepro;

import java.io.IOException;
import java.io.InputStream;

public class FileResourceImpl implements FileResource{
    private InputStream inputStream;
    private long contentLength;

    public FileResourceImpl() {
    }

    public FileResourceImpl(InputStream inputStream, long contentLength) {
        this.inputStream = inputStream;
        this.contentLength = contentLength;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }


    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return inputStream;
    }

    @Override
    public long getContentLength() throws IOException {
        return contentLength;
    }
}
