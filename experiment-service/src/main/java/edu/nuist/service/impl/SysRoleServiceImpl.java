package edu.nuist.service.impl;

import edu.nuist.dao.SysRoleDao;
import edu.nuist.entity.Role;
import edu.nuist.service.SysRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Resource
    private SysRoleDao sysRoleDao;

    @Override
    public Role getRoleByUserId(Integer userId) {
        return sysRoleDao.getRoleByUserId(userId);
    }

}
