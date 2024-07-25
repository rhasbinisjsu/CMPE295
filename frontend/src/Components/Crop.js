// import React, { useState, useEffect } from "react";
// import { useHistory } from 'react-router-dom';


// import Sidebar from "./Sidebar";
// import UserDashboard from "./UserDashboard";

// function Crop() {
//   const [data, setData] = useState(null);

//   return (
//     <div className="flex">
//       <Sidebar />
//       <div className="flex-1 p-10">
//           <h1 className="text-3xl font-bold mb-6">Crop</h1>
//       </div>
//     </div>
//   );
// }

// export default Crop;

//----------------------------------------------------------------------------------------------------


// import React, { useState, useEffect } from "react";
// import { useHistory } from 'react-router-dom';
// import axios from 'axios';

// import Sidebar from "./Sidebar";

// function Crop() {
//   const [crops, setCrops] = useState([]);
//   const history = useHistory();

//   useEffect(() => {
//     const farmId = sessionStorage.getItem('farmId');
//     // axios.get(`http://localhost:8080/CropSense/AppServer/CropController/fetchAllCropsForFarm?farmId=${farmId}`)
//     axios.get(`http://localhost:8080/CropSense/AppServer/CropController/fetchAllCropsForFarm?farmId=1`)
//       .then(response => {
//         setCrops(response.data);
//       })
//       .catch(error => {
//         console.error('There was an error fetching the crops data!', error);
//       });
//   }, []);

//   const handleRowClick = (cropId) => {
//     history.push(`/crop/${cropId}`);
//   };

//   return (
//     <div className="flex">
//       <Sidebar />
//       <div className="flex-1 p-10">
//         <h1 className="text-3xl font-bold mb-6">Crop</h1>
//         <div className="overflow-x-auto">
//           <table className="min-w-full bg-white">
//             <thead>
//               <tr>
//                 <th className="py-2 px-4 bg-gray-200">ID</th>
//                 <th className="py-2 px-4 bg-gray-200">Species</th>
//                 <th className="py-2 px-4 bg-gray-200">Transplant Amount</th>
//                 <th className="py-2 px-4 bg-gray-200">Cultivated Amount</th>
//                 <th className="py-2 px-4 bg-gray-200">Active</th>
//                 <th className="py-2 px-4 bg-gray-200">Status</th>
//                 <th className="py-2 px-4 bg-gray-200">Start Date</th>
//                 <th className="py-2 px-4 bg-gray-200">End Date</th>
//               </tr>
//             </thead>
//             <tbody>
//               {crops.map((crop) => (
//                 <tr 
//                   key={crop.cropId} 
//                   className="cursor-pointer hover:bg-gray-100" 
//                   onClick={() => handleRowClick(crop.cropId)}
//                 >
//                   <td className="py-2 px-4">{crop.cropId}</td>
//                   <td className="py-2 px-4">{crop.cropSpecies}</td>
//                   <td className="py-2 px-4">{crop.transplantAmount}</td>
//                   <td className="py-2 px-4">{crop.cultivatedAmount}</td>
//                   <td className="py-2 px-4">{crop.activeFlag ? 'Yes' : 'No'}</td>
//                   <td className="py-2 px-4">{crop.status}</td>
//                   <td className="py-2 px-4">{crop.startDate}</td>
//                   <td className="py-2 px-4">{crop.endDate}</td>
//                 </tr>
//               ))}
//             </tbody>
//           </table>
//         </div>
//       </div>
//     </div>
//   );
// }

// export default Crop;

import React, { useState, useEffect } from "react";
import { useHistory } from 'react-router-dom';
import axios from 'axios';
import Modal from 'react-modal';

import Sidebar from "./Sidebar";

const METRICS_SERVER_IP=process.env.METRICS_SERVER_IP;
const METRICS_SEVER_PORT=process.env.METRICS_SERVER_PORT;
const APP_SERVER_IP=process.env.APP_SERVER_IP;
const APP_SERVER_PORT=process.env.APP_SERVER_PORT;

