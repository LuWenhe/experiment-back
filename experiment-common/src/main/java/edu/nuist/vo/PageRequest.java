package edu.nuist.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequest {

    private String token;
    private int currentPage;
    private int pageSize;
    private String lesson_name;
    private String tagActive;
    private String tool_name;

}
