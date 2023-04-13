package edu.nuist.service.impl;

import edu.nuist.dao.ProvinceDao;
import edu.nuist.entity.Province;
import edu.nuist.service.ProvinceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProvinceServiceImpl implements ProvinceService {

    @Resource
    private ProvinceDao provinceDao;

    @Override
    public List<Province> getAllProvinces() {
        return provinceDao.getAllProvinces();
    }

}
