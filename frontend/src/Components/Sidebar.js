import React from 'react';
import { Link, useLocation } from 'react-router-dom';
import { HomeIcon, MapIcon, SquaresPlusIcon, CursorArrowRippleIcon } from '@heroicons/react/24/outline';
import './Sidebar.css'; // Import the CSS file

const Sidebar = () => {
  const location = useLocation();

  // Function to check if the link is active
  const isActive = (path) => location.pathname === path;

  const handleLogout = () => {
    // Implement your logout functionality here
    alert('Logged out');
  };

  return (
    <div className="sidebar flex flex-col justify-between border-t border-r h-6/7">
      <div>
        <nav className="mt-10">
          <Link
            to="/userDashboard"
            className={`block text-lg py-4 px-4 rounded transition duration-200 flex items-center ${
              isActive('/userDashboard') ? 'text-indigo-700 font-bold bg-indigo-100' : 'hover:bg-indigo-600 hover:text-white'
            }`}
          >
            <HomeIcon className="h-5 w-5 mr-2" />
            Dashboard
          </Link>
          <Link
            to="/Farm"
            className={`block text-lg py-4 px-4 rounded transition duration-200 flex items-center ${
              isActive('/Farm') ? 'text-indigo-700 font-bold bg-indigo-100' : 'hover:bg-indigo-600 hover:text-white'
            }`}
          >
            <MapIcon className="h-5 w-5 mr-2" />
            Farm
          </Link>
          <Link
            to="/Crop"
            className={`block text-lg py-4 px-4 rounded transition duration-200 flex items-center ${
              isActive('/Crop') ? 'text-indigo-700 font-bold bg-indigo-100' : 'hover:bg-indigo-600 hover:text-white'
            }`}
          >
            <SquaresPlusIcon className="h-5 w-5 mr-2" />
            Crop
          </Link>
          <Link
            to="/Mission"
            className={`block text-lg py-4 px-4 rounded transition duration-200 flex items-center ${
              isActive('/Mission') ? 'text-indigo-700 font-bold bg-indigo-100' : 'hover:bg-indigo-600 hover:text-white'
            }`}
          >
            <CursorArrowRippleIcon className="h-5 w-5 mr-2" />
            Mission
          </Link>
        </nav>
      </div>
      <div className="mb-4 px-4">
        <button
          onClick={handleLogout}
          className="w-full py-2 px-4 text-left rounded transition duration-200 flex items-center text-gray-700 hover:bg-red-600 hover:text-white"
        >
          <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5 mr-2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1" />
          </svg>
          Logout
        </button>
      </div>
    </div>
  );
};

export default Sidebar;
