package edu.nuist.service.impl;

import edu.nuist.dao.BackLessonDao;
import edu.nuist.dao.BackTagDao;
import edu.nuist.enums.RoleEnum;
import edu.nuist.service.BackDashBoardService;
import edu.nuist.vo.DashBoardCount;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BackDashBoardServiceImpl implements BackDashBoardService {

    @Resource
    private BackLessonDao backLessonDao;

    @Resource
    private BackTagDao backTagsDao;

    @Override
    public DashBoardCount getDashBoardInfo(Integer roleId, Integer userId) {
        DashBoardCount dashBoardCount = new DashBoardCount();
        Integer lessonSum = 0;

        if (roleId.equals(RoleEnum.ADMIN_ROLE.getCode())) {
            lessonSum = backLessonDao.getLessonSum();
        } else if (roleId.equals(RoleEnum.TEACHER_ROLE.getCode())) {
            lessonSum = backLessonDao.getLessonSumByUserId(userId);
        }

        dashBoardCount.setLessonCount(lessonSum);
        dashBoardCount.setTagCount(backTagsDao.getTagNum());

        return dashBoardCount;
    }

}
