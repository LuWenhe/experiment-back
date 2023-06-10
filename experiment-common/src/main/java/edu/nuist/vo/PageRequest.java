package edu.nuist.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequest {

    private String token;
    private Integer userId;
    private Integer roleId;
    private int currentPage;
    private int pageSize;
    private String lessonName;
    private String tagName;
    private String tool_name;

}
