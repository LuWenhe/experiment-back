package edu.nuist.service.impl;

import edu.nuist.dao.BackClazzDao;
import edu.nuist.entity.Clazz;
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
    public List<Clazz> getAllClazzList() {
        return backClazzDao.getAllClazzList();
    }

    @Override
    public List<Clazz> getClazzListByTeacherId(Integer teacherId) {
        return backClazzDao.getClazzByTeacherId(teacherId);
    }

    @Override
    public List<Student> getStudentsByClazzId(Integer clazzId) {
        return backClazzDao.getStudentsByClazzId(clazzId);
    }

    @Override
    public boolean addClazz(Clazz clazz) {
        return backClazzDao.addClazz(clazz);
    }

    @Override
    public boolean updateClazz(Clazz clazz) {
        return backClazzDao.updateClazz(clazz);
    }

    @Override
    public boolean deleteClazz(Integer clazzId) {
        return backClazzDao.deleteClazz(clazzId);
    }

}
