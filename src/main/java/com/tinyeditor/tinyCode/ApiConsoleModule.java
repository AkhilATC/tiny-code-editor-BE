package com.tinyeditor.tinyCode;

import com.code.preprossessing.fileWrite;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

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

        try{
            String codeString = payloadData.get("code").toString();
            String codeLang = payloadData.get("lang").toString();
            InputStream codeStringStream = new ByteArrayInputStream(codeString.getBytes());
            boolean flag = new fileWrite().fileWriteToLoc(codeStringStream,"python");
            response.put("status", flag);
            String[] execute_cmds = new String[1];
            execute_cmds[0] = "python";
            Process p1 = Runtime.getRuntime().exec(execute_cmds);

        }catch(Exception e){
            e.printStackTrace();
        }

        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
