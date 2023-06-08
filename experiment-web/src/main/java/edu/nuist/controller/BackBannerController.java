package edu.nuist.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.nuist.entity.Banner;
import edu.nuist.entity.Result;
import edu.nuist.service.BackBannerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/backBanner")
public class BackBannerController {

    @Resource
    private BackBannerService backBannerService;

    @GetMapping(value = "/getAllBanner")
    public Result getAllBanner(@RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage,
                               @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        Result result = new Result();
        PageHelper.startPage(currentPage, pageSize);

        try {
            List<Banner> allBanners = backBannerService.getAllBanners();
            PageInfo<Banner> pageInfo = new PageInfo<>(allBanners, pageSize);
            result.setData(pageInfo);
            result.setCode("200");
        } catch (Exception e) {
            result.setCode("500");
            e.printStackTrace();
        }

        return result;
    }

    @PostMapping(value = "/updateBanner")
    public Result updateBanner(@RequestBody Banner banner) {
        Result result = new Result();

        try {
            backBannerService.updateBanners(banner);
            result.setCode("200");
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode("500");
        }

        return result;
    }

}
