import React from 'react';
import EditorContainer from './components/EditorContainer';
import './App.css';
import MessageComponent from './components/MessageComponent';

function App() {
  return (
    <div className="app">
      <header className="app-header">
        <h1>Java Code Assessment Platform</h1>
      </header>
      <main className="app-main">
        <EditorContainer />
        <MessageComponent />
      </main>
    </div>
  );
}

export default App;