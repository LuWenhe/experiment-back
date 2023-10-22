package edu.nuist.dao;

import edu.nuist.entity.Student;
import edu.nuist.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface BackUserDao {

    @Select("SELECT user_id, username, password, name, phone, email, created_time FROM users WHERE role = 2")
    List<User> getAllTeachers();

    @Select("SELECT user_id, username, password, name, sex, birthday, work_place, job, major, qualification, " +
            "phone, email, created_time FROM users WHERE user_id = #{userId}")
    User getTeacherById(Integer userId);

    @Options(useGeneratedKeys = true, keyProperty = "user_id", keyColumn = "user_id")
    @Insert("insert into users (username, name, password, phone, email, role) " +
            "values (#{username}, #{name}, #{password}, #{phone}, #{email}, #{role})")
    void addTeacher(User user);

    @Update("update users set username = #{username}, name = #{name}, " +
            "phone = #{phone},email = #{email} where user_id = #{user_id}")
    void editTeacher(User user);

    @Select("select * from users where name like CONCAT('%', #{name}, '%')")
    List<User> findTeachersByRName(String name);

    @Select("select user_id,username,password,name,sex,birthday,work_place,job,major,qualification,phone,email," +
            "created_time from users where role = 3")
    @Results({
            @Result(column = "work_place", property = "workPlace")
    })
    List<User> getAllStudents();

    @Select("SELECT user_id FROM users WHERE clazz_id = #{clazzId}")
    List<Integer> getStudentIdsByClazzId(Integer clazzId);

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "user_id")
    @Insert("insert into users (username, password, name, sex, birthday, work_place, job, major, qualification, " +
            "phone, created_time, role, clazz_id) values (#{username}, #{password}, #{name}, #{sex}, #{birthday}, " +
            "#{workPlace}, #{job}, #{major}, #{qualification}, #{phone}, CURDATE(), #{role}, #{clazzId})")
    void addStudent(Student student);

    @Insert({"<script>",
            "INSERT INTO users (user_id, username, password, name, sex, birthday, work_place, job, major, " +
                    "qualification, phone, created_time, role, clazz_id) VALUES ",
            "<foreach collection='students' item='item' index='index' separator=','>",
            "(#{item.id}, #{item.username}, #{item.password}, #{item.name}, #{item.sex}, #{item.birthday}, " +
                    "#{item.workPlace}, #{item.job}, #{item.major}, #{item.qualification}, #{item.phone}, CURDATE(), " +
                    "#{item.role}, #{item.clazzId})",
            "</foreach>",
            "</script>"
    })
    void addStudents(List<Student> students);

    @Update("UPDATE users SET username = #{username}, name = #{name}, sex = #{sex}, birthday = #{birthday}, " +
            "work_place = #{workPlace}, job = #{job}, major = #{major}, qualification = #{qualification}, " +
            "phone = #{phone} WHERE user_id = #{id}")
    void editStudent(Student student);

    @Delete({"<script>",
            "DELETE FROM users WHERE user_id in (",
            "<foreach collection='userIds' item='userId' index='index' separator=','>",
            "#{userId}",
            "</foreach>",
            ")",
            "</script>"
    })
    void deleteUsersByUserIds(List<Integer> userIds);

    @Delete("delete from users where user_id = #{user_id}")
    void deleteUsers(User user);

    @Select("select * from users where name like CONCAT('%', #{name}, '%')")
    List<User> findStudentByRName(String name);

}
