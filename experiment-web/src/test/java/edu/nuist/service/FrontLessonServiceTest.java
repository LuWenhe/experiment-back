package edu.nuist.service;

import com.alibaba.excel.EasyExcel;
import edu.nuist.entity.Result;
import edu.nuist.entity.UserExcel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class FrontLessonServiceTest {

    @Autowired
    private FrontLessonService frontLessonService;

    @Test
    public void testGetGuideBook() {
        Result guideBook = frontLessonService.getGuideBook(52);
        System.out.println(guideBook);
    }

    private List<UserExcel> getUserList() {
        List<UserExcel> list = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            UserExcel userExcel = new UserExcel();
            userExcel.setUsername("AAA");
            userExcel.setRealName("小王");
            userExcel.setPhone("18876365989");
            userExcel.setEmail("AAA@qq.com");
            list.add(userExcel);
        }

        return list;
    }

    @Test
    public void writeExcel() {
        String fileName = "D:\\student.xlsx";
        System.out.println(getUserList());
        EasyExcel.write(fileName, UserExcel.class)
                .sheet("test").doWrite(getUserList());
        System.out.println("添加完成");
    }

    @Test
    public void readExcel() {
        String fileName = "D:\\student.xlsx";
//        ExcelReader excelReader = EasyExcel.read(fileName, UserExcel.class, new ExcelListener("null")).build();
//        ReadSheet readSheet = EasyExcel.readSheet().build();
//        excelReader.read(readSheet);
    }

}