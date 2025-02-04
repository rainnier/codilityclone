package com.codeassess.platform.service;

import org.springframework.stereotype.Service;

import com.codeassess.platform.model.TestResult;

@Service
public class TestExecutionService {
    public TestResult runTests(String sourceCode, String testCode) {
        // Add test execution logic
        return new TestResult(false);
    }
}
