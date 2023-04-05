package edu.nuist.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddChapterInEdit {

    private String chapter_name;
    private String description;
    private String mp4;
    private String ppt;
    private int lessonId;
    private int chapter_no;

}
