package orted.imagepro.services;

import org.springframework.stereotype.Service;
import orted.imagepro.exceptions.InvalidFileTypeException;

@Service
public class ValidatorFileType {
    private static final String[] ACCEPTED_FILE_TYPES = {"image/jpeg", "image/png"};

    public static void validateFileType(String mimeType) throws InvalidFileTypeException {
        for(String x : ACCEPTED_FILE_TYPES) {
            if(x.equals(mimeType)) {
                return;
            }
        }
        throw new InvalidFileTypeException(String.format("FileType %s, not accepted", mimeType));
    }

}
