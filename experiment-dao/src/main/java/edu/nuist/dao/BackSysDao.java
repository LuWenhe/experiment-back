package edu.nuist.dao;

import edu.nuist.entity.Banner;
import edu.nuist.entity.User;

import java.util.List;

public interface BackSysDao {

    List<Banner> getAllBanners();

    void updateBanners(Banner banner);

    List<User> loadAllTeachers();

}
