package edu.nuist.dao;

import edu.nuist.dto.MenuDto;
import edu.nuist.entity.Permission;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SysPermissionDao {

    /**
     * 根据角色id获取用户权限
     */
    @Select("SELECT p.id, p.name, p.description, p.request_url, p.create_time, p.update_time " +
            "FROM role_permission rp INNER JOIN permission p ON rp.permission_id = p.id " +
            "WHERE rp.role_id = #{roleId}")
    @Results({
            @Result(column = "request_url", property = "requestUrl"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime")
    })
    List<Permission> getPermissionsByRoleId(Integer roleId);

    /**
     * 根据用户id获取用户权限
     */
    @Select("SELECT p.id, p.name, p.description, p.router_url, p.create_time, p.update_time " +
            "FROM user_role ur INNER JOIN role_permission rp ON rp.role_id = ur.role_id " +
            "INNER JOIN permission p ON rp.permission_id = p.id WHERE ur.user_id = #{userId}")
    @Results({
            @Result(column = "router_url", property = "routerUrl"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime")
    })
    List<Permission> getPermissionsByUserId(Integer userId);

    /**
     * 根据用户id获取目录
     */
    @Select("SELECT p.id, p.name, p.description, p.parent_id, p.icon, p.router_url, p.create_time, p.update_time " +
            "FROM user_role ur, role_permission rp, permission p " +
            "WHERE ur.role_id = rp.role_id AND rp.permission_id = p.id AND ur.user_id = #{userId}")
    @Results({
            @Result(column = "parent_id", property = "parentId"),
            @Result(column = "router_url", property = "routerUrl"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime")
    })
    List<MenuDto> getMenuByUserId(Integer userId);

}
