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

import java.io.ByteArrayInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static java.lang.Boolean.FALSE;

@RestController
public class ApiConsoleModule {
    @GetMapping("/")
    public String indexMsg(){
        return "Hello, user welcome to tinyCodeEditor";
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
                response.put("message","Invalid payload");
                return new ResponseEntity<>(
                        response
                        ,HttpStatus.BAD_REQUEST);
            }
            InputStream codeStringStream = new ByteArrayInputStream(codeString.getBytes());
            String flag = new fileWrite().fileWriteToLoc(codeStringStream,codeInfo.get("file").toString());
            if(flag==null){
                response.put("status",FALSE);
                response.put("message","System error");
                return new ResponseEntity<>(
                        response
                        ,HttpStatus.BAD_REQUEST);

            }else{
                codeExecutor cExector = new codeExecutor(codeInfo.get("fileFormat").toString(),flag);
                Thread executionThread = new Thread(cExector);
                executionThread.start();
                executionThread.join();
                List<String> codeResponse = cExector.codeOutList;
                response.put("output",codeResponse);
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