function Crop() {
  const [crops, setCrops] = useState([]);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [form, setForm] = useState({
    farmId: sessionStorage.getItem('farmId') || 1, // Assuming farmId is stored in session storage or set to 1 for testing
    cropSpecies: '',
    transplantAmount: '',
    cultivatedAmount: '',
    activeFlag: false,
    status: '',
    startDate: '',
    endDate: '',
  });
  const history = useHistory();

  useEffect(() => {
    const farmId = sessionStorage.getItem('farmId');
    axios.get(`http://${APP_SERVER_IP}:${APP_SERVER_PORT}/CropSense/AppServer/CropController/fetchAllCropsForFarm?farmId=${farmId}`)
    // axios.get(`http://localhost:8080/CropSense/AppServer/CropController/fetchAllCropsForFarm?farmId=1`)
      .then(response => {
        setCrops(response.data);
      })
      .catch(error => {
        console.error('There was an error fetching the crops data!', error);
      });
  }, []);

  const handleRowClick = (cropId) => {
    history.push(`/crop/${cropId}`);
  };

  const openModal = () => {
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
    setForm({
      farmId: sessionStorage.getItem('farmId') || 1,
      cropSpecies: '',
      transplantAmount: '',
      cultivatedAmount: '',
      activeFlag: false,
      status: '',
      startDate: '',
      endDate: '',
    });
  };

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setForm({
      ...form,
      [name]: type === 'checkbox' ? checked : value,
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    axios.post(`http://${APP_SERVER_IP}:${APP_SERVER_PORT}/CropSense/AppServer/CropController/createCrop`, form)
      .then(response => {
        setCrops([...crops, response.data]);
        closeModal();
        window.location.reload();
      })
      .catch(error => {
        console.error('There was an error creating the crop!', error);
      });
  };

  return (
    <div className="flex">

      <div className="flex-1 p-10">
        <div className="relative">
          <h1 className="text-3xl font-bold mb-6">Crop</h1>
          {/* Create Crop Button */}
          <button
            className="absolute top-1/2 right-0 transform -translate-y-1/2 bg-indigo-500 text-white py-2 px-4 rounded shadow hover:bg-indigo-700"
            onClick={openModal}
          >
            Create Crop
          </button>
        </div>
        <div className="overflow-x-auto">
          <table className="min-w-full bg-white">
            <thead>
              <tr>
                <th className="py-2 px-4 bg-gray-200">ID</th>
                <th className="py-2 px-4 bg-gray-200">Species</th>
                <th className="py-2 px-4 bg-gray-200">Transplant Amount</th>
                <th className="py-2 px-4 bg-gray-200">Cultivated Amount</th>
                <th className="py-2 px-4 bg-gray-200">Active</th>
                <th className="py-2 px-4 bg-gray-200">Status</th>
                <th className="py-2 px-4 bg-gray-200">Start Date</th>
                <th className="py-2 px-4 bg-gray-200">End Date</th>
              </tr>
            </thead>
            <tbody>
              {crops.map((crop) => (
                <tr 
                  key={crop.cropId} 
                  className="cursor-pointer hover:bg-gray-100" 
                  onClick={() => handleRowClick(crop.cropId)}
                >
                  <td className="py-2 px-4">{crop.cropId}</td>
                  <td className="py-2 px-4">{crop.cropSpecies}</td>
                  <td className="py-2 px-4">{crop.transplantAmount}</td>
                  <td className="py-2 px-4">{crop.cultivatedAmount}</td>
                  <td className="py-2 px-4">{crop.activeFlag ? 'Yes' : 'No'}</td>
                  <td className="py-2 px-4">{crop.status}</td>
                  <td className="py-2 px-4">{crop.startDate}</td>
                  <td className="py-2 px-4">{crop.endDate}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
      <Modal
        isOpen={isModalOpen}
        onRequestClose={closeModal}
        contentLabel="Create Crop"
        className="fixed inset-0 flex items-center justify-center bg-gray-800 bg-opacity-75"
        ariaHideApp={false}
      >
        <div className="bg-white p-6 rounded-lg w-1/2">
          <h2 className="text-3xl mb-4 font-bold">Create Crop</h2>
          <form onSubmit={handleSubmit}>
            <div className="mb-2">
              <label className="block text-gray-700 text-sm font-bold mb-2">Crop Species</label>
              <input
                type="text"
                name="cropSpecies"
                value={form.cropSpecies}
                onChange={handleChange}
                className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
              />
            </div>
            <div className="mb-2">
              <label className="block text-gray-700 text-sm font-bold mb-2">Transplant Amount</label>
              <input
                type="number"
                name="transplantAmount"
                value={form.transplantAmount}
                onChange={handleChange}
                className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
              />
            </div>
            <div className="mb-2">
              <label className="block text-gray-700 text-sm font-bold mb-2">Cultivated Amount</label>
              <input
                type="number"
                name="cultivatedAmount"
                value={form.cultivatedAmount}
                onChange={handleChange}
                className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
              />
            </div>
            <div className="mb-2">
              <label className="block text-gray-700 text-sm font-bold mb-2">Active</label>
              <input
                type="checkbox"
                name="activeFlag"
                checked={form.activeFlag}
                onChange={handleChange}
                
              />
            </div>
            <div className="mb-2">
              <label className="block text-gray-700 text-sm font-bold mb-2">Status</label>
              <input
                type="text"
                name="status"
                value={form.status}
                onChange={handleChange}
                className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
              />
            </div>
            <div className="mb-2">
              <label className="block text-gray-700 text-sm font-bold mb-2">Start Date</label>
              <input
                type="date"
                name="startDate"
                value={form.startDate}
                onChange={handleChange}
                className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
              />
            </div>
            <div className="mb-2">
              <label className="block text-gray-700 text-sm font-bold mb-2">End Date</label>
              <input
                type="date"
                name="endDate"
                value={form.endDate}
                onChange={handleChange}
                className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
              />
            </div>
            <div className="mt-4 text-right">
              <button
                type="submit"
                className="bg-indigo-500 text-white py-2 px-4 rounded shadow hover:bg-green-700"
              >
                Create
              </button>
              <button
                type="button"
                onClick={closeModal}
                className="bg-gray-400 text-white py-2 px-4 rounded shadow hover:bg-gray-700 ml-2"
              >
                Close
              </button>
            </div>
          </form>
        </div>
      </Modal>
    </div>
  );
}

export default Crop;
