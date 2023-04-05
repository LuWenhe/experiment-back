package edu.nuist.dao;

import edu.nuist.entity.UserExcel;
import edu.nuist.vo.UserAndRole;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface UserDao {

    @Select("select count(*) from users where username = #{param1} and password = #{param2}")
    int findUserByNameAndPassword(String username,String password);

    @Select("select * from users where username = #{param1} and password = #{param2} ")
    UserAndRole findUserAndRole(String username, String password);

    @Select("select * from users where username = #{param1} and password = #{param2} and role = 2 ")
    UserAndRole findUserAndRoleInFront(String username,String password);

    @Insert("insert into users (username,password,realName,phone,email,created_time,role,avatar_image) values (#{username},#{password},#{realName},#{phone},#{email},#{created_time},1,#{avatar_image})")
    void insertUserFromExcel(UserExcel userExcel);

    @Insert("insert into users (username,password,realName,phone,email,created_time,role,avatar_image) values (#{username},#{password},#{realName},#{phone},#{email},#{created_time},2,#{avatar_image})")
    void insertUserFromExcelStu(UserExcel userExcel);

}
