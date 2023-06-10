package edu.nuist.service;

import edu.nuist.entity.UserExcel;
import edu.nuist.dto.UserAndRoleDto;
import edu.nuist.vo.UserAndRoleVo;

import java.util.List;

public interface UserService {

    int findUserByNameAndPassword(String username,String password);

    UserAndRoleDto findUserAndRole(String username, String password);

    UserAndRoleVo getUserAndRole(String username, String password);

    UserAndRoleDto findUserAndRoleInFront(String username, String password);

    void sava(List<UserExcel> userExcelList);

    void insertUserFromExcel(UserExcel userExcel);

    void insertUserFromExcelStu(UserExcel userExcel);

}
