import React from 'react';
import { BrowserRouter, Route, Switch } from 'react-router-dom';
import './App.css';
import './index.css';

import Homepage from './Components/Homepage';
import UserLogin from './Components/UserLogin';
import UserSignup from './Components/UserSignUp';
import UserDashboard from './Components/UserDashboard';
import Farm from './Components/Farm';
import Crop from './Components/Crop';
import Mission from './Components/Mission';
import Farm1 from './Components/Farm1';
import Header from './Components/Header';
import Sidebar from './Components/Sidebar';

function App() {
  return (
    <BrowserRouter>
      <div className="flex flex-col h-screen">
        <Header />
        <div className="flex flex-1">
          <Sidebar />
          <main className="flex-1 overflow-auto p-4">
            <Switch>
              <Route exact path="/" component={Homepage} />
              <Route exact path="/userLogin" component={UserLogin} />
              <Route exact path="/userSignUp" component={UserSignup} />
              <Route exact path="/Farm" component={Farm} />
              <Route exact path="/Crop" component={Crop} />
              <Route exact path="/Mission" component={Mission} />
              <Route exact path="/userDashboard" component={UserDashboard} />
              <Route exact path="/Farm1" component={Farm1} />
            </Switch>
          </main>
        </div>
      </div>
    </BrowserRouter>
  );
}

export default App;
