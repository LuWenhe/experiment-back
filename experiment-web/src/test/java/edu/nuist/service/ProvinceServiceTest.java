package edu.nuist.service;

import edu.nuist.entity.Province;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class ProvinceServiceTest {

    @Resource
    private ProvinceService provinceService;

    @Test
    void testProvince() {
        List<Province> allProvinces = provinceService.getAllProvinces();
        System.out.println(allProvinces);
    }

}
