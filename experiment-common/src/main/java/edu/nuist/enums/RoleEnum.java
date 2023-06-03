package edu.nuist.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum RoleEnum {

    ADMIN_ROLE(0,"管理员"),
    TEACHER_ROLE(1,"老师"),
    STUDENT_ROLE(2, "学生");

    private Integer code;

    private String role;

}
