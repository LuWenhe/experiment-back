package edu.nuist.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chapter {

    private Integer chapter_id;
    private Integer chapter_no;
    private String chapter_name;
    private String description;
    private String mp4;
    private String ppt;
    private String exp_type;
    private String guide_book;
    private Integer lessonId;
    private List<SonChapter> sonChapterList;

}
