package edu.nuist.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SonUserExp {

    private int id;
    private int son_id;
    private int user_id;
    private String exp_url;
    private int lessonId;

}
