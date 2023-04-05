package edu.nuist.controller;

import edu.nuist.entity.Result;
import edu.nuist.service.BackDashBoardService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class BackDashBoardController {

    @Resource
    private BackDashBoardService backDashBoardService;

    @ApiOperation("返回dashboard的信息")
    @PostMapping("/back/getDashBoardInfo")
    public Result getDashBoardInfo(){
        return backDashBoardService.getDashBoardInfo();
    }

}
