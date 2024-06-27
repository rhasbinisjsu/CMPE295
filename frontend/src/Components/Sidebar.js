// src/components/Sidebar.js
import { HomeIcon, UserCircleIcon, Squares2X2Icon, SquaresPlusIcon, ChartPieIcon, MapIcon, CursorArrowRippleIcon } from '@heroicons/react/24/outline';
import logo from "./styles/Crop.png";
import { Link, useLocation } from 'react-router-dom';


const Sidebar = () => {
  const location = useLocation();

  // Function to check if the link is active
  const isActive = (path) => location.pathname === path;

  return (
    <div className="h-screen w-64 bg-white text-black border border-gray-300">
      <div className="p-4 flex items-center justify-center">
        <img src={logo} alt="Logo" style={{height: '60px', width: '60px', marginRight: '10px' }} />
      </div>
      <div className="p-4 flex items-center justify-center">
        <h1 className="px-10 text-2xl font-bold">CropSense</h1>
      </div>

      <nav className="mt-5">
       <Link
          to="/UserDashboard"
          className={`block text-lg py-4 px-10 rounded transition duration-200 flex items-center ${
            isActive('/UserDashboard') ? 'text-indigo-700 font-bold bg-indigo-100' : 'hover:bg-indigo-600 hover:text-white'
          }`}
        >
          <HomeIcon className="h-5 w-5 mr-2" />
          Dashboard
        </Link>

          <Link
          to="/Farm"
          className={`block text-lg py-4 px-10 rounded transition duration-200 flex items-center ${
            isActive('/Farm') ? 'text-indigo-700 font-bold bg-indigo-100' : 'hover:bg-indigo-600 hover:text-white'
          }`}
        >
          <MapIcon className="h-5 w-5 mr-2" />
          Farm
        </Link>

          <Link
          to="/Land"
          className={`block text-lg py-4 px-10 rounded transition duration-200 flex items-center ${
            isActive('/Land') ? 'text-indigo-700 font-bold bg-indigo-100' : 'hover:bg-indigo-600 hover:text-white'
          }`}
        >
          <Squares2X2Icon className="h-5 w-5 mr-2" />
          Land
        </Link>

          <Link
          to="/Crop"
          className={`block text-lg py-4 px-10 rounded transition duration-200 flex items-center ${
            isActive('/Crop') ? 'text-indigo-700 font-bold bg-indigo-100' : 'hover:bg-indigo-600 hover:text-white'
          }`}
        >
          <SquaresPlusIcon className="h-5 w-5 mr-2" />
          Crop
        </Link>

          <Link
          to="/Mission"
          className={`block text-lg py-4 px-10 rounded transition duration-200 flex items-center ${
            isActive('/Mission') ? 'text-indigo-700 font-bold bg-indigo-100' : 'hover:bg-indigo-600 hover:text-white'
          }`}
        >
          <CursorArrowRippleIcon className="h-5 w-5 mr-2" />
          Mission
        </Link>

          <Link
          to="/Metrics"
          className={`block text-lg py-4 px-10 rounded transition duration-200 flex items-center ${
            isActive('/Metrics') ? 'text-indigo-700 font-bold bg-indigo-100' : 'hover:bg-indigo-600 hover:text-white'
          }`}
        >
          <ChartPieIcon className="h-5 w-5 mr-2" />
          Metrics
        </Link>

      </nav>
    </div>
  );
};

export default Sidebar;
