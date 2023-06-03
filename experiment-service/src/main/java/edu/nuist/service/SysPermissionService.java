package edu.nuist.service;

import edu.nuist.entity.Permission;

import java.util.List;

public interface SysPermissionService {

    List<Permission> getPermissionsByRoleId(Integer roleId);

    List<Permission> getPermissionsByUserId(Integer userId);

}
