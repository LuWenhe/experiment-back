package edu.nuist.controller;

import edu.nuist.entity.Lesson;
import edu.nuist.entity.User;
import edu.nuist.service.FrontLessonService;
import edu.nuist.service.FrontUserService;
import edu.nuist.util.EncryptUtil;
import edu.nuist.vo.BasicResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/frontUser")
public class FrontUserController {

    @Resource
    private FrontUserService frontUserService;

    @Resource
    private FrontLessonService frontLessonService;

    @GetMapping("/getPersonInfo")
    public BasicResultVO<User> getPersonInfo(@RequestParam("userId") int userId) {
        User user = frontUserService.getPersonInfo(userId);
        return BasicResultVO.success(user);
    }

    @PostMapping("/updatePersonInfo")
    public BasicResultVO<Void> updatePersonInfo(@RequestBody User user) {
        try {
            frontUserService.updatePersonInfo(user);
            return BasicResultVO.success();
        } catch (Exception e) {
            e.printStackTrace();
            return BasicResultVO.fail();
        }
    }

    @PostMapping("/changePassword")
    public BasicResultVO<Void> changePassword(@RequestBody User user) {
        if (frontUserService.validatePassword(user.getUid())
                .equals(new EncryptUtil().getEnpPassword(user.getOld_password()))) {
            user.setPassword(new EncryptUtil().getEnpPassword(user.getNew_password()));
            frontUserService.changePass(user);
            return BasicResultVO.success();
        } else {
            return BasicResultVO.fail("旧密码输入错误");
        }
    }

    @GetMapping("/getHistoryLesson")
    public BasicResultVO<List<Lesson>> getHistoryLesson(@RequestParam("uid") int user_id) {
        try {
            List<Lesson> historyLesson = frontLessonService.getLessonByUserId(user_id);
            return BasicResultVO.success(historyLesson);
        } catch (Exception e) {
            e.printStackTrace();
            return BasicResultVO.fail();
        }
    }

}
