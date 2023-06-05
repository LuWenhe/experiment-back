package edu.nuist.controller;

import edu.nuist.entity.Result;
import edu.nuist.service.BackDashBoardService;
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
        return backDashBoardService.getDashBoardInfo();
    }

}
