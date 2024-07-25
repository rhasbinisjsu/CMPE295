import React, { useState, useEffect } from "react";
import Modal from 'react-modal';
import { MapContainer, TileLayer, Marker, useMapEvents } from 'react-leaflet';
import L from 'leaflet';
import 'leaflet/dist/leaflet.css';
import Sidebar from "./Sidebar";
import UserDashboard from "./UserDashboard";

const missionTypes = ["Soil Sampling", "Soil Analysis", "Crop Identification"];
const crops = ["Wheat", "Corn", "Rice"];

function Mission() {
  const [data, setData] = useState([]);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [form, setForm] = useState({
    missionType: '',
    crop: '',
    missionDate: '',
    missionTime: '',
  });
  const [pins, setPins] = useState([]);

  useEffect(() => {
    // Example fetch call to simulate data loading
    setTimeout(() => {
      setData([
        { id: 1, missionType: 'Soil Sampling', crop: 'Wheat', missionDate: '2021-10-01', missionTime: '10:00', status: 'Pending' },
        { id: 2, missionType: 'Soil Analysis', crop: 'Corn', missionDate: '2021-10-02', missionTime: '11:00', status: 'Completed' },
        { id: 3, missionType: 'Crop Identification', crop: 'Rice', missionDate: '2021-10-03', missionTime: '12:00', status: 'Pending' },
      ]);
    }, 1000);
  }, []);

  const handleView = (id) => {
    alert(`View Mission with ID: ${id}`);
    // Implement view logic here
  };

  const handleDelete = (id) => {
    alert(`Delete Mission with ID: ${id}`);
    // Implement delete logic here
  };

  const openModal = () => {
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
    setForm({ missionType: '', crop: '', missionDate: '', missionTime: '' });
    setPins([]);
  };

  const handleChange = (e) => {
    setForm({
      ...form,
      [e.target.name]: e.target.value,
    });
  };

  const addPin = (e) => {
    setPins([e.latlng]);
  };

  const MapEvents = () => {
    useMapEvents({
      click: addPin,
    });
    return null;
  }

  const handleSubmit = async (e) => {
    e.preventDefault();
    const newMission = {
      id: data.length + 1,
      missionType: form.missionType,
      crop: form.crop,
      missionDate: form.missionDate,
      missionTime: form.missionTime,
      status: 'Pending',
    };

    setData([...data, newMission]);
    closeModal();
  };

  // Custom icon using the provided SVG
  const customIconHtml = `
    <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="red" class="size-6">
      <path stroke-linecap="round" stroke-linejoin="round" d="M15 10.5a3 3 0 1 1-6 0 3 3 0 0 1 6 0Z" />
      <path stroke-linecap="round" stroke-linejoin="round" d="M19.5 10.5c0 7.142-7.5 11.25-7.5 11.25S4.5 17.642 4.5 10.5a7.5 7.5 0 1 1 15 0Z" />
    </svg>
  `;

  const customIcon = new L.DivIcon({
    html: customIconHtml,
    iconSize: [24, 24],
    iconAnchor: [12, 24],
    className: "" // Add your own class name if needed
  });

  return (
    <div className="flex h-screen ">

      <div className="flex-1 flex justify-center items-start pt-40">
        <div className="w-3/4">
          <div className="relative">
            {/* Create Mission Button */}
            <button
              className="absolute top-1/2 right-0 transform -translate-y-1/2 bg-indigo-500 text-white py-2 px-4 rounded shadow hover:bg-indigo-700"
              onClick={openModal}
            >
              Create Mission
            </button>
          </div>
          {/* Table for showcasing missions */}
          <div className="mt-16">
            <h1 className="text-3xl font-bold mb-4">Missions</h1>
            <table className="min-w-full bg-white">
              <thead>
                <tr>
                  <th className="py-2 px-2 border-b">ID</th>
                  <th className="py-2 px-2 border-b">Mission Type</th>
                  <th className="py-2 px-2 border-b">Crop</th>
                  <th className="py-2 px-2 border-b">Mission Date</th>
                  <th className="py-2 px-2 border-b">Mission Time</th>
                  <th className="py-2 px-2 border-b">Status</th>
                  <th className="py-2 px-2 border-b">Actions</th>
                </tr>
              </thead>
              <tbody>
                {data.map((mission) => (
                  <tr key={mission.id}>
                    <td className="py-4 px-4 border-b text-center">{mission.id}</td>
                    <td className="py-4 px-4 border-b text-center">{mission.missionType}</td>
                    <td className="py-4 px-4 border-b text-center">{mission.crop}</td>
                    <td className="py-4 px-4 border-b text-center">{mission.missionDate}</td>
                    <td className="py-4 px-4 border-b text-center">{mission.missionTime}</td>
                    <td className="py-4 px-4 border-b text-center">{mission.status}</td>
                    <td className="py-4 px-2 border-b text-center">
                      <button
                        className="bg-indigo-500 text-white py-1 px-2 rounded mr-8 hover:bg-indigo-700"
                        onClick={() => handleView(mission.id)}
                      >
                        View
                      </button>
                      <button
                        className="bg-indigo-500 text-white py-1 px-2 rounded hover:bg-indigo-700"
                        onClick={() => handleDelete(mission.id)}
                      >
                        Delete
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      </div>
      <Modal
        isOpen={isModalOpen}
        onRequestClose={closeModal}
        contentLabel="Create Mission"
        className="fixed inset-0 flex items-center justify-center bg-gray-800 bg-opacity-75"
        ariaHideApp={false}
      >
        <div className="bg-white p-6 rounded-lg w-1/2">
          <h2 className="text-3xl mb-4 font-bold">Create Mission</h2>
          <form>
            <div className="mb-2 w-full">
              <label className="block text-gray-700 text-sm font-bold mb-2">Mission Type</label>
              <select
                name="missionType"
                value={form.missionType}
                onChange={handleChange}
                className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
              >
                <option value="">Select Mission Type</option>
                {missionTypes.map((type) => (
                  <option key={type} value={type}>{type}</option>
                ))}
              </select>
            </div>
            <div className="mb-2 w-full">
              <label className="block text-gray-700 text-sm font-bold mb-2">Crop</label>
              <select
                name="crop"
                value={form.crop}
                onChange={handleChange}
                className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
              >
                <option value="">Select Crop</option>
                {crops.map((crop) => (
                  <option key={crop} value={crop}>{crop}</option>
                ))}
              </select>
            </div>
            <div className="flex space-x-4">
             <div className="mb-2 w-1/2">
              <label className="block text-gray-700 text-sm font-bold mb-2">Mission Date</label>
              <input
                type="date"
                name="missionDate"
                value={form.missionDate}
                onChange={handleChange}
                className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
              />
            </div>
            <div className="mb-2 w-1/2">
              <label className="block text-gray-700 text-sm font-bold mb-2">Mission Time</label>
              <input
                type="time"
                name="missionTime"
                value={form.missionTime}
                onChange={handleChange}
                className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
              />
            </div>
            </div>

          </form>
          <div className="mt-2">
            <label className="block text-gray-700 text-sm font-bold mb-2">Mission Location</label>
            <MapContainer center={[37.7749, -122.4194]} zoom={10} style={{ height: '400px', width: '100%' }}>
              <TileLayer
                url="https://server.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer/tile/{z}/{y}/{x}"
              />
              <MapEvents />
              {pins.map((pin, index) => (
                <Marker key={index} position={pin} icon={customIcon} />
              ))}
            </MapContainer>
          </div>
          <div className="mt-4 text-right">
            <button
              onClick={handleSubmit}
              className="bg-indigo-400 text-white py-2 px-4 rounded shadow hover:bg-green-700"
            >
              Create
            </button>
            <button
              onClick={closeModal}
              className="bg-gray-400 text-white py-2 px-4 rounded shadow hover:bg-gray-700 ml-2"
            >
              Close
            </button>
          </div>
        </div>
      </Modal>
    </div>
  );
}

export default Mission;
