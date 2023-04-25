package edu.nuist.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Integer user_id;
    private String username;
    private String password;
    private String realName;
    private String phone;
    private String email;
    private String created_time;
    private String role;
    private String avatar_image;
    private String old_password;
    private String new_password;
    private String repeat_new_password;
    private int uid;
    private SideMenu sideMenu;
    private SysToken sysToken;
    private UserPermission userPermission;

    public User(String username, String role) {
        this.username = username;
        this.role = role;
    }

}
