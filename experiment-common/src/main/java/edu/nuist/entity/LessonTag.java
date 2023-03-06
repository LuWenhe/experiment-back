package edu.nuist.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LessonTag {

    private Integer id;
    private Integer lessonId;
    private Integer tagId;
    private Tag tag;

}
