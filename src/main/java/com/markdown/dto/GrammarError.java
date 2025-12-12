package com.markdown.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrammarError {
    private String message;
    private int line;
    private int column;
    private int length;
    private String context;
    private String[] suggestions;
}

