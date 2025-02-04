package com.codeassess.platform.controller;

import com.codeassess.platform.model.CodeSubmission;
import com.codeassess.platform.model.CompilationResult;
import com.codeassess.platform.service.JavaCompilerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/code")
@CrossOrigin(origins = "http://localhost:3000")
public class CodeController {
    
    @Autowired
    private JavaCompilerService javaCompilerService;
    
    @PostMapping("/submit")
    public CompilationResult submitCode(@RequestBody CodeSubmission submission) {
        return javaCompilerService.compileAndRun(submission);
    }
}
