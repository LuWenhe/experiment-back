package edu.nuist.service.impl;

import edu.nuist.dao.FrontChapterDao;
import edu.nuist.entity.Chapter;
import edu.nuist.service.FrontChapterService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FrontChapterServiceImpl implements FrontChapterService {

    @Resource
    private FrontChapterDao frontChapterDao;

    @Override
    public List<Chapter> getAllChapters() {
        return frontChapterDao.getAllChapters();
    }

}
