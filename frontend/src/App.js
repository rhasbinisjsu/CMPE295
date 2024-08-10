import logo from './logo.svg';
import './App.css';

import {BrowserRouter, Route, Switch, useLocation} from 'react-router-dom';
import React from 'react';
import './index.css';


import Homepage from './Components/Homepage';
import UserLogin from './Components/UserLogin';
import UserSignup from './Components/UserSignUp';
import UserDashboard from './Components/UserDashboard';
import Farm from './Components/Farm';
import Crops from './Components/Crops';
import Mission from './Components/Mission';
import Header from './Components/Header';
import FarmDetail from './Components/FarmDetail';
import Sidebar from "./Components/Sidebar";
import CropDetail from './Components/CropDetail';



function AppContent() {
  const location = useLocation();
  const isAuthPage = location.pathname === '/' || location.pathname === '/userLogin' || location.pathname === '/userSignUp';

  return (
    <div className="flex flex-col h-screen">
      {!isAuthPage && <Header />}
      <div className="flex flex-1">
        {!isAuthPage && <Sidebar />}
        <main className="flex-1 overflow-auto p-4">
          <Switch>
            <Route exact path="/" component={Homepage} />
            <Route path="/userLogin" component={UserLogin} />
            <Route path="/userSignUp" component={UserSignup} />
            <Route path="/Farm" component={Farm} />
            <Route path="/Crops" component={Crops} />
            <Route path="/Mission" component={Mission} />
            <Route path="/userDashboard" component={UserDashboard} />
            <Route path="/farmDetail/:id" component={FarmDetail} />
            <Route path="/CropDetail" component={CropDetail} />
          </Switch>
        </main>
      </div>
    </div>
  );
}

function App() {
  return (
    <BrowserRouter>
      <AppContent />
    </BrowserRouter>
  );
}
export default App;