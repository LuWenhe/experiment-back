package edu.nuist.service.impl;

import edu.nuist.dao.FrontTagDao;
import edu.nuist.entity.Tag;
import edu.nuist.service.FrontTagService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FrontTagServiceImpl implements FrontTagService {

    @Resource
    private FrontTagDao frontTagDao;

    @Override
    public List<Tag> getAllTags() {
        return frontTagDao.getAllTags();
    }

    @Override
    public List<Tag> getTagsByTagId(Integer tagId) {
        return frontTagDao.getTagsByTagId(tagId);
    }

}
