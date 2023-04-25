package edu.nuist.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import edu.nuist.util.DateConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @ExcelProperty("编号")
    private Integer id;

    @ExcelProperty("用户名")
    private String username;

    @ExcelProperty("密码")
    private String password;

    @ExcelProperty("姓名")
    private String name;

    @ExcelProperty("性别")
    private String sex;

    @ExcelProperty(value = "生日", converter = DateConverter.class)
    private Date birthday;

    @ExcelProperty("单位")
    private String workPlace;

    @ExcelProperty("岗位")
    private String job;

    @ExcelProperty("专业")
    private String major;

    @ExcelProperty("学历")
    private String qualification;

    @ExcelProperty("联系方式")
    private String phone;

    // 所在班级
    private Integer clazzId;

}
