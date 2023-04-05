package edu.nuist.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class UserExcel {

    @ExcelProperty(value = "用户名",index = 0)
    private String username;

    private String password;

    @ExcelProperty(value = "真实姓名",index = 1)
    private String realName;

    @ExcelProperty(value = "手机号码",index = 2)
    private String phone;

    @ExcelProperty(value = "邮箱",index = 3)
    private String email;

    private String avatar_image;

    private String created_time;

    private Integer role;

}
