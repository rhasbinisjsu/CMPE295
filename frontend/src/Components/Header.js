// import React, { useState }  from 'react';
// import logo from "./styles/Crop.png";
// import ProfileModal from './ProfileModal';



// const Header = () => {
//   const [isModalOpen, setIsModalOpen] = useState(false);
//   const [user, setUser] = useState({
//     name: "John Doe",
//     username: "johndoe",
//     email: "john@example.com",
//     phone: "123-456-7890",
//     profilePicture: "https://picsum.photos/seed/picsum/200/300" // Replace with the actual profile picture URL
//   });

//   const handleModalOpen = () => {
//     setIsModalOpen(true);
//   };

//   const handleModalClose = () => {
//     setIsModalOpen(false);
//   };

//   const handleSave = (updatedUser) => {
//     setUser(updatedUser);
//   };

//   return (
//     <>
//       <header className="text-black shadow p-4 flex justify-between items-center w-full">
//         {/* Flex container for the logo and title */}
//         <div className="flex items-center">
//           <img src={logo} alt="Logo" style={{ height: '60px', width: '60px', marginRight: '10px' }} />
//           <h1 className="text-2xl font-bold">CropSense</h1>
//         </div>
//         {/* Flex container for the profile picture and username */}
//         <div className="flex items-center space-x-4 pr-4 cursor-pointer" onClick={handleModalOpen}>
//           <img src={user.profilePicture} alt="Profile" className="h-10 w-10 rounded-full" />
//           <span className="text-lg">{user.name}</span>
//         </div>
//       </header>
//       <ProfileModal
//         isOpen={isModalOpen}
//         onRequestClose={handleModalClose}
//         user={user}
//         onSave={handleSave}
//       />
//     </>
//   );
// };

// export default Header;

// import React, { useState, useEffect } from 'react';
// import logo from "./styles/Crop.png";
// import ProfileModal from './ProfileModal';

// const Header = () => {
//   const [isModalOpen, setIsModalOpen] = useState(false);
//   const [user, setUser] = useState({
//     name: "",
//     username: "",
//     email: "",
//     phone: "",
//     profilePicture: "https://picsum.photos/seed/picsum/200/300" // Default profile picture URL
//   });

//   useEffect(() => {
//     // Fetch user data from sessionStorage
//     const storedUser = sessionStorage.getItem('userData');
//     if (storedUser) {
//       const parsedUser = JSON.parse(storedUser);
//       setUser(parsedUser);
//     }
//   }, []);

//   const handleModalOpen = () => {
//     setIsModalOpen(true);
//   };

//   const handleModalClose = () => {
//     setIsModalOpen(false);
//   };

//   const handleSave = (updatedUser) => {
//     // Update user data in sessionStorage
//     sessionStorage.setItem('userData', JSON.stringify(updatedUser));
//     setUser(updatedUser);
//   };

//   return (
//     <>
//       <header className="text-black shadow p-4 flex justify-between items-center w-full">
//         {/* Flex container for the logo and title */}
//         <div className="flex items-center">
//           <img src={logo} alt="Logo" style={{ height: '60px', width: '60px', marginRight: '10px' }} />
//           <h1 className="text-2xl font-bold">CropSense</h1>
//         </div>
//         {/* Flex container for the profile picture and username */}
//         <div className="flex items-center space-x-4 pr-4 cursor-pointer" onClick={handleModalOpen}>
//           <img src={user.profilePicture} alt="Profile" className="h-10 w-10 rounded-full" />
//           <span className="text-lg">{user.name}</span>
//         </div>
//       </header>
//       <ProfileModal
//         isOpen={isModalOpen}
//         onRequestClose={handleModalClose}
//         user={user}
//         onSave={handleSave}
//       />
//     </>
//   );
// };

// export default Header;



// import React, { useState, useEffect } from 'react';
// import logo from "./styles/Crop.png";
// import ProfileModal from './ProfileModal';

