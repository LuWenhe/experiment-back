package edu.nuist.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RealNameVo {
    private String realName;
    private int currentPage;
    private int pageSize;

}
