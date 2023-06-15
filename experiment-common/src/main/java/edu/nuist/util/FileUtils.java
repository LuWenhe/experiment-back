package edu.nuist.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Slf4j
public class FileUtils {

    public static String uploadFile(MultipartFile multipartFile,
                                    String filePath) throws IOException {
        File desFile = new File(filePath);

        // 如果目录不存在
        if (!desFile.exists()) {
            log.info("目录不存在，开始创建: " + desFile);

            if (!desFile.mkdirs()) {
                throw new IOException("文件上传失败");
            }
        }

        String fileName = multipartFile.getOriginalFilename();

        if (!multipartFile.isEmpty() && !StringUtils.isEmpty(fileName)) {
            log.info("文件{}开始保存", fileName);
            // 保存文件
            multipartFile.transferTo(new File(desFile, fileName));
            log.info("文件{}保存结束", fileName);
        } else {
            throw new IOException("文件不能为空");
        }

        return "images/" + fileName;
    }

}
