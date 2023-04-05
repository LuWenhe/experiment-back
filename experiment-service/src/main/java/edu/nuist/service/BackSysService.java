package edu.nuist.service;

import edu.nuist.entity.Banner;
import edu.nuist.entity.Result;

import java.util.List;

public interface BackSysService {

    List<Banner> getAllBanners();

    Result updateBanners(Banner banners);

    Result loadAllTeachers();

}
