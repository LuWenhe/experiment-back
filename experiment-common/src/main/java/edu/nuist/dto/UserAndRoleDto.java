package edu.nuist.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAndRoleDto {

    private int user_id;
    private String username;
    private String role;
    private String avatar_image;

}
