package edu.nuist.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonSubmit {

    private String pic_url;
    private String lesson_name;
    private int difficulty;
    private String teacher_name;
    private String learn_time;
    private String learn_credit;
    private String[] tags;
    private String suitablePerson;
    private String canLearn;
    private String mdDescription;
    private String htmlDescription;
    private String dagang;
    private String cankao;
    private String goal;
    private double learnTime;
    private double learnCredit;
    private Integer lessonId;
    private Integer teacherId;
    private String path;

}
