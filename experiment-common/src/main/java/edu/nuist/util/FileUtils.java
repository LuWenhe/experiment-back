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
                throw new IOException("文件创建失败");
            }
        }

        String fileName = multipartFile.getOriginalFilename();

        if (!multipartFile.isEmpty() && !StringUtils.isEmpty(fileName)) {
            log.info("文件: {}开始保存", fileName);
            // 保存文件
            multipartFile.transferTo(new File(desFile, fileName));
            log.info("文件: {}保存结束", fileName);
        } else {
            throw new IOException("文件不能为空");
        }

        return fileName;
    }

    public static void createFile(String filePath) {
        File file = new File(filePath);
        String name = file.getName();

        if (file.exists()) {
            log.info("目录: {}已经存在", name);
            return;
        }

        if (!file.mkdir()) {
            log.info("目录: {}创建失败", name);
            return;
        }

        log.info("目录: {}创建成功", name);
    }

    public static void renameFile(String srcPath, String desPath) {
        File srcFile = new File(srcPath);

        if (!srcFile.exists()) {
            log.info("目录: {}不存在", srcFile.getAbsoluteFile());

            File destFile = new File(desPath);

            if (destFile.mkdirs()) {
                log.info("目录: {}创建成功", destFile.getName());
            }

            return;
        }

        File newFile = new File(desPath);
        String newFileName = newFile.getName();

        if (!srcFile.renameTo(newFile)) {
            log.info("目录: {}命名失败", newFileName);
            return;
        }

        log.info("目录: {}命名成功", newFileName);
    }

    public static void deleteFiles(String filePath) {
        File file = new File(filePath);

        if (!file.exists()) {
            log.info("目录: {}不存在", filePath);
            return;
        }

        File[] files = file.listFiles();

        if (files == null) {
            log.info("目录: {}下没有文件", filePath);
            return;
        }

        for (File f : files) {
            if (!f.delete()) {
                log.info("文件: {}删除失败", f.getName());
                return;
            }

            log.info("文件: {}删除成功", f.getName());
        }
    }

    public static void deleteFile(String filePath) {
        File file = new File(filePath);

        if (!file.exists()) {
            log.info("目录: {}不存在", filePath);
            return;
        }

        if (!file.delete()) {
            log.info("目录: {}删除失败", file.getName());
            return;
        }

        log.info("目录: {}删除成功", file.getName());
    }

    public static void deleteDirectoriesAndFiles(String filePath) {
        File file = new File(filePath);

        if (!file.exists()) {
            log.info("目录: {}不存在", filePath);
            return;
        }

        if (file.isDirectory()) {
            File[] files = file.listFiles();

            if (files == null) {
                log.info("目录: {}下不存在文件", filePath);
                return;
            }

            for (File f : files) {
                String path = f.getAbsolutePath();
                deleteDirectoriesAndFiles(path);
            }
        }

        String type;

        if (file.isDirectory()) {
            type = "目录";
        } else {
            type = "文件";
        }

        if (file.delete()) {
            log.info("删除{}: {}成功", type, file.getName());
        } else {
            log.info("删除{}: {}失败", type, file.getName());
        }
    }

    public static boolean ifExists(MultipartFile multipartFile, String path) {
        String fileName = multipartFile.getOriginalFilename();

        if (fileName == null) {
            return false;
        }

        File file = new File(path, fileName);

        if (file.exists()) {
            log.info("文件: {}已经存在", fileName);
            return true;
        }

        return false;
    }

}
