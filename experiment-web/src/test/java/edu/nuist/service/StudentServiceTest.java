package edu.nuist.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import edu.nuist.entity.Student;
import edu.nuist.listener.StudentExcelListener;
import io.swagger.models.auth.In;
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
public class StudentServiceTest {

    @Resource
    private StudentService studentService;

    @Test
    public void getAllStudent() {
        List<Student> allStudent = studentService.getAllStudent();
        System.out.println(allStudent);
    }

    @Test
    public void addStudent() {
        Student student = new Student();
        student.setName("BBB");
        student.setSex("男");
        student.setBirthday(new Date(System.currentTimeMillis()));
        student.setWorkPlace("北京/气象台");
        student.setJob("xxx");
        student.setMajor("软件工程");
        student.setQualification("硕士");
        student.setPhone("19923443222");
        studentService.addStudent(student);
        System.out.println("添加成功");
    }

    @Test
    public void addStudents() {
        List<Student> students = new ArrayList<>();
//        students.add(new Student(1, "小黑", "男",
//                new Date(System.currentTimeMillis()), "北京/气象谷", "xxx",
//                "计算机", "硕士", "18876356782"));
//        students.add(new Student(2, "小美", "男",
//                new Date(System.currentTimeMillis()), "北京/气象谷", "xxx",
//                "计算机", "硕士", "18876356782"));
        studentService.addStudents(students);
    }

    private List<Student> getStudentList() {
        List<Student> studentList = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            Student student = new Student();
            student.setId(i + 1);
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
        String fileName = "D:/student1.xlsx";
        EasyExcel.write(fileName, Student.class).sheet("test").doWrite(getStudentList());
        System.out.println("添加完成");
    }

    @Test
    public void readExcel() {
        String fileName = "D:/student1.xlsx";
        ExcelReader excelReader = EasyExcel.read(fileName, Student.class,
                new StudentExcelListener(studentService)).build();
        ReadSheet readSheet = EasyExcel.readSheet().build();
        excelReader.read(readSheet);
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
//        Student student = new Student(1, "小黑", "男",
//                new Date(System.currentTimeMillis()), "北京/气象谷", "xxx",
//                "计算机", "硕士", "18876356782");
//        studentService.editStudent(student);
    }

    @Test
    public void testDeleteStudents() {
        List<Integer> studentIds = Arrays.asList(50, 51);
        studentService.deleteStudentsByIds(studentIds);
    }

}
