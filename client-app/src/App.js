// import logo from './logo.svg';
// import './App.css';
//
// function App() {
//   return (
//     <div className="App">
//       <header className="App-header">
//         <img src={logo} className="App-logo" alt="logo" />
//         <p>
//           Edit <code>src/App.js</code> and save to reload.
//         </p>
//         <a
//           className="App-link"
//           href="https://reactjs.org"
//           target="_blank"
//           rel="noopener noreferrer"
//         >
//           Learn React
//         </a>
//       </header>
//     </div>
//   );
// }
//
// export default App;

import React from "react";

function App() {
  const handleLogin = () => {
    window.location.href = "http://localhost:8080/oauth2/authorize";
  };

  return (
      <div className="App">
        <h1>Welcome to AuthVerse</h1>
        <button onClick={handleLogin}>Login with OAuth</button>
      </div>
  );
}

export default App;