package edu.nuist.controller;

import edu.nuist.entity.Province;
import edu.nuist.service.ProvinceService;
import edu.nuist.vo.BasicResultVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/province")
public class ProvinceController {

    @Resource
    private ProvinceService provinceService;

    @GetMapping("/getAllProvinces")
    public BasicResultVO<List<Province>> getProvinces() {
        List<Province> allProvinces = provinceService.getAllProvinces();
        return BasicResultVO.success(allProvinces);
    }

}
