package edu.nuist.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LessonTree {

    private Integer id;
    private String name;
    private String path;
    private Integer parentId;
    private Integer level;
    private Integer lessonId;
    private Integer chapterId;
    private Integer sonId;
    private boolean isLeaf;
    private Date createTime;
    private Date updateTime;

}
