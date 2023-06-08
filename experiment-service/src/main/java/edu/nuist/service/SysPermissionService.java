package edu.nuist.service;

import edu.nuist.dto.MenuDto;
import edu.nuist.entity.Permission;

import java.util.List;

public interface SysPermissionService {

    List<Permission> getPermissionsByRoleId(Integer roleId);

    List<Permission> getPermissionsByUserId(Integer userId);

    List<MenuDto> getMenuByUserId(Integer userId);

}
