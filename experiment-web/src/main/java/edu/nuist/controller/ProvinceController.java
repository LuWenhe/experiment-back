package edu.nuist.controller;

import edu.nuist.entity.Province;
import edu.nuist.entity.Result;
import edu.nuist.service.ProvinceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class ProvinceController {

    @Resource
    private ProvinceService provinceService;

    @GetMapping("/getAllProvinces")
    public Result getProvinces() {
        Result result = new Result();

        try {
            List<Province> allProvinces = provinceService.getAllProvinces();
            result.setData(allProvinces);
            result.setCode("200");
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode("500");
        }

        return result;
    }

}
