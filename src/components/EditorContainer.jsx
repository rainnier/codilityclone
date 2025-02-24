import React, { useState } from 'react';
import CodeEditor from './CodeEditor';
import FileExplorer from './FileExplorer';
import '../styles/EditorContainer.css';

function EditorContainer() {
  const [code, setCode] = useState('');
  const [output, setOutput] = useState('');
  const [selectedFile, setSelectedFile] = useState(null);
  const [files, setFiles] = useState([
    {
      id: 1,
      name: 'BinaryGap.java',
      clName: 'BinaryGap',
      type: 'java',
      content: `public class BinaryGap {
    public int solution(int N) {
        // write your code here
        
        // Example: For N = 1041 (10000010001 in binary)
        // Should return 5 because the longest binary gap is 5
        return 0;
    }
}`
    },
    {
      id: 2,
      name: 'TestCases.java',
      clName: 'TestCases',
      type: 'java',
      content: `import org.junit.Test;
import static org.junit.Assert.*;

public class TestCases {
    @Test
    public void testBinaryGap() {
        BinaryGap solution = new BinaryGap();
        
        assertEquals(5, solution.solution(1041));  // 10000010001 in binary
        assertEquals(0, solution.solution(15));    // 1111 in binary
        assertEquals(2, solution.solution(9));     // 1001 in binary
        assertEquals(4, solution.solution(529));   // 1000010001 in binary
        assertEquals(1, solution.solution(20));    // 10100 in binary
        assertEquals(0, solution.solution(32));    // 100000 in binary
    }
}`
    },
    {
      id: 3,
      name: 'README.md',
      clName: 'README',
      type: 'markdown',
      content: `# Binary Gap

A binary gap within a positive integer N is any maximal sequence of consecutive zeros that is surrounded by ones at both ends in the binary representation of N.

For example:
- Number 1041 has binary representation 10000010001 and contains a binary gap of length 5
- Number 15 has binary representation 1111 and has no binary gap
- Number 32 has binary representation 100000 and has no binary gap

## Task Description

Write a function:
\`\`\`java
public int solution(int N)
\`\`\`

that, given a positive integer N, returns the length of its longest binary gap. The function should return 0 if N doesn't contain a binary gap.

## Examples

1. Given N = 1041, the function should return 5, because N has binary representation 10000010001 and its longest binary gap is of length 5.

2. Given N = 15, the function should return 0, because N has binary representation 1111 and has no binary gaps.

3. Given N = 32, the function should return 0, because N has binary representation 100000 and has no binary gaps.

## Constraints
- N is an integer within the range [1..2,147,483,647]

## Expected Time Complexity
- O(log N)

## Solution Template
\`\`\`java
public int solution(int N) {
    // write your code here
    return 0;
}
\`\`\`
`
    },
    {
      id: 4,
      name: 'Solution.java',
      clName: 'Solution',
      type: 'java',
      content: `public class Solution {
    // Example solution - hidden from candidates
    public int solution(int N) {
        // Convert to binary string
        String binary = Integer.toBinaryString(N);
        
        int maxGap = 0;
        int currentGap = 0;
        boolean counting = false;
        
        for (char c : binary.toCharArray()) {
            if (c == '1') {
                if (counting) {
                    maxGap = Math.max(maxGap, currentGap);
                }
                counting = true;
                currentGap = 0;
            } else if (counting) {
                currentGap++;
            }
        }
        
        return maxGap;
    }
}`
    },
    {
      id: 5,
      name: 'TesterClass.java',
      clName: 'TesterClass',
      type: 'java',
      content: `public class TesterClass {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java TesterClass <ClassName> <MethodName> <MethodArgs>");
            return;
        }

        String className = args[0]; // The name of the class to instantiate
        String methodName = args[1]; // The name of the method to call

        try {
            // Load the class dynamically
            Class<?> clazz = Class.forName(className);
            // Create an instance of the class
            Object instance = clazz.getDeclaredConstructor().newInstance();

            // Prepare method arguments (if any)
            Object[] methodArgs = new Object[args.length - 2];
            for (int i = 2; i < args.length; i++) {
                methodArgs[i - 2] = Integer.parseInt(args[i]); // Assuming method takes int arguments
            }

            // Find the method with the specified name and parameter types
            Class<?>[] paramTypes = new Class[methodArgs.length];
            for (int i = 0; i < methodArgs.length; i++) {
                paramTypes[i] = methodArgs[i].getClass();
            }

            // Get the method to invoke
            java.lang.reflect.Method method = clazz.getMethod(methodName, paramTypes);

            // Invoke the method and print the result
            Object result = method.invoke(instance, methodArgs);
            System.out.println("Result: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}`
    }
  ]);

  const handleCodeChange = (newCode) => {
    setCode(newCode);
    if (selectedFile) {
      selectedFile.content = newCode;
    }
  };

  const handleFileSelect = (file) => {
    setSelectedFile(file);
    setCode(file.content);
  };

  const handleSubmit = async () => {
    // Define test cases based on TestCases.java
    const testCases = {
        9: 2,   // Example test case
        32: 0,
        1041: 5,
        // Add more test cases as needed
    };

    try {
        const response = await fetch('http://localhost:8080/api/code/submit', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ code, className: selectedFile.clName, testCases }), // Include test cases
        });

        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        const result = await response.json();
        setOutput(result.output || result.error); // Display output or error
    } catch (error) {
        setOutput('Error submitting code: ' + error.message);
    }
  };

  return (
    <div className="editor-container">
      <FileExplorer 
        files={files}
        onFileSelect={handleFileSelect}
        selectedFile={selectedFile}
      />
      <div className="editor-main">
        <div className="editor-wrapper">
          <CodeEditor 
            value={code} 
            onChange={handleCodeChange} 
          />
        </div>
        <div className="controls">
          <button 
            onClick={handleSubmit}
            className="submit-button"
          >
            Run Code
          </button>
        </div>
        {output && (
          <div className="output-panel">
            <h3>Output:</h3>
            <pre>{output}</pre>
          </div>
        )}
      </div>
    </div>
  );
}

export default EditorContainer;