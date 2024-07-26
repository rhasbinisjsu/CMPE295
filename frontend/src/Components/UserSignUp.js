// import React, { Component, useState } from 'react'
// import axios from 'axios';

// import Sidebar from "./Sidebar";

// export default class UserSignup extends React.Component {
//     //Setting the state for the initial launch
//     constructor(props){
//         super(props);
//         this.state= {
//             basicDetails: {
//                 firstName:'',
//                 lastName:'',
//                 email:'',
//                 phoneNumber:'',
//                 gender:'',
//                 dateOfBirth:'',
//                 role:'',
//                 userName:'',
//                 password:'',
//                 dateCreated: new Date()
//             },
//         };
//         this.handleInputChange = this.handleInputChange.bind(this);
//     }

//     //On input change, it updates the state to the new value
//     handleInputChange(event) {
//         const {name, value} = event.target;
//         this.state[name] = value;
//         this.state.basicDetails[name] = value;
//         console.log(this.state)
//     }

//     render() {
//         return(
//            <div>
//             <h1>Sign Up for User</h1>
//             <form>
//                 <h3>
//                     <label className='firstName'>First Name: </label>
//                     <input className='inputfirstname' type="text" name="firstName" onChange={this.handleInputChange}/>
//                 </h3>
//                 <h3>
//                     <label className='lastName'>Last Name: </label>
//                     <input className='inputlastname' type="text" name="lastName" onChange={this.handleInputChange}/>
//                 </h3>
//                 <h3>
//                     <label className='email'>Email: </label>
//                     <input className='inputemail' type="text" name="email" onChange={this.handleInputChange}/>
//                 </h3>
//                 <h3>
//                     <label className='phoneNumber'>Phone Number: </label>
//                     <input className='inputphonenumber' type="text" name="phoneNumber" onChange={this.handleInputChange}/>
//                 </h3>
//                 <h3>
//                     <label className='dateOfBirth'>Date Of Birth: </label>
//                     <input className='inputdateofbirth' type="text" name="dateOfBirth" onChange={this.handleInputChange}/>
//                 </h3>
//                 <h3>
//                     <label className='userName'>Username: </label>
//                     <input className='inputusername' type="text" name="userName" onChange={this.handleInputChange}/>
//                 </h3>
//                 <h3>
//                     <label className='password'>Password: </label>
//                     <input className='inputpassword' type="text" name="password" onChange={this.handleInputChange}/>
//                 </h3>

                
//             </form>
//             {/* <button onClick={this.handleButtonClicked.bind(this)}>Submit</button> */}
//             <button>Submit</button>
//            </div>
//         )
//       }
//     }


//-----------------------------


// import React, { useState } from "react";
// import axios from 'axios';
// import Sidebar from "./Sidebar";
// import { useHistory } from 'react-router-dom';

// function UserSignUp() {
//   const [formData, setFormData] = useState({
//     uname: '',
//     pswd: '',
//     email: '',
//     fname: '',
//     lname: '',
//     pnumber: '',
//   });

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
//     const newUser = {
//       uname: formData.uname,
//       pswd: formData.pswd,
//       email: formData.email,
//       name: {
//         fname: formData.fname,
//         lname: formData.lname
//       },
//       pnumber: formData.pnumber
//     };

//     try {
//       const response = await axios.post('http://localhost:8080/CropSense/AppServer/UserController/createUser', newUser);
//       console.log('User created successfully:', response.data);
//       history.push('/');
//       // Optionally, redirect to a new page or show a success message
//     } catch (error) {
//       console.error('Error creating user:', error);
//       // Handle error: show an error message to the user
//     }
//   };

