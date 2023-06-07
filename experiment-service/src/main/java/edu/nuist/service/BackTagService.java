package edu.nuist.service;

import edu.nuist.entity.Tag;

import java.util.List;

public interface BackTagService {

    List<Tag> getAllTags();

    void addTag(Tag tag);

    void editTag(Tag tag);

    void delTagByTagId(Integer tagId);

}
