package edu.nuist.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserJupyterFile {

    private Integer id;
    private Integer userId;
    private String name;
    private String url;
    private String path;
    private Integer type;
    private Integer sonId;
    private Integer lessonId;
    private Date createTime;
    private Date updateTime;

}
