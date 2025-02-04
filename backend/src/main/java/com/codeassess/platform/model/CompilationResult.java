package com.codeassess.platform.model;

import lombok.Data;

@Data
public class CompilationResult {
    private boolean success;
    private String output;
    private String error;
    private long executionTime;
}
