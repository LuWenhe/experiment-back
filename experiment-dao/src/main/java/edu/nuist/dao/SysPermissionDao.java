package edu.nuist.dao;

import edu.nuist.entity.Permission;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SysPermissionDao {

    /**
     * 根据角色id获取用户权限
     */
    @Select("SELECT p.id, p.name, p.description, p.url, p.create_time, p.update_time " +
            "FROM role_permission rp INNER JOIN permission p ON rp.permission_id = p.id " +
            "WHERE rp.role_id = #{roleId}")
    @Results({
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime")
    })
    List<Permission> getPermissionsByRoleId(Integer roleId);

    /**
     * 根据用户id获取用户权限
     */
    @Select("SELECT p.id, p.name, p.description, p.url, p.create_time, p.update_time " +
            "FROM user_role ur INNER JOIN role_permission rp ON rp.role_id = ur.role_id " +
            "INNER JOIN permission p ON rp.permission_id = p.id WHERE ur.user_id = #{userId}")
    List<Permission> getPermissionsByUserId(Integer userId);

}
