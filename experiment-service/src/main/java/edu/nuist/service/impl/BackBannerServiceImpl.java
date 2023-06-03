package edu.nuist.service.impl;

import edu.nuist.dao.BackBannerDao;
import edu.nuist.entity.Banner;
import edu.nuist.entity.Result;
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
    public Result updateBanners(Banner banner) {
        Result result = new Result();

        try {
            backBannerDao.updateBanners(banner);
            result.setCode("200");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            result.setCode("500");
        }

        return result;
    }

}
