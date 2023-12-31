package edu.nuist.service.impl;

import edu.nuist.dao.BackUserDao;
import edu.nuist.entity.Student;
import edu.nuist.entity.User;
import edu.nuist.enums.RoleEnum;
import edu.nuist.service.BackUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class BackUserServiceImpl implements BackUserService {

    @Resource
    private BackUserDao backUsersDao;

    @Override
    public List<User> getAllTeachers() {
        return backUsersDao.getAllTeachers();
    }

    @Override
    public List<User> getTeachersByRole(Integer roleId, Integer userId) {
        List<User> users = new ArrayList<>();

        if (roleId.equals(RoleEnum.ADMIN_ROLE.getCode())) {
            users = backUsersDao.getAllTeachers();
        } else if (roleId.equals(RoleEnum.TEACHER_ROLE.getCode())) {
            User user = backUsersDao.getTeacherById(userId);
            users.add(user);
        }

        return users;
    }

    @Override
    public List<User> getAllStudents() {
        return backUsersDao.getAllStudents();
    }

    @Override
    public List<User> getTeachersByRealName(String realName) {
        return backUsersDao.findTeachersByRName(realName);
    }

    @Override
    public List<User> getStudentByRealName(String realName) {
        return backUsersDao.findStudentByRName(realName);
    }

    @Override
    public void addStudent(Student student) {
        backUsersDao.addStudent(student);
    }

    @Override
    public void addStudents(List<Student> students) {
        backUsersDao.addStudents(students);
    }

    @Override
    public void editStudent(Student student) {
        backUsersDao.editStudent(student);
    }

    @Override
    public void deleteUsersByIds(List<Integer> studentIds) {
        backUsersDao.deleteUsersByUserIds(studentIds);
    }

    @Override
    public void deleteStudentsByClazzId(Integer clazzId) {
        List<Integer> studentIds = backUsersDao.getStudentIdsByClazzId(clazzId);

        // 如果clazzId不为空, 则删除该班级下的所有学生
        if (!studentIds.isEmpty()) {
            backUsersDao.deleteUsersByUserIds(studentIds);
        }
    }

    @Override
    public void addTeacher(User user) {
        backUsersDao.addTeacher(user);
    }

    @Override
    public void editTeacher(User user) {
        backUsersDao.editTeacher(user);
    }

    @Override
    public void deleteUser(User user) {
        backUsersDao.deleteUsers(user);
    }

}
