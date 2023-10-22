package edu.nuist.service;

import edu.nuist.entity.Role;
import edu.nuist.entity.UserRole;

import java.util.List;

public interface SysRoleService {

    Role getRoleByUserId(Integer userId);

    void addUserAndRole(UserRole userRole);

    void addUserAndRoles(List<UserRole> userRoles);

    void deleteUserAndRole(Integer id);

    void deleteUserAndRoles(List<Integer> userIds);

}
