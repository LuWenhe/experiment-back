package edu.nuist.dao;

import edu.nuist.entity.SideMenu;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

public interface FrontSideMenuDao {

    @Select("select id, user_id, icon, 'index', title from sidemenu where user_id = #{userId}")
    @Results({
        @Result(column = "user_id", property = "userId")
    })
    SideMenu getSideMenu(Integer userId);

}
