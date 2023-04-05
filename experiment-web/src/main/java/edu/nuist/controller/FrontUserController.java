package edu.nuist.controller;

import edu.nuist.entity.Result;
import edu.nuist.entity.User;
import edu.nuist.service.FrontLessonService;
import edu.nuist.service.FrontUserService;
import edu.nuist.util.EncryptUtil;
import edu.nuist.vo.UserAndRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class FrontUserController {

    @Resource
    private FrontUserService frontUserService;

    @Resource
    private FrontLessonService frontLessonService;

    @RequestMapping("/frontUser/getPersonInfo")
    public Result getPersonInfo(@RequestParam("uid") int user_id){
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

    @RequestMapping("/frontUser/updatePersonInfo")
    public Result updatePersonInfo(@RequestBody User user){
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

    @RequestMapping("/frontUser/changePass")
    public Result changePass(@RequestBody User user){
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

    @RequestMapping("/frontUser/getHistoryLesson")
    public Result getHistoryLesson(@RequestParam("uid") int user_id){
        UserAndRole userAndRole = new UserAndRole();
        userAndRole.setUser_id(user_id);
        return frontLessonService.getHistoryLesson(userAndRole);
    }

}
