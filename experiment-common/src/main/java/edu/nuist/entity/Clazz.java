package edu.nuist.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Clazz {

    private Integer id;
    private String name;
    private Integer size;
    private Integer teacherId;
    private Date createTime;
    private Date updateTime;

}
