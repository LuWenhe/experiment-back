package edu.nuist.dao;

import edu.nuist.ExperimentWebApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = ExperimentWebApplication.class)
public class BackUserDaoTest {

    @Autowired
    private BackUserDao backUserDao;

    @Test
    void getAllTeachers() {
        System.out.println(backUserDao.getAllTeachers());
    }

}