package edu.nuist.service.impl;

import edu.nuist.dao.BackClazzDao;
import edu.nuist.dto.ClazzDto;
import edu.nuist.entity.Student;
import edu.nuist.service.BackClazzService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BackClazzServiceImpl implements BackClazzService {

    @Resource
    private BackClazzDao backClazzDao;

    @Override
    public List<ClazzDto> getAllClazzList() {
        return backClazzDao.getAllClazzList();
    }

    @Override
    public List<ClazzDto> getClazzListByTeacherId(Integer teacherId) {
        return backClazzDao.getClazzByTeacherId(teacherId);
    }

    @Override
    public List<Student> getStudentsByClazzId(Integer clazzId) {
        return backClazzDao.getStudentsByClazzId(clazzId);
    }

    @Override
    public void addClazz(ClazzDto clazzDto) {
        backClazzDao.addClazz(clazzDto);
    }

    @Override
    public void updateClazz(ClazzDto clazzDto) {
        backClazzDao.updateClazz(clazzDto);
    }

    @Override
    public void deleteClazz(Integer clazzId) {
        backClazzDao.deleteClazz(clazzId);
    }

}
