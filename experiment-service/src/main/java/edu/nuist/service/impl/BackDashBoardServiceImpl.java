package edu.nuist.service.impl;

import edu.nuist.dao.BackLessonDao;
import edu.nuist.dao.BackTagDao;
import edu.nuist.entity.Result;
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
    public Result getDashBoardInfo() {
        Result result = new Result();
        DashBoardCount dashBoardCount = new DashBoardCount();

        try {
            int lessonCount = backLessonDao.getLessonCount();
            int tagCount = backTagsDao.getTagNum();
            dashBoardCount.setLessonCount(lessonCount);
            dashBoardCount.setTagCount(tagCount);
            result.setCode("200");
            result.setData(dashBoardCount);
        } catch (Exception e) {
            e.printStackTrace();
            dashBoardCount.setLessonCount(0);
            dashBoardCount.setTagCount(0);
            result.setData(dashBoardCount);
            result.setCode("500");
        }

        return result;
    }

}
