package edu.nuist.controller;

import edu.nuist.vo.BasicResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;

@Slf4j
@RestController
@RequestMapping("/IDE")
public class IDEController {

    @GetMapping("/OnlineIDESub")
    public BasicResultVO<Object> onlineIDE(String codeInput)
            throws InterruptedException, ExecutionException {
        BasicResultVO<Object> res = null;
        int taskSize = 50;
        long startTime = new Date().getTime();

        ExecutorService pool = Executors.newFixedThreadPool(taskSize);
        List<Future> list = new ArrayList<>();

        for (int i = 0; i < taskSize; i++) {
            Callable c = new MyCallable(codeInput);
            Future f = pool.submit(c);
            list.add(f);
        }

        pool.shutdown();
        long endTime = new Date().getTime();
        double wasteTime = (double) (endTime - startTime) / 1000;

        for (Future f : list) {
            // 从Future对象上获取任务的返回值，并输出到控制台
            if (f.get().toString().equals("error")) {
                res = BasicResultVO.fail();
            } else {
                res = BasicResultVO.success(f.get().toString());
            }
        }

        return res;
    }

}

class MyCallable implements Callable<Object> {

    private final String codeInput;

    MyCallable(String codeInput) {
        this.codeInput = codeInput;
    }

    @Override
    public Object call() throws Exception {
        String file_name = UUID.randomUUID().toString();
        File file;
        Process proc;

        String platformType = "windows";

        if (platformType.equals("windows")) {
            file = new File("D:/data/" + file_name + ".py");
            boolean newFile = file.createNewFile();

            try {
                proc = Runtime.getRuntime().exec("python " + "D:/data/" + file_name + ".py");
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
