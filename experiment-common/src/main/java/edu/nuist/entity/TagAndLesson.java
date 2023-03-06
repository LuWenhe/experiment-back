package edu.nuist.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagAndLesson {

    private int id;
    private int tag_id;
    private int lessonId;
    private Tag tag;

}