//   return (
//     <div className="flex h-screen">
//       <Sidebar />
//       <div className="flex-1 flex justify-center items-center">
//         <form onSubmit={handleSubmit} className="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4 w-1/2">
//           <h2 className="text-2xl font-bold mb-6">User Signup</h2>
//           <div className="mb-4">
//             <label className="block text-gray-700 text-sm font-bold mb-2">
//               Username
//             </label>
//             <input
//               type="text"
//               name="uname"
//               value={formData.uname}
//               onChange={handleChange}
//               className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
//               required
//             />
//           </div>
//           <div className="mb-4">
//             <label className="block text-gray-700 text-sm font-bold mb-2">
//               Password
//             </label>
//             <input
//               type="password"
//               name="pswd"
//               value={formData.pswd}
//               onChange={handleChange}
//               className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
//               required
//             />
//           </div>
//           <div className="mb-4">
//             <label className="block text-gray-700 text-sm font-bold mb-2">
//               Email
//             </label>
//             <input
//               type="email"
//               name="email"
//               value={formData.email}
//               onChange={handleChange}
//               className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
//               required
//             />
//           </div>
//           <div className="mb-4">
//             <label className="block text-gray-700 text-sm font-bold mb-2">
//               First Name
//             </label>
//             <input
//               type="text"
//               name="fname"
//               value={formData.fname}
//               onChange={handleChange}
//               className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
//               required
//             />
//           </div>
//           <div className="mb-4">
//             <label className="block text-gray-700 text-sm font-bold mb-2">
//               Last Name
//             </label>
//             <input
//               type="text"
//               name="lname"
//               value={formData.lname}
//               onChange={handleChange}
//               className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
//               required
//             />
//           </div>
//           <div className="mb-6">
//             <label className="block text-gray-700 text-sm font-bold mb-2">
//               Phone Number
//             </label>
//             <input
//               type="tel"
//               name="pnumber"
//               value={formData.pnumber}
//               onChange={handleChange}
//               className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
//               required
//             />
//           </div>
//           <div className="flex items-center justify-between">
//             <button
//               type="submit"
//               className="bg-indigo-500 hover:bg-indigo-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
//             >
//               Sign Up
//             </button>
//           </div>
//         </form>
//       </div>
//     </div>
//   );
// }

// export default UserSignUp;

import React, { useState } from "react";
import axios from 'axios';
import Sidebar from "./Sidebar";
import { useHistory } from 'react-router-dom';

function UserSignUp() {
  const [formData, setFormData] = useState({
    uname: '',
    pswd: '',
    email: '',
    fname: '',
    lname: '',
    pnumber: '',
  });

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
    const newUser = {
      uname: formData.uname,
      pswd: formData.pswd,
      email: formData.email,
      name: {
        fname: formData.fname,
        lname: formData.lname
      },
      pnumber: formData.pnumber
    };

    try {
      const response = await axios.post('http://localhost:8080/CropSense/AppServer/UserController/createUser', newUser);
      
      // Store user information in session storage
      sessionStorage.setItem('userId', response.data.userId);
      sessionStorage.setItem('username', formData.uname);
      sessionStorage.setItem('email', formData.email);
      sessionStorage.setItem('firstName', formData.fname);
      sessionStorage.setItem('lastName', formData.lname);
      sessionStorage.setItem('phoneNumber', formData.pnumber);

      console.log('User created successfully:', response.data);

      // Redirect to home page or any other page
      history.push('/');

    } catch (error) {
      console.error('Error creating user:', error);
      // Handle error: show an error message to the user
      alert('Error creating user. Please try again.');
    }
  };

  return (
    <div className="flex h-screen">

      <div className="flex-1 flex justify-center items-center">
        <form onSubmit={handleSubmit} className="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4 w-1/2">
          <h2 className="text-2xl font-bold mb-6">User Signup</h2>
          <div className="mb-4">
            <label className="block text-gray-700 text-sm font-bold mb-2">
              Username
            </label>
            <input
              type="text"
              name="uname"
              value={formData.uname}
              onChange={handleChange}
              className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
              required
            />
          </div>
          <div className="mb-4">
            <label className="block text-gray-700 text-sm font-bold mb-2">
              Password
            </label>
            <input
              type="password"
              name="pswd"
              value={formData.pswd}
              onChange={handleChange}
              className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
              required
            />
          </div>
          <div className="mb-4">
            <label className="block text-gray-700 text-sm font-bold mb-2">
              Email
            </label>
            <input
              type="email"
              name="email"
              value={formData.email}
              onChange={handleChange}
              className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
              required
            />
          </div>
          <div className="mb-4">
            <label className="block text-gray-700 text-sm font-bold mb-2">
              First Name
            </label>
            <input
              type="text"
              name="fname"
              value={formData.fname}
              onChange={handleChange}
              className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
              required
            />
          </div>
          <div className="mb-4">
            <label className="block text-gray-700 text-sm font-bold mb-2">
              Last Name
            </label>
            <input
              type="text"
              name="lname"
              value={formData.lname}
              onChange={handleChange}
              className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
              required
            />
          </div>
          <div className="mb-6">
            <label className="block text-gray-700 text-sm font-bold mb-2">
              Phone Number
            </label>
            <input
              type="tel"
              name="pnumber"
              value={formData.pnumber}
              onChange={handleChange}
              className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
              required
            />
          </div>
          <div className="flex items-center justify-between">
            <button
              type="submit"
              className="bg-indigo-500 hover:bg-indigo-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
            >
              Sign Up
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}

export default UserSignUp;
