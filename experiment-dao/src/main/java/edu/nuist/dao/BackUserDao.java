package edu.nuist.dao;

import edu.nuist.entity.Student;
import edu.nuist.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface BackUserDao {

    @Select("select user_id,username,password,name,phone,email,created_time from users where role = 1")
    List<User> getAllTeachers();

    @Insert("insert into users (username,name,password,phone,email,created_time,role) " +
            "values (#{username},#{name},#{password},#{phone},#{email},#{created_time},1)")
    void addTeacher(User user);

    @Update("update users set username = #{username}, name = #{name}, " +
            "phone = #{phone},email = #{email} where user_id = #{user_id}")
    void editTeacher(User user);

    @Select("select * from users where name = #{param1}")
    List<User> findTeachersByRName(String name);

    @Select("select user_id,username,password,name,sex,birthday,work_place,job,major,qualification,phone,email," +
            "created_time from users where role = 2")
    @Results({
            @Result(column = "work_place", property = "workPlace")
    })
    List<User> getAllStudents();

    @Insert("insert into users (username, password, name, sex, birthday, work_place, job, " +
            "major, qualification, phone, created_time, role) " +
            "values (#{username}, #{password}, #{name}, #{sex}, #{birthday}, #{workPlace}, #{job}, #{major}, " +
            "#{qualification}, #{phone}, CURDATE(), 2)")
    void addStudent(Student student);

    @Insert({
            "<script>",
            "INSERT INTO users (username, password, name, sex, birthday, work_place, job, " +
                    "major, qualification, phone, created_time, role) VALUES ",
            "<foreach collection='students' item='item' index='index' separator=','>",
            "(#{item.username}, #{item.password}, #{item.name}, #{item.sex}, #{item.birthday}, #{item.workPlace}, " +
                    "#{item.job}, #{item.major}, #{item.qualification}, #{item.phone}, CURDATE(), 2)",
            "</foreach>",
            "</script>"
    })
    void addStudents(List<Student> students);

    @Update("UPDATE users SET username = #{username}, name = #{name}, sex = #{sex}, birthday = #{birthday}, " +
            "work_place = #{workPlace}, job = #{job}, major = #{major}, " +
            "qualification = #{qualification}, phone = #{phone} WHERE user_id = #{id}")
    void editStudent(Student student);

    @Delete({"<script>",
            "DELETE FROM users WHERE user_id in (",
            "<foreach collection='studentIds' item='userId' index='index' separator=','>",
            "#{userId}",
            "</foreach>",
            ")",
            "</script>"
    })
    void deleteStudentsByIds(List<Integer> studentIds);

    @Delete("delete from users where user_id = #{user_id}")
    void deleteUsers(User user);

    @Select("select * from users where name = #{param1}")
    List<User> findStudentByRName(String name);

}
