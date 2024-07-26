// import React, { Component } from 'react'
// import axios from 'axios';


// export default class UserLogin extends React.Component {
//     constructor(props){
//     super(props);
  
//     this.state = {
//       username : '',
//       password : '',
//     }
//     this.handleInputChange = this.handleInputChange.bind(this);
  
//   }

//   handleInputChange(event) {
//     const {name, value} = event.target;
//     this.state[name] = value;
//     console.log(this.state)
//   }

// //   handleButtonClicked = async (id) =>{
// //     let body = this.state

// //     //add api call here
// //     //await axios.post/get

//     render() {
        
//         return(
//           <div className='background'>
//             <div>
//               <form>
//                 <h1>Sign In</h1>
    
//                 <h3>
//                       <label className='username'>Username: </label>
//                       <input className='inputusername' type="text" name="username" onChange={this.handleInputChange}/>
//                   </h3>
//                   <h3>
//                       <label className='password'>Password: </label>
//                       <input className='inputpassword' type="text" name="password" onChange={this.handleInputChange}/>
//                   </h3>
//               </form>
//               {/* <button onClick={this.handleButtonClicked.bind(this)}>Submit</button> */}
//               <button>Submit</button>
//             </div>
//           </div>
//         )
//       }
//     }

// //   }


//-----------------------------------------------------

// import React, { useState } from "react";
// import axios from 'axios';
// import { useHistory } from 'react-router-dom';
// import Sidebar from "./Sidebar";

// function UserLogin() {
//   const [formData, setFormData] = useState({
//     uname: '',
//     pswd: ''
//   });
//   const [error, setError] = useState('');
//   const history = useHistory();

//   const handleChange = (e) => {
//     const { name, value } = e.target;
//     setFormData({
//       ...formData,
//       [name]: value
//     });
//   };

//   const handleSubmit = async (e) => {
//     e.preventDefault();
//     const { uname, pswd } = formData;

//     try {
//       // Perform login API call
//       const response = await axios.get(`http://localhost:8080/CropSense/AppServer/UserController/login?uname=${uname}&pswd=${pswd}`);
//       console.log('Login successful:', response.data);

//       // Redirect to home page after successful login
//       history.push('/');
//     } catch (error) {
//       console.error('Error logging in:', error);
//       setError('Invalid username or password. Please try again.');
//     }
//   };

//   const ErrorPopup = ({ message }) => (
//     <div className="fixed top-0 left-0 w-full h-full flex items-center justify-center">
//       <div className="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4">
//         <p className="text-red-500 text-sm italic">{message}</p>
//         <button
//           className="bg-indigo-500 hover:bg-indigo-700 text-white font-bold py-2 px-4 rounded mt-4 focus:outline-none focus:shadow-outline"
//           onClick={() => setError('')}
//         >
//           Close
//         </button>
//       </div>
//     </div>
//   );

//   return (
//     <div className="flex h-screen">
//       <Sidebar />
//       <div className="flex-1 flex justify-center items-center">
//         {error && <ErrorPopup message={error} />}
//         <form onSubmit={handleSubmit} className="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4 w-1/2">
//           <h2 className="text-2xl font-bold mb-6">User Login</h2>
//           <div className="mb-4">
//             <label className="block text-gray-700 text-sm font-bold mb-2">Username</label>
//             <input
//               type="text"
//               name="uname"
//               value={formData.uname}
//               onChange={handleChange}
//               className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
//             />
//           </div>
//           <div className="mb-6">
//             <label className="block text-gray-700 text-sm font-bold mb-2">Password</label>
//             <input
//               type="password"
//               name="pswd"
//               value={formData.pswd}
//               onChange={handleChange}
//               className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
//             />
//           </div>
//           <div className="flex items-center justify-between">
//             <button
//               type="submit"
//               className="bg-indigo-500 hover:bg-indigo-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
//             >
//               Login
//             </button>
//           </div>
//         </form>
//       </div>
//     </div>
//   );
// }

// export default UserLogin;


