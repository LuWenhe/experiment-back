package edu.nuist.controller;

import edu.nuist.entity.IDEInput;
import edu.nuist.entity.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.nio.file.Files;
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

    private final String codeInput;

    MyCallable(String codeInput) {
        this.codeInput = codeInput;
    }

    @Override
    public Object call() throws Exception {
        String pythonV = "python";
        String[] codeString = codeInput.split("\n");
        String file_name = UUID.randomUUID().toString();
        File file;
        Process proc;

        if (platformType.equals("windows")) {
            file = new File("D:\\data\\" + file_name + ".py");
            boolean newFile = file.createNewFile();

            try {
                proc = Runtime.getRuntime().exec("python " + "D:\\data\\" + file_name + ".py");
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
        } else {
            file = new File("/home/pl/jupyter_files/" + file_name + ".py");
            boolean newFile = file.createNewFile();

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
        }
    }
}
