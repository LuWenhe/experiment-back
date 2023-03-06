package edu.nuist.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SonChapterAndUrl {
    private int son_id;
    private String exp_url;
    private String guide_book;
}
