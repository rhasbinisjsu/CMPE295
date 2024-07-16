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
import Crop from './Components/Crop';
import Mission from './Components/Mission';
// import Farm1 from './Components/Farm1';
import Header from './Components/Header';




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
      <div className="flex flex-col h-screen">
        <Header />
        <main className="flex-1 overflow-auto p-4">
             <div>
              <BrowserRouter>
              <Switch>
              <Route exact path="/" component={Homepage}/>
              <Route exact path="/userLogin" component={UserLogin}/>
              <Route exact path="/userSignUp" component={UserSignup}/>
              <Route exact path="/Farm" component={Farm}/>
              <Route exact path="/Crop" component={Crop}/>
              <Route exact path="/Mission" component={Mission}/>
              <Route exact path="/userDashboard" component={UserDashboard}/>
              </Switch>
              </BrowserRouter>
            </div>
        </main>
    </div>

  );
}

// <Route exact path="/Farm1" component={Farm1}/>
export default App;
