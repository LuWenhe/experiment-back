package edu.nuist.service.impl;

import edu.nuist.dao.FrontUserDao;
import edu.nuist.entity.User;
import edu.nuist.service.FrontUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FrontUserServiceImpl implements FrontUserService {

    @Resource
    private FrontUserDao frontUserDao;

    @Override
    public List<User> getAllUsers(Integer pageNo, Integer pageSize) {
        return frontUserDao.getAllUsers(pageNo, pageSize);
    }

    @Override
    public User getPersonInfo(int userId) {
        return frontUserDao.getPersonInfo(userId);
    }

    @Override
    public void updatePersonInfo(User user) {
        frontUserDao.updatePersonInfo(user);
    }

    @Override
    public String validatePassword(int uid) {
        return frontUserDao.validatePassword(uid);
    }

    @Override
    public void changePass(User user) {
        frontUserDao.changePass(user);
    }

}
