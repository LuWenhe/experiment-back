package edu.nuist.controller;

import edu.nuist.entity.Result;
import edu.nuist.service.BackDashBoardService;
import edu.nuist.vo.DashBoardCount;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/back")
public class BackDashBoardController {

    @Resource
    private BackDashBoardService backDashBoardService;

    @ApiOperation("返回dashboard的信息")
    @GetMapping("/getDashBoardInfo")
    public Result getDashBoardInfo() {
        Result result = new Result();
        DashBoardCount dashBoardCount = new DashBoardCount();

        try {
            DashBoardCount dashBoardInfo = backDashBoardService.getDashBoardInfo();
            result.setData(dashBoardInfo);
            result.setCode("200");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            dashBoardCount.setTagCount(0);
            dashBoardCount.setTagCount(0);
            result.setData(dashBoardCount);
            result.setCode("500");
        }

        return result;
    }

}
