import React from 'react';
import Editor from "@monaco-editor/react";
import '../styles/CodeEditor.css'; 

function CodeEditor({ onChange, value }) {
  const handleEditorChange = (value) => {
    onChange(value);
  };

  return (
    <Editor
      height="70vh"
      defaultLanguage="java"
      defaultValue={`public class Solution {
    public static void main(String[] args) {
        // Write your code here
    }
}`}
      theme="vs-dark"
      value={value}
      onChange={handleEditorChange}
      options={{
        minimap: { enabled: false },
        fontSize: 14,
        lineNumbers: 'on',
        scrollBeyondLastLine: false,
        automaticLayout: true,
        tabSize: 4,
        formatOnPaste: true,
        formatOnType: true,
        autoIndent: 'full',
        snippetSuggestions: 'inline'
      }}
      loading={<div>Loading editor...</div>}
    />
  );
}

export default CodeEditor;