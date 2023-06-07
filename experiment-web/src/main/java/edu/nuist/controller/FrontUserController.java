package edu.nuist.controller;

import edu.nuist.entity.Lesson;
import edu.nuist.entity.Result;
import edu.nuist.entity.User;
import edu.nuist.service.FrontLessonService;
import edu.nuist.service.FrontUserService;
import edu.nuist.util.EncryptUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping("/getPersonInfo")
    public Result getPersonInfo(@RequestParam("uid") int user_id) {
        Result result = new Result();

        try {
            result.setData(frontUserService.getPersonInfo(user_id));
            result.setCode("200");
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode("500");
        }


        return result;
    }

    @RequestMapping("/updatePersonInfo")
    public Result updatePersonInfo(@RequestBody User user) {
        Result result = new Result();

        try {
            frontUserService.updatePersonInfo(user);
            result.setCode("200");
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode("500");
        }

        return result;
    }

    @RequestMapping("/changePass")
    public Result changePass(@RequestBody User user) {
        Result result = new Result();

        if (frontUserService.validatePassword(user.getUid())
                .equals(new EncryptUtil().getEnpPassword(user.getOld_password()))) {
            user.setPassword(new EncryptUtil().getEnpPassword(user.getNew_password()));
            frontUserService.changePass(user);
            result.setCode("200");
        } else {
            result.setCode("502");
            result.setMsg("旧密码输入错误");
        }

        return result;
    }

    @RequestMapping("/getHistoryLesson")
    public Result getHistoryLesson(@RequestParam("uid") int user_id) {
        Result result = new Result();

        try {
            List<Lesson> historyLesson = frontLessonService.getHistoryLesson(user_id);
            result.setData(historyLesson);
            result.setCode("200");
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode("500");
        }

        return result;
    }

}
