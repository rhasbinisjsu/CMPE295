import logo from './logo.svg';
import './App.css';

import {BrowserRouter, Route, Switch} from 'react-router-dom';
import React from 'react';
import './index.css';


import Homepage from './Components/Homepage';
import UserLogin from './Components/UserLogin';
import UserSignup from './Components/UserSignUp';
import UserDashboard from './Components/UserDashboard';

// function App() {
//   return (
//     <div className="App">
//       <header className="App-header">
//         <img src={logo} className="App-logo" alt="logo" />
//         <p>
//           Edit <code>src/App.js</code> and save to reloadaaaaaaaaa.
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

function App(){
  return(
    <div>
      <BrowserRouter>
      <Switch>
      <Route exact path="/" component={Homepage}/>
      <Route exact path="/userLogin" component={UserLogin}/>
      <Route exact path="/userSignUp" component={UserSignup}/>
      <Route exact path="/userDashboard" component={UserDashboard}/>
      </Switch>
      </BrowserRouter>
    </div>
  );
}

export default App;
