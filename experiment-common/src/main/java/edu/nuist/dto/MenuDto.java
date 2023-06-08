package edu.nuist.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuDto {

    private Integer id;
    private Integer parentId;
    private String name;
    private String description;
    private String routerUrl;
    private String icon;
    private Date createTime;
    private Date updateTime;
    private List<MenuDto> children;

    public void addChild(MenuDto menuDto) {
        if (children == null) {
            children = new ArrayList<>();
        }

        children.add(menuDto);
    }

}
