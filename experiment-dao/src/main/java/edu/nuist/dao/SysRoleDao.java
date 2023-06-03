package edu.nuist.dao;

import edu.nuist.entity.Role;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

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

}
