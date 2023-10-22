package edu.nuist.service;

import edu.nuist.dto.ClazzDto;
import edu.nuist.entity.Student;

import java.util.List;

public interface BackClazzService {

    List<ClazzDto> getAllClazzList();

    List<ClazzDto> getClazzListByTeacherId(Integer teacherId);

    List<Student> getStudentsByClazzId(Integer clazzId);

    void addClazz(ClazzDto clazz);

    void updateClazz(ClazzDto clazz);

    void deleteClazz(Integer clazzId);

}
