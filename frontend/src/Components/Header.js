import React, { useState }  from 'react';
import logo from "./styles/Crop.png";
import ProfileModal from './ProfileModal';



const Header = () => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [user, setUser] = useState({
    name: "John Doe",
    username: "johndoe",
    email: "john@example.com",
    phone: "123-456-7890",
    profilePicture: "https://picsum.photos/seed/picsum/200/300" // Replace with the actual profile picture URL
  });

  const handleModalOpen = () => {
    setIsModalOpen(true);
  };

  const handleModalClose = () => {
    setIsModalOpen(false);
  };

  const handleSave = (updatedUser) => {
    setUser(updatedUser);
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
          <img src={user.profilePicture} alt="Profile" className="h-10 w-10 rounded-full" />
          <span className="text-lg">{user.name}</span>
        </div>
      </header>
      <ProfileModal
        isOpen={isModalOpen}
        onRequestClose={handleModalClose}
        user={user}
        onSave={handleSave}
      />
    </>
  );
};

export default Header;