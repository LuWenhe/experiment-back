package edu.nuist.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagLesson {

    private int id;
    private int lessonId;
    private int tag_id;
    private Lesson lesson;

}
