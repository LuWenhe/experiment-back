package edu.nuist.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tool {

    private int tool_id;
    private String tool_name;
    private String tool_env;
    private String upload_time;
    private String download_url;

}
