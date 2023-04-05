package edu.nuist.dao;

import edu.nuist.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface BackUserDao {

    @Select("select user_id,username,password,realName,phone,email,created_time from users where role = 1")
    List<User> getAllTeachers();

    @Select("select user_id,username,password,realName,phone,email,created_time from users where role = 2")
    List<User> getAllStudents();

    @Insert("insert into users (username,realName,password,phone,email,created_time,role) " +
            "values (#{username},#{realName},#{password},#{phone},#{email},#{created_time},1)")
    void addTeacher(User user);

    @Insert("insert into users (username,realName,password,phone,email,created_time,role) " +
            "values (#{username},#{realName},#{password},#{phone},#{email},#{created_time},2)")
    void addStudent(User user);

    @Update("update users set username = #{username},realName = #{realName}, " +
            "phone = #{phone},email = #{email} where user_id = #{user_id}")
    void editTeacher(User user);

    @Delete("delete from users where user_id = #{user_id}")
    void deleteUsers(User user);

    @Select("select * from users where realName = #{param1}")
    List<User> findTeachersByRName(String realName);

    @Select("select * from users where realName = #{param1}")
    List<User> findStudentByRName(String realName);

}
