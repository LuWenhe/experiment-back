package edu.nuist.dao;

import edu.nuist.entity.Student;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface StudentDao {

    @Insert("INSERT INTO student (name, sex, birthday, work_place, job, major, qualification, phone) " +
            "VALUES (#{name}, #{sex}, #{birthday}, #{workPlace}, #{job}, #{major}, #{qualification}, #{phone})")
    void addStudent(Student student);

    @Insert({
            "<script>",
            "INSERT INTO student (name, sex, birthday, work_place, job, major, qualification, phone) VALUES ",
            "<foreach collection='students' item='item' index='index' separator=','>",
                "(#{item.name}, #{item.sex}, #{item.birthday}, #{item.workPlace}, #{item.job}, #{item.major}, " +
                        "#{item.qualification}, #{item.phone})",
            "</foreach>",
            "</script>"
    })
    void addStudents(List<Student> students);

    @Select("SELECT id, name, sex, birthday, work_place, job, major, qualification, phone FROM student")
    @Results({
            @Result(column = "work_place", property = "workPlace")
    })
    List<Student> getAllStudent();

    @Update("UPDATE student SET name=#{name}, sex = #{sex}, birthday = #{birthday}, " +
            "work_place = #{workPlace}, job = #{job}, major = #{major}, " +
            "qualification = #{qualification}, phone = #{phone} WHERE id = #{id}")
    void editStudent(Student student);

    @Delete({"<script>",
            "DELETE FROM student WHERE id in (",
            "<foreach collection='studentIds' item='studentId' index='index' separator=','>",
                "#{studentId}",
            "</foreach>",
            ")",
            "</script>"
    })
    void deleteStudentsByIds(List<Integer> studentIds);

}