//------------------------------------------------

// import React, { useState } from "react";
// import axios from 'axios';
// import { useHistory } from 'react-router-dom';
// import Sidebar from "./Sidebar";

// function UserLogin() {
//   const [formData, setFormData] = useState({
//     uname: '',
//     pswd: ''
//   });
//   const [error, setError] = useState('');
//   const history = useHistory();

//   const handleChange = (e) => {
//     const { name, value } = e.target;
//     setFormData({
//       ...formData,
//       [name]: value
//     });
//   };

//   const handleSubmit = async (e) => {
//     e.preventDefault();
//     const { uname, pswd } = formData;

//     try {
//       // Perform login API call
//       const response = await axios.get(`http://localhost:8080/CropSense/AppServer/UserController/login?uname=${uname}&pswd=${pswd}`);
//       console.log('Login successful:', response.data);

//       // Update sessionStorage with user information
//       sessionStorage.setItem('userId', response.data.userId);
//       sessionStorage.setItem('username', uname);

//       // Redirect to home page after successful login
//       history.push('/');
//     } catch (error) {
//       console.error('Error logging in:', error);
//       setError('Invalid username or password. Please try again.');
//     }
//   };

//   const ErrorPopup = ({ message }) => (
//     <div className="fixed top-0 left-0 w-full h-full flex items-center justify-center">
//       <div className="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4">
//         <p className="text-red-500 text-sm italic">{message}</p>
//         <button
//           className="bg-indigo-500 hover:bg-indigo-700 text-white font-bold py-2 px-4 rounded mt-4 focus:outline-none focus:shadow-outline"
//           onClick={() => setError('')}
//         >
//           Close
//         </button>
//       </div>
//     </div>
//   );

//   return (
//     <div className="flex h-screen">
//       <Sidebar />
//       <div className="flex-1 flex justify-center items-center">
//         {error && <ErrorPopup message={error} />}
//         <form onSubmit={handleSubmit} className="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4 w-1/2">
//           <h2 className="text-2xl font-bold mb-6">User Login</h2>
//           <div className="mb-4">
//             <label className="block text-gray-700 text-sm font-bold mb-2">Username</label>
//             <input
//               type="text"
//               name="uname"
//               value={formData.uname}
//               onChange={handleChange}
//               className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
//             />
//           </div>
//           <div className="mb-6">
//             <label className="block text-gray-700 text-sm font-bold mb-2">Password</label>
//             <input
//               type="password"
//               name="pswd"
//               value={formData.pswd}
//               onChange={handleChange}
//               className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
//             />
//           </div>
//           <div className="flex items-center justify-between">
//             <button
//               type="submit"
//               className="bg-indigo-500 hover:bg-indigo-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
//             >
//               Login
//             </button>
//           </div>
//         </form>
//       </div>
//     </div>
//   );
// }

// export default UserLogin;


//--------------------------------------------------------
// import React, { useState } from "react";
// import axios from 'axios';
// import { useHistory } from 'react-router-dom';
// import Sidebar from "./Sidebar";

// function UserLogin() {
//   const [formData, setFormData] = useState({
//     uname: '',
//     pswd: ''
//   });
//   const [error, setError] = useState('');
//   const history = useHistory();

//   const handleChange = (e) => {
//     const { name, value } = e.target;
//     setFormData({
//       ...formData,
//       [name]: value
//     });
//   };

//   const handleSubmit = async (e) => {
//     e.preventDefault();
//     const { uname, pswd } = formData;

//     try {
//       // Perform login API call
//       const response = await axios.get(`http://localhost:8080/CropSense/AppServer/UserController/login?uname=${uname}&pswd=${pswd}`);
//       console.log('Login successful:', response.data);

//       // Update sessionStorage with user information
//       sessionStorage.setItem('userId', response.data.userId); // Assuming the API response has userId
//       sessionStorage.setItem('username', uname);

//       // Redirect to home page after successful login
//       history.push('/');
//     } catch (error) {
//       console.error('Error logging in:', error);
//       setError('Invalid username or password. Please try again.');
//     }
//   };

