package edu.nuist.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Permission {

    private Integer id;
    private String name;
    private String description;
    private String url;
    private Date createTime;
    private Date updateTime;

}
