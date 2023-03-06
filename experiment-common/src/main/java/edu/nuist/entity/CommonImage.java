package edu.nuist.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonImage {

    private String repository;
    private String tag;
    private String image_id;
    private String size;

}
