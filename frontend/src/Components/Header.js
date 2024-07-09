import React from 'react';
import { Cog6ToothIcon, UserIcon } from '@heroicons/react/24/outline';
import logo from "./styles/Crop.png";

const Header = () => {
  return (
    <header className="bg-gray-800 text-white shadow p-4 flex justify-between items-center">
      <div className=" flex items-center ">
        <img src={logo} alt="Logo" style={{height: '60px', width: '60px', marginRight: '10px' }} />
        <h1 className="px-10 text-2xl font-bold">CropSense</h1>
      </div>
      <div className="flex items-center space-x-4">
        <button aria-label="Settings">
          <Cog6ToothIcon className="h-6 w-6 text-white hover:text-gray-400" />
        </button>
        <button aria-label="Profile">
          <UserIcon className="h-6 w-6 text-white hover:text-gray-400" />
        </button>
      </div>
    </header>
  );
};

export default Header;
