package edu.nuist.service;

import edu.nuist.entity.Banner;

import java.util.List;

public interface BackBannerService {

    List<Banner> getAllBanners();

    void updateBanners(Banner banners);

}