// const Header = () => {
//   const [isModalOpen, setIsModalOpen] = useState(false);
//   const [user, setUser] = useState({
//     firstName: sessionStorage.getItem("firstName"),
//     lastName:  sessionStorage.getItem("lastName"),
//     username: sessionStorage.getItem("username"),
//     email: sessionStorage.getItem("email"),
//     phone: sessionStorage.getItem("phoneNumber"),
//     profilePicture: "https://picsum.photos/seed/picsum/200/300" // Default profile picture URL
//   });

//   useEffect(() => {
//     // Retrieve user information from session storage
//     const userData = JSON.parse(sessionStorage.getItem('userData'));
//     if (userData) {
//       setUser({
//         name: `${userData.firstName} ${userData.lastName}`,
//         username: userData.username,
//         email: userData.email,
//         phone: userData.phoneNumber,
//         profilePicture: "https://picsum.photos/seed/picsum/200/300" // Replace with actual profile picture URL if available
//       });
//     }
//   }, []);

//   const handleModalOpen = () => {
//     setIsModalOpen(true);
//   };

//   const handleModalClose = () => {
//     setIsModalOpen(false);
//   };

//   const handleSave = (updatedUser) => {
//     // Handle saving updated user information if needed
//     setUser(updatedUser);
//   };

//   return (
//     <>
//       <header className="text-black shadow p-4 flex justify-between items-center w-full">
//         {/* Flex container for the logo and title */}
//         <div className="flex items-center">
//           <img src={logo} alt="Logo" style={{ height: '60px', width: '60px', marginRight: '10px' }} />
//           <h1 className="text-2xl font-bold">CropSense</h1>
//         </div>
//         {/* Flex container for the profile picture and username */}
//         <div className="flex items-center space-x-4 pr-4 cursor-pointer" onClick={handleModalOpen}>
//           <img src={user.profilePicture} alt="Profile" className="h-10 w-10 rounded-full" />
//           <span className="text-lg">{user.name}</span>
//         </div>
//       </header>
//       <ProfileModal
//         isOpen={isModalOpen}
//         onRequestClose={handleModalClose}
//         user={user}
//         onSave={handleSave}
//       />
//     </>
//   );
// };

// export default Header;


import React, { useState } from 'react';
import logo from "./styles/Crop.png";
import ProfileModal from './ProfileModal';

const Header = () => {
  const [isModalOpen, setIsModalOpen] = useState(false);

  const handleModalOpen = () => {
    setIsModalOpen(true);
  };

  const handleModalClose = () => {
    setIsModalOpen(false);
  };

  return (
    <>
      <header className="text-black shadow p-4 flex justify-between items-center w-full">
        {/* Flex container for the logo and title */}
        <div className="flex items-center">
          <img src={logo} alt="Logo" style={{ height: '60px', width: '60px', marginRight: '10px' }} />
          <h1 className="text-2xl font-bold">CropSense</h1>
        </div>
        {/* Flex container for the profile picture and username */}
        <div className="flex items-center space-x-4 pr-4 cursor-pointer" onClick={handleModalOpen}>
          <img src={sessionStorage.getItem("profilePicture") || "https://picsum.photos/seed/picsum/200/300"} alt="Profile" className="h-10 w-10 rounded-full" />
          <span className="text-lg">{sessionStorage.getItem("firstName")} {sessionStorage.getItem("lastName")}</span>
        </div>
      </header>
      <ProfileModal
        isOpen={isModalOpen}
        onRequestClose={handleModalClose}
        onSave={(updatedUser) => {
          // Optionally handle saving updated user information
        }}
        user={{
          name: `${sessionStorage.getItem("firstName")} ${sessionStorage.getItem("lastName")}`,
          username: sessionStorage.getItem("username"),
          email: sessionStorage.getItem("email"),
          phone: sessionStorage.getItem("phoneNumber"),
          profilePicture: sessionStorage.getItem("profilePicture") || "https://picsum.photos/seed/picsum/200/300"
        }}
      />
    </>
  );
};

export default Header;
