package edu.nuist.entity.jupyter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Metadata {

    private KernelSpec kernelSpec;
    private languageInfo languageInfo;

}
