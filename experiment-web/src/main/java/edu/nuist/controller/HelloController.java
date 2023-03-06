package edu.nuist.controller;

import edu.nuist.dao.BackUserDao;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class HelloController {

    @Resource
    private BackUserDao backUserDao;

    @RequestMapping("/hello")
    public String hello() {
        System.out.println(backUserDao.getAllTeachers());
        return "hello";
    }

}
