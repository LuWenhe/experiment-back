package edu.nuist.entity.jupyter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class languageInfo {

    private codeMirrorMode codeMirrorMode;
    private String fileExtension;
    private String mimeType;
    private String name;
    private String nbConvertExporter;
    private String pygmentsLexer;
    private String version;

}
