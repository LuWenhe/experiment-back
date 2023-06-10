package edu.nuist.service.impl;

import edu.nuist.dao.UserDao;
import edu.nuist.entity.UserExcel;
import edu.nuist.service.UserService;
import edu.nuist.util.EncryptUtil;
import edu.nuist.util.GetCurrentDate;
import edu.nuist.dto.UserAndRoleDto;
import edu.nuist.vo.UserAndRoleVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public int findUserByNameAndPassword(String username, String password) {
        return userDao.findUserByNameAndPassword(username, password);
    }

    @Override
    public UserAndRoleDto findUserAndRole(String username, String password) {
        return userDao.findUserAndRole(username, password);
    }

    @Override
    public UserAndRoleVo getUserAndRole(String username, String password) {
        return userDao.getUserAndRole(username, password);
    }

    @Override
    public UserAndRoleDto findUserAndRoleInFront(String username, String password) {
        return userDao.findUserAndRoleInFront(username, password);
    }

    @Override
    public void sava(List<UserExcel> userExcelList) {
        for (UserExcel userExcel : userExcelList) {
            userExcel.setPassword(new EncryptUtil().getEnpPassword(userExcel.getPassword()));
            userExcel.setCreated_time(new GetCurrentDate().getCurrentDate());
            userDao.insertUserFromExcel(userExcel);
        }
    }

    @Override
    public void insertUserFromExcel(UserExcel userExcel) {
        userDao.insertUserFromExcel(userExcel);
    }

    @Override
    public void insertUserFromExcelStu(UserExcel userExcel) {
        userDao.insertUserFromExcelStu(userExcel);
    }

}
