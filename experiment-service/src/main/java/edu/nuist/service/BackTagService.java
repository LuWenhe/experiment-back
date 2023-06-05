package edu.nuist.service;

import edu.nuist.entity.Result;
import edu.nuist.entity.Tag;

import java.util.List;

public interface BackTagService {

    List<Tag> getAllTags();

    Result addTag(Tag tag);

    Result editTag(Tag tag);

    Result delTagByTagId(Integer tagId);

}
