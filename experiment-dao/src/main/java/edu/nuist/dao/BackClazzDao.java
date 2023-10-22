package edu.nuist.dao;

import edu.nuist.dto.ClazzDto;
import edu.nuist.entity.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface BackClazzDao {

    @Select("SELECT id, name, size, teacher_id teacherId, create_time, update_time FROM clazz")
    @Results({
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime")
    })
    List<ClazzDto> getAllClazzList();

    @Select("SELECT id, name, size, teacher_id teacherId, create_time, update_time " +
            "FROM clazz WHERE teacher_id = #{teacherId}")
    @Results({
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime")
    })
    List<ClazzDto> getClazzByTeacherId(Integer teacherId);

    @Select("SELECT user_id, username, name, sex, birthday, work_place, job, major, qualification, phone " +
            "FROM users WHERE clazz_id = #{clazzId}")
    @Results({
            @Result(column = "work_place", property = "workPlace"),
            @Result(column = "user_id", property = "id")
    })
    List<Student> getStudentsByClazzId(Integer clazzId);

    @Insert("INSERT INTO clazz(name, size, teacher_id) VALUES(#{name}, #{size}, #{teacherId})")
    boolean addClazz(ClazzDto clazz);

    @Update("UPDATE clazz SET name = #{name}, size = #{size}, teacher_id = #{teacherId} WHERE id = #{id}")
    boolean updateClazz(ClazzDto clazzDto);

    @Delete("DELETE FROM clazz WHERE id = #{clazzId}")
    boolean deleteClazz(Integer clazzId);

}
