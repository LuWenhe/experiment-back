package edu.nuist.service.impl;

import edu.nuist.dao.BackSysDao;
import edu.nuist.entity.Banner;
import edu.nuist.entity.Result;
import edu.nuist.service.BackSysService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BackSysServiceImpl implements BackSysService {

    @Resource
    private BackSysDao backSysDao;

    @Override
    public List<Banner> getAllBanners() {
        return backSysDao.getAllBanners();
    }

    @Override
    public Result updateBanners(Banner banner) {
        Result result = new Result();

        try {
            backSysDao.updateBanners(banner);
            result.setCode("200");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            result.setCode("500");
        }

        return result;
    }

    @Override
    public Result loadAllTeachers() {
        Result result = new Result();

        try {
            result.setData(backSysDao.loadAllTeachers());
            result.setCode("200");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            result.setCode("500");
        }

        return result;
    }
}
