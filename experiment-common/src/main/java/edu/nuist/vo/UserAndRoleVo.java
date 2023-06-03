package edu.nuist.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAndRoleVo {

    private Integer userId;
    private String username;
    private Integer roleId;
    private String roleName;

}
