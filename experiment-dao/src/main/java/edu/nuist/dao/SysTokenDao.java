package edu.nuist.dao;

import edu.nuist.entity.SysToken;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

public interface SysTokenDao {

    @Select("select user_id, expire_time, token, update_time, sys_token_id " +
            "from sys_token where user_id = #{userId}")
    @Results({
            @Result(id = true, column = "user_id", property = "userId"),
            @Result(column = "expire_time", property = "expireTime"),
            @Result(column = "update_time", property = "updateTime"),
            @Result(column = "sys_token_id", property = "sysTokenId")
    })
    SysToken getSysToken(Integer userId);

}
