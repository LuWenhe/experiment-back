package edu.nuist.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SideMenu {

    private Integer id;
    private Integer userId;
    private String icon;
    private String index;
    private String title;

}
