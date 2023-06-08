package edu.nuist.service.impl;

import edu.nuist.dao.SysPermissionDao;
import edu.nuist.dto.MenuDto;
import edu.nuist.entity.Permission;
import edu.nuist.service.SysPermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
    public List<MenuDto> getMenuByUserId(Integer userId) {
        return sysPermissionDao.getMenuByUserId(userId);
    }

}
