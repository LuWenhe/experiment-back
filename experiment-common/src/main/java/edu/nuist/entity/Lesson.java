package edu.nuist.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Lesson {

    private Integer lessonId;
    private String lesson_name;
    private String pic_url;
    private Integer difficulty;
    private double lesson_time;
    private double learn_credit;
    private String suitablePerson;
    private String canLearn;
    private String mdDescription;
    private String htmlDescription;
    private Integer teacherId;
    private User user;
    private List<Chapter> chapters;
    private List<Tag> tags;
    private String teacher_name;
    private String path;
    private Date createTime;
    private Date updateTime;

}
