package edu.nuist.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysToken {

    private Integer userId;
    private Date expireTime;
    private String token;
    private Date updateTime;
    private Integer sysTokenId;

}
