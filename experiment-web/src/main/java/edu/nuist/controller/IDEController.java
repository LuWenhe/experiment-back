package edu.nuist.controller;

import edu.nuist.vo.BasicResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

@Slf4j
@RestController
@RequestMapping("/IDE")
public class IDEController {

    @Value("${file.fileDirectory}")
    private String fileDirectory;

    @GetMapping("/OnlineIDESub")
    public BasicResultVO<Object> onlineIDE(String codeInput) {
        MyCallable myCallable = new MyCallable(codeInput, fileDirectory);

        try {
            Object call = myCallable.call();
            return BasicResultVO.success(call);
        } catch (Exception e) {
            return BasicResultVO.fail("运行失败");
        }
    }

}

class MyCallable implements Callable<Object> {

    private final String codeInput;

    private final String fileDirectory;

    MyCallable(String codeInput, String fileDirectory) {
        this.codeInput = codeInput;
        this.fileDirectory = fileDirectory;
    }

    @Override
    public Object call() throws Exception {
        Process proc;

        String filePath = fileDirectory + "python/test.py";
        File file = new File(filePath);
        FileUtils.writeStringToFile(file, codeInput, "UTF-8");

        try {
            proc = Runtime.getRuntime().exec("python " + filePath);
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line;
            StringBuilder resultString = new StringBuilder();

            while ((line = in.readLine()) != null) {
                resultString.append(line).append("\n");
            }

            System.out.println(resultString);

            in.close();
            proc.waitFor();
            return resultString.toString();
        } catch (IOException | InterruptedException e) {
            return "error";
        }
    }

}
