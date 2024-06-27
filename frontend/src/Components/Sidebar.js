// src/components/Sidebar.js
import { HomeIcon, UserCircleIcon, Squares2X2Icon, SquaresPlusIcon, ChartPieIcon, MapIcon, CursorArrowRippleIcon } from '@heroicons/react/24/outline';
import logo from "./styles/Crop.png";

const Sidebar = () => {
  return (
    <div className="h-screen w-64 bg-white text-black border border-gray-300">
      <div className="p-4 flex items-center justify-center">
        <img src={logo} alt="Logo" style={{height: '60px', width: '60px', marginRight: '10px' }} />
      </div>
      <div className="p-4 flex items-center justify-center">
        <h1 className="px-10 text-2xl font-bold">CropSense</h1>
      </div>
      <nav className="mt-5">
        <a href="/UserDashboard" className="block text-lg text-indigo-700 font-bold py-4 px-10 rounded transition duration-200 bg-indigo-100 hover:bg-gray-700 hover:text-white flex items-center">
          <HomeIcon className="h-5 w-5 mr-2" />
          Dashboard
        </a>
        <a href="/Farm" className="block text-lg py-4 px-10 rounded transition duration-200 hover:bg-gray-700 hover:text-white flex items-center">
          <MapIcon className="h-5 w-5 mr-2" />
          Farm
        </a>
        <a href="/Land" className="block text-lg py-4 px-10 rounded transition duration-200 hover:bg-gray-700 hover:text-white flex items-center">
          <Squares2X2Icon className="h-5 w-5 mr-2" />
          Land
        </a>
        <a href="/Crop" className="block text-lg py-4 px-10 rounded transition duration-200 hover:bg-gray-700 hover:text-white flex items-center">
          <SquaresPlusIcon className="h-5 w-5 mr-2" />
          Crop
        </a>
        <a href="/Mission" className="block text-lg py-4 px-10 rounded transition duration-200 hover:bg-gray-700 hover:text-white flex items-center">
          <CursorArrowRippleIcon className="h-5 w-5 mr-2" />
          Missions
        </a>
        <a href="/Metrics" className="block text-lg py-4 px-10 rounded transition duration-200 hover:bg-gray-700 hover:text-white flex items-center">
          <ChartPieIcon className="h-5 w-5 mr-2" />
          Metrics
        </a>
      </nav>
    </div>
  );
};

export default Sidebar;
