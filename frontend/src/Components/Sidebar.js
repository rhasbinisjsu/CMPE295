// src/components/Sidebar.js
import { HomeIcon, UserCircleIcon, Squares2X2Icon, SquaresPlusIcon, ChartPieIcon, MapIcon, CursorArrowRippleIcon } from '@heroicons/react/24/outline';
import logo from "./styles/Crop.png";
import { Link, useLocation } from 'react-router-dom';


const Sidebar = () => {
  const location = useLocation();

  // Function to check if the link is active
  const isActive = (path) => location.pathname === path;

  return (
    <div className="h-screen py-8 w-64 bg-white text-black p-4 border-r items-center">


      <nav className="mt-5">
       <Link
          to="/UserDashboard"
          className={`block text-lg py-8 px-10 rounded transition duration-200 flex items-center ${
            isActive('/UserDashboard') ? 'text-indigo-700 font-bold bg-indigo-100' : 'hover:bg-indigo-600 hover:text-white'
          }`}
        >
          <HomeIcon className="h-5 w-5 mr-2" />
          Dashboard
        </Link>

          <Link
          to="/Farm"
          className={`block text-lg py-8 px-10 rounded transition duration-200 flex items-center ${
            isActive('/Farm') ? 'text-indigo-700 font-bold bg-indigo-100' : 'hover:bg-indigo-600 hover:text-white'
          }`}
        >
          <MapIcon className="h-5 w-5 mr-2" />
          Farm
        </Link>


          <Link
          to="/Crop"
          className={`block text-lg py-8 px-10 rounded transition duration-200 flex items-center ${
            isActive('/Crop') ? 'text-indigo-700 font-bold bg-indigo-100' : 'hover:bg-indigo-600 hover:text-white'
          }`}
        >
          <SquaresPlusIcon className="h-5 w-5 mr-2" />
          Crop
        </Link>

          <Link
          to="/Mission"
          className={`block text-lg py-8 px-10 rounded transition duration-200 flex items-center ${
            isActive('/Mission') ? 'text-indigo-700 font-bold bg-indigo-100' : 'hover:bg-indigo-600 hover:text-white'
          }`}
        >
          <CursorArrowRippleIcon className="h-5 w-5 mr-2" />
          Mission
        </Link>


      </nav>
    </div>
  );
};

export default Sidebar;
