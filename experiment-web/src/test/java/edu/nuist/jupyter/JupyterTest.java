package edu.nuist.jupyter;

import com.alibaba.fastjson.JSON;
import edu.nuist.entity.jupyter.Cells;
import edu.nuist.entity.jupyter.JsonRootBean;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

public class JupyterTest {

    public MultipartFile getMultipartFile(String filePath) throws IOException {
        File file = new File(filePath);
        FileInputStream fileInputStream = new FileInputStream(file);
        return new MockMultipartFile("copy" + file.getName(), file.getName(),
                "application/octet-stream", fileInputStream);
    }

    @Test
    void testJupyter() throws IOException {
        String filePath = "D:/Projects/ActualProjects/experiment-ai/file/jupyter/new_template.ipynb";
        MultipartFile multipartFile = getMultipartFile(filePath);

        InputStream inputStream = multipartFile.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);

        String tmpStr;
        boolean isFirstRow = true;
        StringBuilder jupyterContent = new StringBuilder();

        while ((tmpStr = reader.readLine()) != null) {
            jupyterContent.append(tmpStr);

            if (isFirstRow) {
                isFirstRow = false;
                continue;
            }

            jupyterContent.append("\n");
        }

//        if (jupyterContent.length() > 0 && jupyterContent.charAt(jupyterContent.length() - 1) == '\n') {
//            jupyterContent.setLength(jupyterContent.length() - 1);
//        }

        reader.close();
        JsonRootBean jsonRootBean = JSON.parseObject(jupyterContent.toString(), JsonRootBean.class);

        List<Cells> cells = jsonRootBean.getCells();
        StringBuilder parseContent = new StringBuilder();

        for (Cells cell : cells) {
            String cellType = cell.getCellType();
            List<String> cellSource = cell.getSource();

            if (cellType.equals("markdown")){
                if (cellSource.size() == 0){
                    continue;
                }

                if (parseContent.length() > 0) {
                    parseContent.append("\n");
                }

                for (String s : cellSource) {
                    parseContent.append(s);
                }
            } else if (cellType.equals("code")){
                if (cellSource.size() == 0){
                    continue;
                }

                if (parseContent.length() > 0) {
                    parseContent.append("\n");
                }

                parseContent.append("```python\n");

                for (String s : cellSource) {
                    parseContent.append(s);
                }

                parseContent.append("\n```");
            }
        }

        System.out.println(parseContent);
    }

    @Test
    void jupyterTest() throws IOException {
//        File file = new File("");
//        BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
//        String tmp;
//        StringBuilder sb = new StringBuilder();
//
//        while ((tmp = reader.readLine()) != null) {
//            sb.append(tmp).append("\n");
//        }
//
//        if (sb.length() > 0 && sb.charAt(sb.length() - 1) == '\n') {
//            sb.setLength(sb.length() - 1);
//        }
//
//        reader.close();
//
//        JsonRootBean jsonRootBean = JSON.parseObject(sb.toString(), JsonRootBean.class);
//        List<Cells> cells = jsonRootBean.getCells();
//        StringBuilder stringBuilder = new StringBuilder();
//
//        for (Cells cell : cells) {
//            String cellType = cell.getCellType();
//            List<String> cellSource = cell.getSource();
//
//            if (cellType.equals("markdown")){
//                if (cellSource.size() == 0){
//                    continue;
//                }
//
//                stringBuilder.append("\n");
//
//                for (String s : cellSource) {
//                    stringBuilder.append(s);
//                }
//            } else if (cellType.equals("code")){
//                if (cellSource.size() == 0){
//                    continue;
//                }
//
//                stringBuilder.append("\n");
//                stringBuilder.append("```python\n");
//
//                for (String s : cellSource) {
//                    stringBuilder.append(s);
//                }
//
//                stringBuilder.append("\n ```");
//            }
//        }
//
//        System.out.println(stringBuilder.toString());
    }

}
