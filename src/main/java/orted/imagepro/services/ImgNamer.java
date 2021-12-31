package orted.imagepro.services;

import org.springframework.stereotype.Service;

@Service
public class ImgNamer {
    static String fileType = null;
    public static String getNewName(String oldName) {
        /*
            Escaping special character in regex by \ backslash
            So here 2 backslashes are there 1 for escaping itself only because \ is also special character
            and another for escaping.
         */
        String [] fileData = oldName.split("\\.", 2);
        String fileName = fileData[0];
        fileType = fileData[1];
        return String.format("%sCompressed.jpeg", fileName);
    }
    public static String getOldFileType(String oldName){
        if(fileType!=null) return  fileType;
        else return oldName.split("\\.", 2)[1];
    }
}
