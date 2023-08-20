package edu.nuist.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JupyterFileDto {

    private List<Integer> jupyterIds;
    private List<String> jupyterPaths;

}
