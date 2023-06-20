package edu.nuist.ide;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

class MyCallable implements Callable<Object> {

    private final String codeInput;

    private final String fileDirectory;

    MyCallable(String codeInput, String fileDirectory) {
        this.codeInput = codeInput;
        this.fileDirectory = fileDirectory;
    }

    @Override
    public String call() throws Exception {
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

            in.close();
            proc.waitFor();
            return resultString.toString();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "error";
        }
    }

}

public class IDETest {

    @Test
    void ideTest() throws Exception {
        String code = "for i in [2,3]:\n" +
                "    print(i)";
        System.out.println(code);
        MyCallable myCallable = new MyCallable(code, "D:/Projects/ActualProjects/experiment-ai/file/");
        String call = myCallable.call();
        System.out.println(call);
    }

    @Test
    void t() {
        String str = "hello world";
        System.out.println(str);
        String jsonString = JSON.toJSONString(str);
        System.out.println(jsonString);
        System.out.println(jsonString.replace("\"", ""));
    }

}
