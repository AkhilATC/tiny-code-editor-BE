package com.code.preprossessing;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;


public class fileWrite {
    //public String fileLoc = "/src/main/resources/";

    /**
     * InputFileStream writes to file loc
     * @param codeStrem
     * @param fileName
     * @return
     */
    public String fileWriteToLoc(InputStream codeStrem, String fileName){

        try{
            // concat local file loc with fileName
            Path source = Paths.get(this.getClass().getResource("/").getPath());
            String fileLoc = source.toAbsolutePath().toString();
            String localFile = fileLoc.concat(fileName);
            File codeFile = new File(localFile);
            codeFile.createNewFile();
            try (FileOutputStream outputStream = new FileOutputStream(codeFile, false)) {
                int read;
                byte[] bytes = new byte[8192];
                while ((read = codeStrem.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }
            }
            return localFile;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
    public void trashFileFromLoc(String fileName){
        Path source = Paths.get(this.getClass().getResource("/").getPath());
        String fileLoc = source.toAbsolutePath().toString();
        String localFile = fileLoc.concat(fileName);
        File isFile = new File(localFile);
        if(isFile.exists()){
            isFile.delete();
        }
    }
}
