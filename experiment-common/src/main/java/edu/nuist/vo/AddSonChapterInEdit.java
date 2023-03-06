package edu.nuist.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddSonChapterInEdit {

    private int son_id;
    private int lessonId;
    private int chapter_id;
    private double son_no;
    private String son_name;
    private String description;
    private String exp_type;
    private String mp4;
    private String ppt;

}
