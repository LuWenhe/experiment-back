package edu.nuist.dto;

import edu.nuist.entity.JupyterFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SonChapterDto {

    private int id;
    private double son_no;
    private String name;            // 小节名称
    private String mp4;
    private String ppt;
    private String exp_type;
    private String guide_book;
    private int chapter_id;
    private String lessonName;
    private String path;
    private String description;
    private int lessonId;
    private String chapterName;
    private JupyterFile jupyterFile;

}
