package edu.nuist.service.impl;

import edu.nuist.dao.SysPermissionDao;
import edu.nuist.dto.SideMenuDto;
import edu.nuist.dto.UserPermissionDto;
import edu.nuist.entity.Permission;
import edu.nuist.entity.UserPermission;
import edu.nuist.enums.RoleEnum;
import edu.nuist.service.SysPermissionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SysPermissionServiceImpl implements SysPermissionService {

    @Resource
    private SysPermissionDao sysPermissionDao;

    @Override
    public List<Permission> getPermissionsByRoleId(Integer roleId) {
        return sysPermissionDao.getPermissionsByRoleId(roleId);
    }

    @Override
    public List<Permission> getPermissionsByUserId(Integer userId) {
        return sysPermissionDao.getPermissionsByUserId(userId);
    }

    @Override
    public UserPermissionDto getMenuOrButtonPermissionByUserId(Integer userId) {
        List<UserPermission> userPermissions = sysPermissionDao.getUserPermissionByUserId(userId);
        Set<String> requestUrlList = new HashSet<>();
        Set<String> permsList = new HashSet<>();

        for (UserPermission userPermission : userPermissions) {
            // 如果请求接口权限
            if (userPermission.getType() == 1) {
                if (!StringUtils.isBlank(userPermission.getRequestUrl())) {
                    requestUrlList.add(userPermission.getRequestUrl());
                }
            } else {
                permsList.add(userPermission.getPerms());
            }
        }

        String username = userPermissions.get(0).getUsername();
        Integer roleId = userPermissions.get(0).getRoleId();
        String avatarImage = userPermissions.get(0).getAvatarImage();
        String roleName;

        if (roleId.equals(RoleEnum.ADMIN_ROLE.getCode())) {
            roleName = RoleEnum.ADMIN_ROLE.getRole();
        } else if (roleId.equals(RoleEnum.TEACHER_ROLE.getCode())) {
            roleName = RoleEnum.TEACHER_ROLE.getRole();
        } else {
            roleName = RoleEnum.STUDENT_ROLE.getRole();
        }

        return new UserPermissionDto(userId, username, avatarImage, roleId, roleName, requestUrlList, permsList);
    }

    @Override
    public List<SideMenuDto> getMenuByUserId(Integer userId) {
        return sysPermissionDao.getMenuByUserId(userId);
    }

}
