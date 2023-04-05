package edu.nuist.controller;

import edu.nuist.entity.IDEInput;
import edu.nuist.entity.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;

@RestController
public class IDEController {

    @PostMapping("/IDE/OnlineIDESub")
    public Result onlineIdepost(@RequestBody IDEInput ideInput)
            throws InterruptedException, ExecutionException {
        Result result = new Result();
        int taskSize = 50;
        long startTime = new Date().getTime();

        ExecutorService pool = Executors.newFixedThreadPool(taskSize);
        List<Future> list = new ArrayList<>();

        for (int i = 0; i < taskSize; i++) {
            Callable c = new MyCallable(ideInput.getCodeInput());
            Future f = pool.submit(c);
            list.add(f);
        }

        pool.shutdown();
        long endTime = new Date().getTime();
        double wasteTime = (double) (endTime - startTime) / 1000;

        for (Future f : list) {
            // 从Future对象上获取任务的返回值，并输出到控制台
            if (f.get().toString().equals("error")) {
                result.setCode("500");
            } else {
                result.setCode("200");
                result.setWasteTime(wasteTime);
                result.setData(f.get().toString());
            }
        }

        return result;
    }

}

class MyCallable implements Callable<Object> {

    @Value("${platform.type}")
    private String platformType;

    private String codeInput;

    MyCallable(String codeInput) {
        this.codeInput = codeInput;
    }

    public Object call() throws Exception {
        String pythonV = "python";
        String[] codeString = codeInput.split("\n");
        String file_name = UUID.randomUUID().toString();
        //新建文件并在文件中存入信息
//        String cmdString = "cp /home/jupyterPage/template.ipynb /home/jupyterPage/"+sonUserExp.getSon_id()+sonUserExp.getUser_id()+".ipynb";
//        //Runtime.getRuntime().exec(new String[]{"/bin/sh","-c","ps -ef|grep java"});
//        Runtime.getRuntime().exec(cmdString);

        File file = new File("/home/pl/jupyter_files/" + file_name + ".py");
        file.createNewFile();
        PrintStream ps = new PrintStream(new FileOutputStream(file));

        for (String s : codeString) {
            ps.println(s);
        }

        Process proc;

        try {
            proc = Runtime.getRuntime().exec("python " + "/home/pl/jupyter_files/" + file_name + ".py");
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line;
            String resultString = "";

            while ((line = in.readLine()) != null) {
                resultString = resultString + line + "\n";
            }

            in.close();
            proc.waitFor();
            return resultString;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "error";
        }

//        if (platformType.equals("windows")){
//            // File file = new File("/home/jupyterPage/"+file_name+".py");
//            File file = new File("D:\\data\\"+file_name+".py");
//
//            file.createNewFile();
//            PrintStream ps = new PrintStream(new FileOutputStream(file));
//            for (String s : codeString) {
//                ps.println(s);
//            }
//
//            Process proc;
//            try {
//                //proc = Runtime.getRuntime().exec("python "+ "/home/jupyterPage/"+file_name+".py");
//                proc = Runtime.getRuntime().exec("python "+ "D:\\data\\"+file_name+".py");
//                BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
//                String line = null;
//                String resultString = "";
//                while ((line = in.readLine()) != null) {
//                    resultString = resultString + line+"\n";
//                }
//                System.out.println(resultString);
//                in.close();
//                proc.waitFor();
//
//                return resultString;
//            } catch (IOException e) {
//                e.printStackTrace();
//                return "error";
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//                return "error";
//            }
//        }else {
//             File file = new File("/home/pl/jupyter_files/"+file_name+".py");
//
//            file.createNewFile();
//            PrintStream ps = new PrintStream(new FileOutputStream(file));
//            for (String s : codeString) {
//                ps.println(s);
//            }
//
//            Process proc;
//            try {
//                proc = Runtime.getRuntime().exec("python "+ "/home/pl/jupyter_files/"+file_name+".py");
//                BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
//                String line = null;
//                String resultString = "";
//                while ((line = in.readLine()) != null) {
//                    resultString = resultString + line+"\n";
//                }
//                System.out.println(resultString);
//                in.close();
//                proc.waitFor();
//
//                return resultString;
//            } catch (IOException e) {
//                e.printStackTrace();
//                return "error";
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//                return "error";
//            }
//        }
    }
}