package edu.nuist.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {

    private String code;
    private String msg;
    private Object data;
    private double wasteTime;
    private String token;
    private String role;
    private String name;

}
