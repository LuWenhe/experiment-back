package edu.nuist.dao;

import edu.nuist.entity.Clazz;
import edu.nuist.entity.Student;
import edu.nuist.vo.StudentAndClazz;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface BackClazzDao {

    @Select("SELECT id, name, size, teacher_id teacherId, create_time, update_time " +
            "FROM clazz WHERE teacher_id = #{teacherId}")
    @Results({
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime")
    })
    List<Clazz> getClazzByTeacherId(Integer teacherId);

    @Select("SELECT user_id, username, name, sex, birthday, work_place, job, major, qualification, phone " +
            "FROM users WHERE clazz_id = #{clazzId}")
    @Results({
            @Result(column = "work_place", property = "workPlace"),
            @Result(column = "user_id", property = "id")
    })
    List<Student> getStudentsByClazzId(Integer clazzId);

    @Insert("INSERT INTO clazz(name, size, teacher_id) VALUES(#{name}, #{size}, #{teacherId})")
    boolean addClazz(Clazz clazz);

    @Update("UPDATE clazz SET name = #{name}, size = #{size} WHERE id = #{id}")
    boolean updateClazz(Clazz clazz);

    @Delete("DELETE FROM clazz WHERE id = #{clazzId}")
    boolean deleteClazz(Integer clazzId);

    @Select("SELECT s.user_id, s.name, s.sex, s.birthday, s.work_place, s.job, s.major, s.qualification, " +
            "s.phone, c.id clazzId, c.name clazzName, c.teacher_id FROM users s " +
            "INNER JOIN clazz c ON s.clazz_id = c.id where c.teacher_id = #{teacherId}")
    @Results({
            @Result(column = "work_place", property = "workPlace")
    })
    List<StudentAndClazz> getStudentsByTeacherId(Integer teacherId);

}
