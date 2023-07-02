package edu.nuist.entity.jupyter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cells {

    private String cellType;
    private Metadata metadata;
    private List<String> source;

}
