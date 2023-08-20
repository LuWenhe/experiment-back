package edu.nuist.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChapterDto {

    private Integer id;
    private String name;
    private String description;
    private String mp4;
    private String ppt;
    private int lessonId;
    private String path;
    private String lessonName;
    private int chapter_no;
    private String lessonPath;

}
