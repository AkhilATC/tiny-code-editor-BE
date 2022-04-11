package com.tinyeditor.tinyCode;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiConsoleModule {
    @GetMapping("/")
    public String indexMsg(){
        return "Hello, user welcome to tinyCodeEditor";
    }

    @PostMapping("/run")
    public String compileCode(@RequestBody String body){

        return "ok";

    }
}
