package edu.nuist.service;

import edu.nuist.entity.Tag;

import java.util.List;

public interface FrontTagService {

    List<Tag> getAllTags();

    List<Tag> getTagsByTagId(Integer tagId);

}
