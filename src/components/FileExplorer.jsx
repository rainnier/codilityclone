import React from 'react';
import '../styles/FileExplorer.css';

function FileExplorer({ files, onFileSelect, selectedFile }) {
  return (
    <div className="file-explorer">
      <h3 className="file-explorer-title">Files</h3>
      <div className="file-list">
        {files.map((file) => (
          <div
            key={file.id}
            className={`file-item ${selectedFile?.id === file.id ? 'selected' : ''}`}
            onClick={() => onFileSelect(file)}
          >
            <span className="file-icon">📄</span>
            <span className="file-name">{file.name}</span>
          </div>
        ))}
      </div>
    </div>
  );
}

export default FileExplorer;
