package edu.nuist.service.impl;

import edu.nuist.dao.BackTagDao;
import edu.nuist.entity.Result;
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
    public Result addTag(Tag tag) {
        Result result = new Result();

        try {
            backTagDao.addTag(tag);
            result.setCode("200");
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode("500");
        }

        return result;
    }

    @Override
    public Result editTag(Tag tag) {
        Result result = new Result();

        try {
            backTagDao.updateTage(tag);
            result.setCode("200");
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode("500");
        }

        return result;
    }

    @Override
    public Result delTagByTagId(Integer tagId) {
        Result result = new Result();

        try {
            int count = backTagDao.findLessonTagNum(tagId);

            if (count > 0) {
                result.setCode("502");
            } else {
                backTagDao.delTag(tagId);
                result.setCode("200");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode("500");
        }

        return result;
    }

}
