package edu.nuist.service;

import edu.nuist.dto.SideMenuDto;
import edu.nuist.dto.UserPermissionDto;
import edu.nuist.entity.Permission;

import java.util.List;

public interface SysPermissionService {

    List<Permission> getPermissionsByRoleId(Integer roleId);

    List<Permission> getPermissionsByUserId(Integer userId);

    UserPermissionDto getMenuOrButtonPermissionByUserId(Integer userId);

    List<SideMenuDto> getMenuByUserId(Integer userId);

}
