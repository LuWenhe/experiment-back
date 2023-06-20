package edu.nuist.service;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class UtilTest {

    @Test
    void testString() {
        String str = "f061e4375eaf69366daf160d74dabbf7.mp4";
        String substring = str.substring(str.lastIndexOf("."));
        System.out.println(Arrays.asList(".mp4").contains(substring));
    }

    @Test
    void testCMD() {
        String filePath = "D:/Projects/ActualProjects/experiment-ai/file/jupyter/";
        String sourcePath = filePath + "template.ipynb";
        String destinationPath = filePath + "512.ipynb";
        try {
            FileUtils.copyFile(new File(sourcePath), new File(destinationPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testPython() throws IOException {
        String filePath = "D:\\Projects\\ActualProjects\\experiment-ai\\file\\python\\" + "hello.py";
        new File(filePath).createNewFile();
    }

}
