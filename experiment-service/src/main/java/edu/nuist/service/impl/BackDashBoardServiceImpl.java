package edu.nuist.service.impl;

import edu.nuist.dao.BackLessonDao;
import edu.nuist.dao.BackTagDao;
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
    public DashBoardCount getDashBoardInfo() {
        DashBoardCount dashBoardCount = new DashBoardCount();

        dashBoardCount.setLessonCount(backLessonDao.getLessonCount());
        dashBoardCount.setTagCount(backTagsDao.getTagNum());

        return dashBoardCount;
    }

}
