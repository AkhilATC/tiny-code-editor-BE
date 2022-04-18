package com.code.preprossessing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class codeExecutor implements Runnable{

    public List<String> codeOutList = new ArrayList<String>();
    public String lang;
    public boolean codeOutStatus = true;
    public String fileLoc;
    public codeExecutor(String lang,String fileLoc){
        this.lang = lang;
        this.fileLoc = fileLoc;

    }

    @Override
    public void run() {
        if(this.fileLoc == null || this.lang == null){
            this.codeOutStatus = false;
            this.codeOutList.add("Failed execution");
        }
        if(this.lang == "python"){
            try {
                StringBuilder sb = new StringBuilder();
                sb.append("python3");
                sb.append(" ");
                sb.append(this.fileLoc);
                System.out.println(sb);
                Process p = Runtime.getRuntime().exec(sb.toString());
                InputStream inputStrem = p.getInputStream();
                BufferedReader bfReader =  new BufferedReader(new InputStreamReader(inputStrem));
                String line;
                while((line= bfReader.readLine())!=null){
                    this.codeOutList.add(line);
                }

            } catch (IOException e) {
                this.codeOutList.add("failed");
                this.codeOutStatus = false;
                throw new RuntimeException(e);
            }
        }else{
            System.out.println("dddd");
        }

    }
}
