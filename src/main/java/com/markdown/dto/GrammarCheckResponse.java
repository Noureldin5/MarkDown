package com.markdown.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrammarCheckResponse {
    private int totalErrors;
    private List<GrammarError> errors;
    private String summary;
}

