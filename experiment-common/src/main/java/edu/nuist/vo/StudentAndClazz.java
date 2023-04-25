package edu.nuist.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentAndClazz {

    private Integer id;
    private String username;
    private String password;
    private String name;
    private String sex;
    private Date birthday;
    private String workPlace;
    private String job;
    private String major;
    private String qualification;
    private String phone;
    private String clazzName;

}
