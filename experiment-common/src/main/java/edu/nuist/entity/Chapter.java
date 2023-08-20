package edu.nuist.entity;

import edu.nuist.dto.SonChapterDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chapter {

    private Integer id;
    private Integer chapter_no;
    private String name;
    private String description;
    private String mp4;
    private String ppt;
    private String exp_type;
    private String guide_book;
    private Integer lessonId;
    private String lessonName;
    private String path;
    private List<SonChapterDto> sonChapterList;
    private Date createTime;
    private Date updateTime;

}
