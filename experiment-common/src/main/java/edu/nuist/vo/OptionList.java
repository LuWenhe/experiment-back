package edu.nuist.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OptionList {

    private String value;
    private String label;

    public OptionList(String value, String label) {
        this.value = value;
        this.label = label;
    }

}
