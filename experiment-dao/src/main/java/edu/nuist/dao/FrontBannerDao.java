package edu.nuist.dao;

import edu.nuist.entity.Banner;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface FrontBannerDao {

    @Select("select * from banners")
    List<Banner> getAllBanners();

}
