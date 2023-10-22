package edu.nuist.service.impl;

import edu.nuist.dao.SysRoleDao;
import edu.nuist.entity.Role;
import edu.nuist.entity.UserRole;
import edu.nuist.service.SysRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Resource
    private SysRoleDao sysRoleDao;

    @Override
    public Role getRoleByUserId(Integer userId) {
        return sysRoleDao.getRoleByUserId(userId);
    }

    @Override
    public void addUserAndRole(UserRole userRole) {
        sysRoleDao.addUserAndRole(userRole);
    }

    @Override
    public void addUserAndRoles(List<UserRole> userRoles) {
        sysRoleDao.addUserAndRoles(userRoles);
    }

    @Override
    public void deleteUserAndRole(Integer id) {
        sysRoleDao.deleteUserAndRole(id);
    }

    @Override
    public void deleteUserAndRoles(List<Integer> userIds) {
        sysRoleDao.deleteUserAndRoles(userIds);
    }

}
