package edu.nuist.controller;

import edu.nuist.entity.Banner;
import edu.nuist.service.FrontBannerService;
import edu.nuist.vo.BasicResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/frontBanner")
public class FrontBannerController {

    @Resource
    private FrontBannerService frontBannerService;

    @GetMapping("/getAllBanners")
    public BasicResultVO<List<Banner>> getAllBanners() {
        List<Banner> allBanners = frontBannerService.getAllBanners();
        return BasicResultVO.success(allBanners);
    }

}
