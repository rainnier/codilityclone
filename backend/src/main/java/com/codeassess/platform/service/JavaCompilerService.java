package com.codeassess.platform.service;

import com.codeassess.platform.model.CodeSubmission;
import com.codeassess.platform.model.CompilationResult;
import com.codeassess.platform.model.TestCase;
import org.springframework.stereotype.Service;

import javax.tools.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JavaCompilerService {

    private static final String SAVE_DIRECTORY = "compiled"; // Directory to save compiled files

    public CompilationResult compileAndRun(CodeSubmission submission) {
        
        CompilationResult result = new CompilationResult();
        long startTime = System.currentTimeMillis();

        String code = submission.getCode();
        String className = submission.getClassName();
        String fileName = className + ".java";
        Path savePath = Paths.get(SAVE_DIRECTORY, fileName);

         // Create the directory if it doesn't exist
        try {
            Files.createDirectories(Paths.get(SAVE_DIRECTORY));
        } catch (IOException e) {
            result.setSuccess(false);
            result.setError("Error creating directory: " + e.getMessage());
            return result;
        }

        // Save the submitted code to a file named fileName
        try (PrintWriter writer = new PrintWriter(savePath.toFile())) {
            writer.write(code);
        } catch (FileNotFoundException e) {
            result.setSuccess(false);
            result.setError("Error saving file: " + e.getMessage());
            return result;
        }

        // Compile the file
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        
        // Specify the output directory for compiled classes
        String outputDirectory = SAVE_DIRECTORY;
        String classpath = "compiled;compiled/junit-4.13.2.jar;compiled/hamcrest-2.2.jar;compiled/hamcrest-library-2.2.jar";
        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjects(savePath.toFile());
        
        // Set the compilation options to specify the output directory
        List<String> options = List.of("-d", outputDirectory, "-classpath", classpath);
        boolean success = compiler.getTask(null, fileManager, null, options, null, compilationUnits).call();
        
        if (!success) {
            System.out.println("Fail compilation for: " + savePath);
            result.setSuccess(false);
            result.setError("Compilation failed.");
            return result;
        } else {
            System.out.println("Success compilation for: " + savePath);
        }

        // Convert the Map<Integer, Integer> to List<TestCase>
        List<TestCase> testCases = submission.getTestCases().entrySet().stream()
            .map(entry -> new TestCase(entry.getKey(), entry.getValue()))
            .collect(Collectors.toList());

        // Call runChecker to execute the code and run test cases
        // return runChecker(className, testCases, result, startTime);
        return runJUnitTests(result, startTime);
    }

    private CompilationResult runChecker(String className, List<TestCase> testCases, CompilationResult result, long startTime) {
        StringBuilder results = new StringBuilder();

        for (TestCase testCase : testCases) {
            int input = testCase.getInput();
            System.out.println("Input: " + input);
            int expectedOutput = testCase.getExpectedOutput();
            System.out.println("Expected Output: " + expectedOutput);

            // Execute the code with the input
            try {
                // Ensure the compiled directory is included in the classpath
                String classpath = "compiled;compiled/junit-4.13.2.jar;compiled/hamcrest-2.2.jar;compiled/hamcrest-library-2.2.jar"; // Use the directory where the class is saved
                ProcessBuilder processBuilder = new ProcessBuilder("java", "-cp", classpath, className, String.valueOf(input));
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
                    results.append("Executioner failed with exit code: ").append(exitCode).append("\n");
                }
            } catch (IOException e) {
                System.err.println("io");
                e.printStackTrace();
                results.append("Error executing code: ").append(e.getMessage()).append("\n");
            } catch(InterruptedException e) {
                System.err.println("interrupted");
                e.printStackTrace();
            
        }catch(Exception e){
                System.err.println("exception");
                e.printStackTrace();
            }
        }

        result.setOutput(results.toString());
        result.setExecutionTime(System.currentTimeMillis() - startTime);
        result.setSuccess(true); // Set success to true if all tests pass
        return result;
    }

    private CompilationResult runJUnitTests(CompilationResult result, long startTime) {
        StringBuilder results = new StringBuilder();
        boolean allTestsPassed = true; // Track if all tests pass

        // Specify the JUnit test class name
        String testClassName = "TestCases"; // Change this to your actual test class name

        try {
            // Ensure the compiled directory is included in the classpath
            // Build the classpath
        String classpath = "compiled;compiled/junit-4.13.2.jar;compiled/hamcrest-2.2.jar;compiled/hamcrest-library-2.2.jar";// Adjust paths as necessary
        ProcessBuilder processBuilder = new ProcessBuilder("java", "-cp", classpath, "org.junit.runner.JUnitCore", testClassName);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            
            
            // Capture output
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                results.append(line).append("\n");
            }
            
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                results.append("All JUnit tests passed.\n");
            } else {
                results.append("JUnit tests failed with exit code: ").append(exitCode).append("\n");
                allTestsPassed = false; // Mark as failed
            }
        } catch (IOException e) {
            System.err.println("io");
            e.printStackTrace();
            results.append("Error executing JUnit tests: ").append(e.getMessage()).append("\n");
            allTestsPassed = false; // Mark as failed
        } catch (InterruptedException e) {
            System.err.println("interrupted");
            e.printStackTrace();
            allTestsPassed = false; // Mark as failed
        }

        result.setOutput(results.toString());
        result.setExecutionTime(System.currentTimeMillis() - startTime);
        result.setSuccess(allTestsPassed); // Set success based on test results
        return result;
    }
}
