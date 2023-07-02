package edu.nuist.entity.jupyter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonRootBean {

    private List<Cells> cells;
    private Metadata metadata;
    private Integer nbFormat;
    private Integer nbFormatMinor;

}
