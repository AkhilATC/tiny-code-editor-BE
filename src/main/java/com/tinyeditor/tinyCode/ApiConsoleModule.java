package com.tinyeditor.tinyCode;

import com.code.preprossessing.codeExecutor;
import com.code.preprossessing.fileWrite;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static java.lang.Boolean.FALSE;

@RestController
public class ApiConsoleModule {
    @GetMapping("/")
    public String indexMsg() throws IOException {

        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        System.out.println("Current absolute path is: " + s);
        File file = new File("./tinyCode/demo1.txt");
        file.createNewFile();
        System.out.println(file.exists());
        return "Hello, user welcome to tinyCodeEditor, u are at now:".concat(s);
    }


    @PostMapping("/tiny_code/run")
    public ResponseEntity<?> compileCode(@RequestBody String body) throws IOException, ParseException {
        JSONParser jParser = new JSONParser();
        JSONObject payloadData = (JSONObject) jParser.parse(body);
        JSONObject response = new JSONObject();
        String codeString = payloadData.get("code").toString();
        JSONObject codeInfo = (JSONObject) payloadData.get("lang");
        try{

            // payload parse error
            if(codeInfo.isEmpty() || codeString == "" || codeString == null){
                response.put("status",FALSE);
                response.put("output","Invalid payload");
                return new ResponseEntity<>(
                        response
                        ,HttpStatus.BAD_REQUEST);
            }
            InputStream codeStringStream = new ByteArrayInputStream(codeString.getBytes());
            String fileName = codeInfo.get("file").toString();
            String flag = new fileWrite().fileWriteToLoc(codeStringStream,fileName);
            if(flag==null){
                response.put("status",FALSE);
                response.put("output","System error");
                return new ResponseEntity<>(
                        response
                        ,HttpStatus.BAD_REQUEST);

            }else{
                codeExecutor cExector = new codeExecutor(codeInfo.get("fileFormat").toString(),flag,fileName);
                Thread executionThread = new Thread(cExector);
                executionThread.start();
                executionThread.join();
                List<String> codeResponse = cExector.codeOutList;
                response.put("output",codeResponse);
                response.put("status",cExector.codeOutStatus);
                executionThread.stop();
            }


        }catch(Exception e){
            e.printStackTrace();
        }finally {
           new fileWrite().trashFileFromLoc(codeInfo.get("file").toString());
        }

        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
