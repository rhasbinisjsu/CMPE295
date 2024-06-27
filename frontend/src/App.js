import logo from './logo.svg';
import './App.css';

import {BrowserRouter, Route, Switch} from 'react-router-dom';
import React from 'react';
import './index.css';


import Homepage from './Components/Homepage';
import UserLogin from './Components/UserLogin';
import UserSignup from './Components/UserSignUp';
import UserDashboard from './Components/UserDashboard';
import Farm from './Components/Farm';
import Land from './Components/Land';
import Crop from './Components/Crop';
import Metrics from './Components/Metrics';
import Mission from './Components/Mission';




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
        <Route exact path="/Farm" component={Farm}/>
        <Route exact path="/Land" component={Land}/>
        <Route exact path="/Crop" component={Crop}/>
        <Route exact path="/Metrics" component={Metrics}/>
          <Route exact path="/Mission" component={Mission}/>
      <Route exact path="/userDashboard" component={UserDashboard}/>
      </Switch>
      </BrowserRouter>
    </div>
  );
}

export default App;
