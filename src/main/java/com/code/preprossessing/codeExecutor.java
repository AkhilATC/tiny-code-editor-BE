package com.code.preprossessing;

import java.io.*;
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
    public void executeCode(StringBuilder sb){
        try {
            System.out.println(sb.toString());
            Process p = Runtime.getRuntime().exec(sb.toString());
            InputStream inputStrem = p.getInputStream();
            BufferedReader bfReader =  new BufferedReader(new InputStreamReader(inputStrem));
            String line;
            while((line= bfReader.readLine())!=null){
                this.codeOutList.add(line);
            }

            InputStream errStrem = p.getErrorStream();
            BufferedReader errorReader =  new BufferedReader(new InputStreamReader(errStrem));
            String each;
            while((each= errorReader.readLine())!=null){
                this.codeOutList.add(each);
            }



        } catch (IOException e) {
            this.codeOutList.add("failed");
            this.codeOutStatus = false;
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        if(this.fileLoc == null || this.lang == null){
            this.codeOutStatus = false;
            this.codeOutList.add("Failed execution");
        }

        if(this.lang.equalsIgnoreCase("python")){
            System.out.println("here im ");
            StringBuilder sb = new StringBuilder();
            sb.append("python3");
            sb.append(" ");
            sb.append(this.fileLoc);
            executeCode(sb);

        }else{
            System.out.println(this.lang);
            StringBuilder sb = new StringBuilder();
            sb.append("javac ");
            sb.append(this.fileLoc);
            executeCode(sb);
            //executeCode(new StringBuilder().append("java "+this.fileLoc.replace(".java","")));

        }

    }
}
