package edu.nuist.service;

import edu.nuist.entity.UserExcel;
import edu.nuist.vo.UserAndRole;

import java.util.List;

public interface UserService {

    int findUserByNameAndPassword(String username,String password);

    UserAndRole findUserAndRole(String username, String password);

    UserAndRole findUserAndRoleInFront(String username,String password);

    void sava(List<UserExcel> userExcelList);

    void insertUserFromExcel(UserExcel userExcel);

    void insertUserFromExcelStu(UserExcel userExcel);

}
