package edu.nuist.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RoleEnum {

    ADMIN_ROLE(1,"管理员"),
    TEACHER_ROLE(2,"老师"),
    STUDENT_ROLE(3, "学生");

    private final Integer code;

    private final String role;

}
