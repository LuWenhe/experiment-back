package edu.nuist.service.impl;

import edu.nuist.dao.BackBannerDao;
import edu.nuist.entity.Banner;
import edu.nuist.service.BackBannerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BackBannerServiceImpl implements BackBannerService {

    @Resource
    private BackBannerDao backBannerDao;

    @Override
    public List<Banner> getAllBanners() {
        return backBannerDao.getAllBanners();
    }

    @Override
    public void updateBanners(Banner banner) {
        backBannerDao.updateBanners(banner);
    }

}
