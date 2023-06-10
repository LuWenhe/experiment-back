package edu.nuist.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPermission {

    private Integer userId;
    private Integer roleId;
    private String roleName;
    private Integer type;
    private String requestUrl;
    private String perms;

}
