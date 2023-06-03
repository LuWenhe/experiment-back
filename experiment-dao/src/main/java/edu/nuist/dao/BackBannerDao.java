package edu.nuist.dao;

import edu.nuist.entity.Banner;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface BackBannerDao {

    @Select("select id, banner_url from banners")
    List<Banner> getAllBanners();

    @Update("update banners set banner_url = #{banner_url} where id = #{id} ")
    void updateBanners(Banner banners);

}
