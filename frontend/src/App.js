// import logo from './logo.svg';
// import './App.css';

// import {BrowserRouter, Route, Switch} from 'react-router-dom';
// import React from 'react';
// import './index.css';


// import Homepage from './Components/Homepage';
// import UserLogin from './Components/UserLogin';
// import UserSignup from './Components/UserSignUp';
// import UserDashboard from './Components/UserDashboard';
// import Farm from './Components/Farm';
// import Mission from './Components/Mission';
// import Header from './Components/Header';
// import Crops from './Components/Crops';
// import FarmDetail from './Components/FarmDetail';
// import Sidebar from "./Components/Sidebar";
// import CropDetail from './Components/CropDetail';

// function App(){
// return (
//     <div className="flex flex-col h-screen">
//       <Header />
//       <BrowserRouter>
//           <div className="flex flex-1">
//             <Sidebar />
//             <main className="flex-1 overflow-auto p-4">
//               <Switch>
//               <Route exact path="/" component={Homepage}/>
//               <Route exact path="/userLogin" component={UserLogin}/>
//               <Route exact path="/userSignUp" component={UserSignup}/>
//               <Route exact path="/Farm" component={Farm}/>
//               <Route exact path="/Mission" component={Mission}/>
//               <Route exact path="/userDashboard" component={UserDashboard}/>
//               <Route exact path="/crops"component={Crops}/>
//               <Route exact path="/farmDetail/:id" component={FarmDetail} />
//               <Route exact path="/CropDetail" component={CropDetail} />
//               </Switch>
//               </main>
//             </div>
//             </BrowserRouter>
//     </div>
    
//   );
// }

// export default App;


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
            <Route path="/Crop" component={Crops} />
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