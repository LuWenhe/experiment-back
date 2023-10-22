package edu.nuist.dao;

import edu.nuist.entity.Role;
import edu.nuist.entity.UserRole;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface SysRoleDao {

    /**
     * 根据用户id获取用户角色
     */
    @Select("SELECT r.id, r.name, r.description, r.create_time, r.update_time " +
            "FROM user_role ur INNER JOIN role r ON ur.role_id = r.id " +
            "WHERE ur.user_id = #{userId}")
    @Results({
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime")
    })
    Role getRoleByUserId(Integer userId);

    @Insert("INSERT INTO user_role (user_id, role_id) VALUES(#{userId}, #{roleId})")
    void addUserAndRole(UserRole userRole);

    @Insert({
            "<script>",
            "INSERT INTO user_role (user_id, role_id) VALUES ",
            "<foreach collection='userRoles' item='item' index='index' separator=','>",
            "(#{item.userId}, #{item.roleId})",
            "</foreach>",
            "</script>"
    })
    void addUserAndRoles(List<UserRole> userRoles);

    @Delete("DELETE FROM user_role WHERE id = #{id}")
    void deleteUserAndRole(Integer id);

    @Delete({"<script>",
            "DELETE FROM user_role WHERE user_id in (",
            "<foreach collection='userIds' item='userId' index='index' separator=','>",
            "#{userId}",
            "</foreach>",
            ")",
            "</script>"
    })
    void deleteUserAndRoles(List<Integer> userIds);

}
