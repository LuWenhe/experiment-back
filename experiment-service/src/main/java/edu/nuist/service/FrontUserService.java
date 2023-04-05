package edu.nuist.service;

import edu.nuist.entity.User;

import java.util.List;

public interface FrontUserService {

    List<User> getAllUsers(Integer pageNo, Integer pageSize);

    User getPersonInfo(int user_id);

    void updatePersonInfo(User user);

    String validatePassword(int uid);

    void changePass(User users);

}
