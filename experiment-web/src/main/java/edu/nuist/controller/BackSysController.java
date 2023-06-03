package edu.nuist.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.nuist.entity.Banner;
import edu.nuist.entity.Result;
import edu.nuist.service.BackBannerService;
import edu.nuist.vo.PageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
public class BackSysController {

    @Resource
    private BackBannerService backBannerService;

    @PostMapping(value = "/backSysAdmin/getAllBanner")
    public PageInfo<Banner> getAllBanner(@RequestBody PageRequest pageRequest) {
        PageHelper.startPage(pageRequest.getCurrentPage(), pageRequest.getPageSize());
        List<Banner> bannersList;

        try {
            bannersList = backBannerService.getAllBanners();
            return new PageInfo<>(bannersList, pageRequest.getPageSize());
        } catch (Exception e) {
            return new PageInfo<>(null, pageRequest.getPageSize());
        }
    }

    @PostMapping(value = "/backSysAdmin/updateBanner")
    public Result updateBanner(@RequestBody Banner banners) {
        return backBannerService.updateBanners(banners);
    }

}
