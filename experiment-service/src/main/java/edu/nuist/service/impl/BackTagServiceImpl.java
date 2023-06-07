package edu.nuist.service.impl;

import edu.nuist.dao.BackTagDao;
import edu.nuist.entity.Tag;
import edu.nuist.service.BackTagService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BackTagServiceImpl implements BackTagService {

    @Resource
    private BackTagDao backTagDao;

    @Override
    public List<Tag> getAllTags() {
        return backTagDao.getAllTags();
    }

    @Override
    public void addTag(Tag tag) {
        backTagDao.addTag(tag);
    }

    @Override
    public void editTag(Tag tag) {
        backTagDao.updateTage(tag);
    }

    @Override
    public void delTagByTagId(Integer tagId) {
        int count = backTagDao.findLessonTagNum(tagId);

        if (count <= 0) {
            backTagDao.delTag(tagId);
        }
    }

}
