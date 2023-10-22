package edu.nuist.service;

import com.alibaba.excel.EasyExcel;
import edu.nuist.entity.Student;
import edu.nuist.entity.User;
import edu.nuist.util.EncryptUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class BackUserServiceTest {

    @Resource
    private BackUserService backUserService;

    @Test
    public void getAllStudent() {
        List<User> students = backUserService.getAllStudents();
        System.out.println(students);
    }

    @Test
    public void addStudent() {
        Student student = new Student();
        student.setUsername("bbb");
        student.setPassword(new EncryptUtil().getEnpPassword("bbb"));
        student.setName("BBB");
        student.setSex("男");
        student.setBirthday(new Date(System.currentTimeMillis()));
        student.setWorkPlace("北京/气象台");
        student.setJob("xxx");
        student.setMajor("软件工程");
        student.setQualification("硕士");
        student.setPhone("19923443222");
        backUserService.addStudent(student);
        System.out.println("添加成功");
    }

    private List<Student> getStudentList() {
        List<Student> studentList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Student student = new Student();
            student.setId(i + 10);
            student.setUsername("xiaohong");
            student.setPassword(new EncryptUtil().getEnpPassword("18865437393"));
            student.setName("小红");
            student.setSex("女");
            student.setBirthday(new Date(System.currentTimeMillis()));
            student.setWorkPlace("北京/气象谷");
            student.setMajor("计算机科学");
            student.setJob("打杂滴");
            student.setQualification("硕士");
            student.setPhone("18865437393");
            studentList.add(student);
        }

        return studentList;
    }

    @Test
    public void writeExcel() {
        String fileName = "D:/student.xlsx";
        EasyExcel.write(fileName, Student.class).sheet("test").doWrite(getStudentList());
        System.out.println("添加完成");
    }

    @Test
    public void testDate() throws ParseException {
//        String date = "2023-04-07";
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        java.util.Date parse = dateFormat.parse(date);
//        Date date1 = new Date(parse.getTime());
//        System.out.println(date1);

        LocalDate localDate = LocalDate.of(1900, 1, 1);
        //excel 有些奇怪的bug, 导致日期数差2
        localDate = localDate.plusDays(45025 - 2);
        Date date = Date.valueOf(localDate);
        System.out.println(date);
    }

    @Test
    public void testEditStudent() {
//        Student student = new Student(70,"xiaoqing1",
//                new EncryptUtil().getEnpPassword("18856564545"),"小青1","女",
//                null,null,null,null,null,null,null);
//        backUserService.editStudent(student);
    }

    @Test
    public void testDeleteStudents() {
        List<Integer> studentIds = Arrays.asList(69);
        backUserService.deleteUsersByIds(studentIds);
    }

    @Test
    public void testDeleteStudents2() {
        backUserService.deleteStudentsByClazzId(12);
    }

    @Test
    void testGetTeachersByRealName() {
        List<User> teachers = backUserService.getTeachersByRealName("小");
        System.out.println(teachers);
    }

}
