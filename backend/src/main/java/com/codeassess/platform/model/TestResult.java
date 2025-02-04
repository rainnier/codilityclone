package com.codeassess.platform.model;

public class TestResult {
    private boolean passed;
    private String message;
    
    // Constructor
    public TestResult(boolean passed, String message) {
        this.passed = passed;
        this.message = message;
    }
    
    public TestResult(boolean passed) {
        this.passed = passed;
    }
    
    // Getters and setters
    public boolean isPassed() {
        return passed;
    }
    
    public void setPassed(boolean passed) {
        this.passed = passed;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
}
