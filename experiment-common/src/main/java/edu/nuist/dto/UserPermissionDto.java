package edu.nuist.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPermissionDto {

    private Integer userId;
    private Integer roleId;
    private String roleName;
    private Set<String> requestUrlList;     // 请求接口的权限
    private Set<String> permissionList;     // 按钮的权限

}