//   const ErrorPopup = ({ message }) => (
//     <div className="fixed top-0 left-0 w-full h-full flex items-center justify-center">
//       <div className="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4">
//         <p className="text-red-500 text-sm italic">{message}</p>
//         <button
//           className="bg-indigo-500 hover:bg-indigo-700 text-white font-bold py-2 px-4 rounded mt-4 focus:outline-none focus:shadow-outline"
//           onClick={() => setError('')}
//         >
//           Close
//         </button>
//       </div>
//     </div>
//   );

//   return (
//     <div className="flex h-screen">
//       <Sidebar />
//       <div className="flex-1 flex justify-center items-center">
//         {error && <ErrorPopup message={error} />}
//         <form onSubmit={handleSubmit} className="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4 w-1/2">
//           <h2 className="text-2xl font-bold mb-6">User Login</h2>
//           <div className="mb-4">
//             <label className="block text-gray-700 text-sm font-bold mb-2">Username</label>
//             <input
//               type="text"
//               name="uname"
//               value={formData.uname}
//               onChange={handleChange}
//               className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
//             />
//           </div>
//           <div className="mb-6">
//             <label className="block text-gray-700 text-sm font-bold mb-2">Password</label>
//             <input
//               type="password"
//               name="pswd"
//               value={formData.pswd}
//               onChange={handleChange}
//               className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
//             />
//           </div>
//           <div className="flex items-center justify-between">
//             <button
//               type="submit"
//               className="bg-indigo-500 hover:bg-indigo-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
//             >
//               Login
//             </button>
//           </div>
//         </form>
//       </div>
//     </div>
//   );
// }

// export default UserLogin;

import React, { useState } from "react";
import axios from 'axios';
import { useHistory } from 'react-router-dom';
import Sidebar from "./Sidebar";

function UserLogin() {
  const [formData, setFormData] = useState({
    uname: '',
    pswd: ''
  });
  const [error, setError] = useState('');
  const history = useHistory();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const { uname, pswd } = formData;

    try {
      // Perform login API call
      const response = await axios.get(`http://localhost:8080/CropSense/AppServer/UserController/login?uname=${uname}&pswd=${pswd}`);
      console.log('Login successful. User ID:', response.data);

      // Update sessionStorage with user ID
      sessionStorage.setItem('userId', response.data); // Assuming response.data is the user ID

      // Redirect to home page after successful login
      history.push('/');
    } catch (error) {
      console.error('Error logging in:', error);
      setError('Invalid username or password. Please try again.');
    }
  };

  const ErrorPopup = ({ message }) => (
    <div className="fixed top-0 left-0 w-full h-full flex items-center justify-center">
      <div className="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4">
        <p className="text-red-500 text-sm italic">{message}</p>
        <button
          className="bg-indigo-500 hover:bg-indigo-700 text-white font-bold py-2 px-4 rounded mt-4 focus:outline-none focus:shadow-outline"
          onClick={() => setError('')}
        >
          Close
        </button>
      </div>
    </div>
  );

  return (
    <div className="flex h-screen">
      <div className="flex-1 flex justify-center items-center">
        {error && <ErrorPopup message={error} />}
        <form onSubmit={handleSubmit} className="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4 w-1/2">
          <h2 className="text-2xl font-bold mb-6">User Login</h2>
          <div className="mb-4">
            <label className="block text-gray-700 text-sm font-bold mb-2">Username</label>
            <input
              type="text"
              name="uname"
              value={formData.uname}
              onChange={handleChange}
              className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            />
          </div>
          <div className="mb-6">
            <label className="block text-gray-700 text-sm font-bold mb-2">Password</label>
            <input
              type="password"
              name="pswd"
              value={formData.pswd}
              onChange={handleChange}
              className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            />
          </div>
          <div className="flex items-center justify-between">
            <button
              type="submit"
              className="bg-indigo-500 hover:bg-indigo-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
            >
              Login
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}

export default UserLogin;
