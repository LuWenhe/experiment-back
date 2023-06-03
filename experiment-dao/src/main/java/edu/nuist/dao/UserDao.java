package edu.nuist.dao;

import edu.nuist.entity.UserExcel;
import edu.nuist.vo.UserAndRole;
import edu.nuist.vo.UserAndRoleVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

public interface UserDao {

    @Select("select count(*) from users where username = #{param1} and password = #{param2}")
    int findUserByNameAndPassword(String username, String password);

    @Select("select user_id, username, role, avatar_image from users " +
            "where username = #{param1} and password = #{param2} ")
    UserAndRole findUserAndRole(String username, String password);

    /**
     * 根据用户名和密码获取用户和对应的角色
     */
    @Select("SELECT u.user_id, u.username, r.id role_id, r.name name FROM users u " +
            "INNER JOIN user_role ur ON u.user_id = ur.user_id " +
            "INNER JOIN role r ON ur.role_id = r.id WHERE u.username = #{username} and u.password = #{password}")
    @Results({
            @Result(column = "user_id", property = "userId"),
            @Result(column = "role_id", property = "roleId"),
            @Result(column = "name", property = "roleName")
    })
    UserAndRoleVo getUserAndRole(String username, String password);

    @Select("select * from users where username = #{param1} and password = #{param2} and role = 2 ")
    UserAndRole findUserAndRoleInFront(String username,String password);

    @Insert("insert into users (username,password,name,phone,email,created_time,role,avatar_image) " +
            "values (#{username},#{password},#{name},#{phone},#{email},#{created_time},1,#{avatar_image})")
    void insertUserFromExcel(UserExcel userExcel);

    @Insert("insert into users (username,password,name,phone,email,created_time,role,avatar_image) " +
            "values (#{username},#{password},#{name},#{phone},#{email},#{created_time},2,#{avatar_image})")
    void insertUserFromExcelStu(UserExcel userExcel);

}
