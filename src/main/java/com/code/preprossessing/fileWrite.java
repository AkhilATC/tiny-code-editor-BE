package com.code.preprossessing;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;


public class fileWrite {
    public boolean fileWriteToLoc(InputStream codeStrem, String lang){

        try{
            File codeFile = new File("CODEFILE.py");
            try (FileOutputStream outputStream = new FileOutputStream(codeFile, false)) {
                int read;
                byte[] bytes = new byte[8192];
                while ((read = codeStrem.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
