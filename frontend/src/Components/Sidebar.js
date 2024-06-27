// src/components/Sidebar.js
import { HomeIcon, UserCircleIcon, Squares2X2Icon, SquaresPlusIcon, ChartPieIcon, MapIcon, CursorArrowRippleIcon } from '@heroicons/react/24/outline';

const Sidebar = () => {
  return (
    <div className="h-screen w-64 bg-gray-800 text-white">
      <div className="p-4">
        <h1 className="text-2xl font-bold">Dashboard</h1>
      </div>
      <nav className="mt-5">
        <a href="#" className="block py-2.5 px-4 rounded transition duration-200 hover:bg-gray-700 hover:text-white flex items-center">
          <HomeIcon className="h-5 w-5 mr-2" />
          Dashboard
        </a>
        <a href="#" className="block py-2.5 px-4 rounded transition duration-200 hover:bg-gray-700 hover:text-white flex items-center">
          <MapIcon className="h-5 w-5 mr-2" />
          Farm
        </a>
        <a href="#" className="block py-2.5 px-4 rounded transition duration-200 hover:bg-gray-700 hover:text-white flex items-center">
          <Squares2X2Icon className="h-5 w-5 mr-2" />
          Land
        </a>
        <a href="#" className="block py-2.5 px-4 rounded transition duration-200 hover:bg-gray-700 hover:text-white flex items-center">
          <SquaresPlusIcon className="h-5 w-5 mr-2" />
          Crop
        </a>
        <a href="#" className="block py-2.5 px-4 rounded transition duration-200 hover:bg-gray-700 hover:text-white flex items-center">
          <CursorArrowRippleIcon className="h-5 w-5 mr-2" />
          Missions
        </a>
        <a href="#" className="block py-2.5 px-4 rounded transition duration-200 hover:bg-gray-700 hover:text-white flex items-center">
          <ChartPieIcon className="h-5 w-5 mr-2" />
          Metrics
        </a>
      </nav>
    </div>
  );
};

export default Sidebar;
