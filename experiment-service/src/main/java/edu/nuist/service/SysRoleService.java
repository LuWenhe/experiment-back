package edu.nuist.service;

import edu.nuist.entity.Role;

public interface SysRoleService {

    Role getRoleByUserId(Integer userId);

}
