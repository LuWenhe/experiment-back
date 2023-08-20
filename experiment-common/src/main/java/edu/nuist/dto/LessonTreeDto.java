package edu.nuist.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LessonTreeDto {

    private Integer id;
    private String name;
    private String path;
    private Integer parentId;
    private Integer level;
    private Integer leaf;           // 是否是叶子节点
    private Integer lessonId;
    private Integer chapterId;
    private Integer sonId;
    private Date createTime;
    private Date updateTime;
    private List<LessonTreeDto> children;

    public void addChild(LessonTreeDto lessonTreeDto) {
        if (children == null) {
            children = new ArrayList<>();
        }

        children.add(lessonTreeDto);
    }

}
