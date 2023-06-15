package edu.nuist.controller;

import edu.nuist.service.BackDashBoardService;
import edu.nuist.vo.BasicResultVO;
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
    public BasicResultVO<DashBoardCount> getDashBoardInfo() {
        DashBoardCount dashBoardInfo = backDashBoardService.getDashBoardInfo();
        return BasicResultVO.success(dashBoardInfo);
    }

}
