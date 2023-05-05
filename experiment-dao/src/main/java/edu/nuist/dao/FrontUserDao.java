package edu.nuist.dao;

import edu.nuist.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface FrontUserDao {

    @Select("select user_id, username, password, name, phone, email, created_time, role " +
            "from user limit #{pageNo}, #{pageSize}")
    @Results({
            @Result(id = true, column = "user_id", property = "userId"),
            @Result(column = "username", property = "username"),
            @Result(column = "password", property = "password"),
            @Result(column = "name", property = "name"),
            @Result(column = "phone", property = "phone"),
            @Result(column = "email", property = "email"),
            @Result(column = "created_time", property = "createdTime"),
            @Result(column = "role", property = "role"),
            @Result(property = "sideMenu", column = "user_id",
                    one = @One(select = "edu.nuist.dao.FrontSideMenuDao.getSideMenu")),
            @Result(property = "userPermission", column = "user_id",
                    one = @One(select = "edu.nuist.dao.PermissionDao.getUserPermission")),
            @Result(property = "sysToken", column = "user_id",
                    one = @One(select = "edu.nuist.dao.SysTokenDao.getSysToken"))
    })
    List<User> getAllUsers(@Param("pageNo") Integer pageNo,
                           @Param("pageSize") Integer pageSize);

    @Select("select username,name,phone,email,created_time,avatar_image from users where user_id = #{param1} ")
    User getPersonInfo(int user_id);

    @Update("update users set phone = #{phone},email = #{email},avatar_image = #{avatar_image} where user_id = #{uid}")
    void updatePersonInfo(User user);

    @Select("select password from users where user_id = #{param1}")
    String validatePassword(int uid);

    @Update("update users set password = #{password} where user_id = #{uid}")
    void changePass(User user);

}
