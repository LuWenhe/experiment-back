package edu.nuist.dao;

import edu.nuist.entity.UserPermission;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

public interface PermissionDao {

    @Select("select user_id, permission from user_permission where user_id = #{userId}")
    @Results({
            @Result(id = true, column = "user_id", property = "userId")
    })
    UserPermission getUserPermission(Integer userId);

}
