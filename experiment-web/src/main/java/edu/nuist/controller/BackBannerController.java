package edu.nuist.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.nuist.annotation.PermissionAnnotation;
import edu.nuist.entity.Banner;
import edu.nuist.service.BackBannerService;
import edu.nuist.vo.BasicResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/backBanner")
@PermissionAnnotation
public class BackBannerController {

    @Resource
    private BackBannerService backBannerService;

    @GetMapping("/getAllBanners")
    public BasicResultVO<List<Banner>> getAllBanners() {
        List<Banner> allBanners = backBannerService.getAllBanners();
        return BasicResultVO.success(allBanners);
    }

    @GetMapping(value = "/getAllBanner")
    public BasicResultVO<PageInfo<Banner>> getAllBannerPage(
            @RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<Banner> allBanners = backBannerService.getAllBanners();
        PageInfo<Banner> pageInfo = new PageInfo<>(allBanners, pageSize);
        return BasicResultVO.success(pageInfo);
    }

    @PostMapping(value = "/updateBanner")
    public BasicResultVO<Void> updateBanner(@RequestBody Banner banner) {
        try {
            backBannerService.updateBanners(banner);
            return BasicResultVO.success();
        } catch (Exception e) {
            return BasicResultVO.fail();
        }
    }

}
