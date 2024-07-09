import React from 'react';
import logo from "./styles/Crop.png";

const Header = () => {
  // You can replace this with actual user data
  const user = {
    name: "John Doe",
    profilePicture: "https://picsum.photos/seed/picsum/200/300" // Replace with the actual profile picture URL
  };

  return (
    <header className=" text-black shadow p-4 flex justify-between items-center">
      {/* Flex container for the logo and title */}
      <div className="flex items-center">
        <img src={logo} alt="Logo" style={{ height: '60px', width: '60px', marginRight: '10px' }} />
        <h1 className="text-2xl font-bold">CropSense</h1>
      </div>
      {/* Flex container for the profile picture and user name */}
      <div className="flex items-center space-x-4 pr-12">
        <img src={user.profilePicture} alt="Profile" className="h-12 w-12 rounded-full" />
        <span className="text-xl">{user.name}</span>
      </div>
    </header>
  );
};

export default Header;

