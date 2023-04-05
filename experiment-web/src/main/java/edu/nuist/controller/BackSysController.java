package edu.nuist.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.nuist.entity.Banner;
import edu.nuist.entity.Result;
import edu.nuist.service.BackSysService;
import edu.nuist.vo.PageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
public class BackSysController {

    @Resource
    private BackSysService backSysService;

    @PostMapping(value = "/backSysAdmin/getAllBanner")
    public PageInfo<Banner> getAllBanner(@RequestBody PageRequest pageRequest) {
        PageHelper.startPage(pageRequest.getCurrentPage(), pageRequest.getPageSize());
        List<Banner> bannersList;

        try {
            bannersList = backSysService.getAllBanners();
            return new PageInfo<>(bannersList, pageRequest.getPageSize());
        } catch (Exception e) {
            return new PageInfo<>(null, pageRequest.getPageSize());
        }
    }

    @PostMapping(value = "/backSysAdmin/updateBanner")
    public Result updateBanner(@RequestBody Banner banners) {
        return backSysService.updateBanners(banners);
    }

    @GetMapping(value = "/backSysAdmin/loadAllTeachers")
    public Result updateBanner() {
        return backSysService.loadAllTeachers();
    }

}
