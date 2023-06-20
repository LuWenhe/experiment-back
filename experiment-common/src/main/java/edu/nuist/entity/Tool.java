package edu.nuist.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tool {

    private int tool_id;
    private String tool_name;
    private String tool_env;
    private String download_url;
    private Date upload_time;

}
