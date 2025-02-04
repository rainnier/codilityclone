package com.codeassess.platform.model;

import lombok.Data;
import java.util.Map;

@Data
public class CodeSubmission {
    private String code;
    private String language;
    private String fileName;
    private String className;
    private Map<Integer, Integer> testCases;
}
