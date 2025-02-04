package com.codeassess.platform.model;

public class TestCase {
    private String id; // Unique identifier for the test case
    private String description; // Description of the test case
    private String expectedResult; // Expected result of the test case
    private String actualResult; // Actual result of the test case
    private boolean passed; // Indicates if the test case passed
    private Integer expectedOutput; // Add this field to store the expected output
    private Integer input; // Assuming this field exists

    // Constructor
    public TestCase(String id, String description, String expectedResult) {
        this.id = id;
        this.description = description;
        this.expectedResult = expectedResult;
        this.passed = false; // Default to false until evaluated
    }

    // Add this constructor
    public TestCase(Integer input, Integer expectedOutput) {
        this.input = input;
        this.expectedOutput = expectedOutput;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getExpectedResult() {
        return expectedResult;
    }

    public String getActualResult() {
        return actualResult;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setActualResult(String actualResult) {
        this.actualResult = actualResult;
        this.passed = actualResult.equals(expectedResult); // Evaluate pass/fail
    }

    public Integer getExpectedOutput() {
        return expectedOutput; // Ensure expectedOutput is a field in TestCase
    }

    public Integer getInput() { // Add this method
        return input;
    }
}
