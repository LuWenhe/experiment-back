package edu.nuist.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClazzDto {

    private Integer id;
    private String name;
    private Integer size;
    private Integer teacherId;
    private String teacherName;
    private Date createTime;
    private Date updateTime;

}
