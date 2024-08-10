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
     
        <div className="flex items-center">
          <img src={logo} alt="Logo" style={{ height: '60px', width: '60px', marginRight: '10px' }} />
          <h1 className="text-2xl font-bold">CropSense</h1>
        </div>
      
        <div className="flex items-center space-x-4 pr-4 cursor-pointer" onClick={handleModalOpen}>
          <img src={sessionStorage.getItem("profilePicture") || "https://picsum.photos/seed/picsum/200/300"} alt="Profile" className="h-10 w-10 rounded-full" />
          <span className="text-lg">{sessionStorage.getItem("firstName")} {sessionStorage.getItem("lastName")}</span>
        </div>
      </header>
      <ProfileModal
        isOpen={isModalOpen}
        onRequestClose={handleModalClose}
        onSave={(updatedUser) => {
       
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


