package edu.nuist.service.impl;

import edu.nuist.dao.FrontBannerDao;
import edu.nuist.entity.Banner;
import edu.nuist.service.FrontBannerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FrontBannerServiceImpl implements FrontBannerService {

    @Resource
    private FrontBannerDao frontBannerDao;

    @Override
    public List<Banner> getAllBanners() {
        return frontBannerDao.getAllBanners();
    }

}
