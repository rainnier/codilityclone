package com.codeassess.platform.service;

import com.codeassess.platform.model.CodeSubmission;
import com.codeassess.platform.model.CompilationResult;
import com.codeassess.platform.model.TestCase;
import org.springframework.stereotype.Service;

import javax.tools.*;
import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JavaCompilerService {

    public CompilationResult compileAndRun(CodeSubmission submission) {
        CompilationResult result = new CompilationResult();
        long startTime = System.currentTimeMillis();

        String code = submission.getCode();
        String className = submission.getClassName();
        String fileName = className + ".java";

        // Save the submitted code to a file named fileName
        try (PrintWriter writer = new PrintWriter(fileName)) {
            writer.write(code);
        } catch (FileNotFoundException e) {
            result.setSuccess(false);
            result.setError("Error saving file: " + e.getMessage());
            return result;
        }

        // Compile the file
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        
        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjects(fileName);
        boolean success = compiler.getTask(null, fileManager, null, null, null, compilationUnits).call();
        
        if (!success) {
            result.setSuccess(false);
            result.setError("Compilation failed.");
            return result;
        }

        // Convert the Map<Integer, Integer> to List<TestCase>
        List<TestCase> testCases = submission.getTestCases().entrySet().stream()
            .map(entry -> new TestCase(entry.getKey(), entry.getValue()))
            .collect(Collectors.toList());

        // Call runChecker to execute the code and run test cases
        return runChecker(className, testCases, result, startTime);
    }

    private CompilationResult runChecker(String className, List<TestCase> testCases, CompilationResult result, long startTime) {
        StringBuilder results = new StringBuilder();

        for (TestCase testCase : testCases) {
            int input = testCase.getInput();
            int expectedOutput = testCase.getExpectedOutput();

            // Execute the code with the input
            try {
                ProcessBuilder processBuilder = new ProcessBuilder("java", className, String.valueOf(input));
                processBuilder.redirectErrorStream(true);
                Process process = processBuilder.start();
                
                // Capture output
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                StringBuilder output = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
                
                int exitCode = process.waitFor();
                if (exitCode == 0) {
                    int actualOutput = Integer.parseInt(output.toString().trim());
                    if (actualOutput == expectedOutput) {
                        results.append("Test case with input ").append(input).append(" passed.\n");
                    } else {
                        results.append("Test case with input ").append(input).append(" failed: expected ")
                               .append(expectedOutput).append(", got ").append(actualOutput).append("\n");
                    }
                } else {
                    results.append("Execution failed with exit code: ").append(exitCode).append("\n");
                }
            } catch (IOException | InterruptedException e) {
                results.append("Error executing code: ").append(e.getMessage()).append("\n");
            }
        }

        result.setOutput(results.toString());
        result.setExecutionTime(System.currentTimeMillis() - startTime);
        result.setSuccess(true); // Set success to true if all tests pass
        return result;
    }
}
