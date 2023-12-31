package edu.nuist.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SonChapter {

    private Integer id;
    private double son_no;
    private String name;
    private String description;
    private String mp4;
    private String ppt;
    private String exp_type;
    private String guide_book;
    private Integer chapter_id;
    private String lessonName;
    private String path;
    private String exp_url;
    private Integer jupyterId;

}
