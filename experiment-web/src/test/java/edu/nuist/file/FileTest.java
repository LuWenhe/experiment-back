package edu.nuist.file;

import edu.nuist.util.FileUtils;
import org.junit.jupiter.api.Test;

public class FileTest {

    @Test
    void testDelete() {
        String filePath = "D:/lesson/深度学习雷雨大风分类与识别";
        FileUtils.deleteDirectoriesAndFiles(filePath);
    }

}
