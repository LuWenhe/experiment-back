package edu.nuist.entity.jupyter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KernelSpec {

    private String display_name;
    private String language;
    private String name;

}
