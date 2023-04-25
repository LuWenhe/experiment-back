package edu.nuist.dao;

import edu.nuist.entity.Clazz;
import edu.nuist.entity.Student;
import edu.nuist.vo.StudentAndClazz;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BackClazzDao {

    @Select("SELECT id, name FROM clazz WHERE teacher_id = #{teacherId}")
    List<Clazz> getClazzByTeacherId(Integer teacherId);

    @Select("SELECT id, name, sex, birthday, work_place, job, major, qualification, phone FROM student " +
            "WHERE clazz_id = #{clazzId}")
    @Results({
            @Result(column = "work_place", property = "workPlace")
    })
    List<Student> getStudentsByClazzId(Integer clazzId);

    @Select("SELECT s.id, s.name, s.sex, s.birthday, s.work_place, s.job, s.major, s.qualification, " +
            "s.phone, c.id clazzId, c.name clazzName, c.teacher_id FROM student s " +
            "INNER JOIN clazz c ON s.clazz_id = c.id where c.teacher_id = #{teacherId}")
    @Results({
            @Result(column = "work_place", property = "workPlace")
    })
    List<StudentAndClazz> getStudentsByTeacherId(Integer teacherId);

}
