package edu.nuist.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRole {

    private Integer id;
    private Integer userId;
    private Integer roleId;

}
