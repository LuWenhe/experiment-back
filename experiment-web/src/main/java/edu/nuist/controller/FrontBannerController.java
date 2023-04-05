package edu.nuist.controller;

import edu.nuist.entity.Banner;
import edu.nuist.service.FrontBannerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
public class FrontBannerController {

    @Resource
    private FrontBannerService frontBannerService;

    @GetMapping("/front/getAllBanners")
    public List<Banner> getAllBanners(){
        return frontBannerService.getAllBanners();
    }

}
