import React, { useState, useEffect } from "react";
import Modal from 'react-modal';
import { MapContainer, TileLayer, Marker, useMapEvents, Polygon } from 'react-leaflet';
import L from 'leaflet';
import 'leaflet/dist/leaflet.css';
import Sidebar from "./Sidebar";
import { MapPinIcon } from '@heroicons/react/solid';
import axios from 'axios';
import { useHistory } from 'react-router-dom';

import UserDashboard from "./UserDashboard";

function Farm() {
  const history = useHistory();
  const [data, setData] = useState([]);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [form, setForm] = useState({
    name: '',
    street: '',
    city: '',
    zip: '',
  });
  const [pins, setPins] = useState([]);

  useEffect(() => {
    // Example fetch call to simulate data loading
    setTimeout(() => {
      setData([
        { id: 1, name: 'Farm 1', location: 'Location 1' },
        { id: 2, name: 'Farm 2', location: 'Location 2' },
        { id: 3, name: 'Farm 3', location: 'Location 3' },
      ]);
    }, 1000);
  }, []);

  const handleView = (id) => {
    alert(`View Farm with ID: ${id}`);
    history.push(`/farmDetail/${id}`);

    // Implement view logic here
  };

  const handleDelete = (id) => {
    alert(`Delete Farm with ID: ${id}`);
    // Implement delete logic here
  };

  const openModal = () => {
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
    setForm({ name: '', street: '', city: '', zip: '' });
    setPins([]);
  };

  const handleChange = (e) => {
    setForm({
      ...form,
      [e.target.name]: e.target.value,
    });
  };

  const handleCenterFarm = () => {
    // Center the map based on the address
    // You can use a geocoding service to get coordinates from the address
  };

  const addPin = (e) => {
    if (pins.length < 4) {
      setPins([...pins, e.latlng]);
    }
  };

  const MapEvents = () => {
    useMapEvents({
      click: addPin,
    });
    return null;
  }

  const handleSubmit = async (e) => {
    e.preventDefault();
    // Make an API call to save the farm to the database
    const newFarm = {
      id: data.length + 1, // This is just a placeholder.
      name: form.name,
      location: `${form.street}, ${form.city}, ${form.zip}`,
    };
    // Example of API call:
    // await fetch('/api/farms', {
    //   method: 'POST',
    //   headers: {
    //     'Content-Type': 'application/json',
    //   },
    //   body: JSON.stringify(newFarm),
    // });

    // Update the local state to include the new farm
    setData([...data, newFarm]);
    closeModal();
  };

  // Custom icon using Heroicons MapPinIcon
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
          {/* Create Farm Button */}
          <button
            className="absolute top-1/2 right-0 transform -translate-y-1/2 bg-indigo-500 text-white py-2 px-4 rounded shadow hover:bg-indigo-700"
            onClick={openModal}
          >
            Create Farm
          </button>
        </div>
        {/* Table for showcasing farms */}
        <div className="mt-16">
          <h1 className="text-3xl font-bold mb-4">Farms</h1>
          <table className="min-w-full bg-white">
            <thead>
              <tr>
                <th className="py-2 px-4 border-b">ID</th>
                <th className="py-2 px-4 border-b">Name</th>
                <th className="py-2 px-4 border-b">Location</th>
                <th className="py-2 px-4 border-b">Actions</th>
              </tr>
            </thead>
            <tbody>
              {data.map((farm) => (
                <tr key={farm.id}>
                  <td className="py-4 px-4 border-b text-center">{farm.id}</td>
                  <td className="py-4 px-4 border-b text-center">{farm.name}</td>
                  <td className="py-4 px-4 border-b text-center">{farm.location}</td>
                  <td className="py-4 px-4 border-b text-center">
                    <button
                      className="bg-indigo-500 text-white py-1 px-3 rounded mr-8 hover:bg-indigo-700"
                      onClick={() => handleView(farm.id)}
                    >
                      View
                    </button>
                    <button
                      className="bg-indigo-500 text-white py-1 px-3 rounded hover:bg-indigo-700"
                      onClick={() => handleDelete(farm.id)}
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
        contentLabel="Create Farm"
        className="fixed inset-0 flex items-center justify-center bg-gray-800 bg-opacity-75"
        ariaHideApp={false}
      >
        <div className="bg-white p-6 rounded-lg w-1/2">
          <h2 className="text-3xl mb-4 font-bold">Create Farm</h2>
          <form>
            <div className="mb-2 w-1/2">
              <label className="block text-gray-700 text-sm font-bold mb-2">Farm Name</label>
              <input
                type="text"
                name="name"
                value={form.name}
                onChange={handleChange}
                className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
              />
            </div>
            <div className="mb-2">
              <label className="block text-gray-700 text-sm font-bold mb-2">Street</label>
              <input
                type="text"
                name="street"
                value={form.street}
                onChange={handleChange}
                className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
              />
            </div>
            <div className="flex space-x-4">
              <div className="mb-2 w-3/5 mr-4">
              <label className="block text-gray-700 text-sm font-bold mb-2">City</label>
              <input
                type="text"
                name="city"
                value={form.city}
                onChange={handleChange}
                className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
              />
            </div>
            <div className="mb-2 w-2/5">
              <label className="block text-gray-700 text-sm font-bold mb-2">Zip Code</label>
              <input
                type="text"
                name="zip"
                value={form.zip}
                onChange={handleChange}
                className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
              />
            </div>
            </div>
            <button
              type="button"
              onClick={handleCenterFarm}
              className="bg-indigo-400 text-white py-2 px-4 rounded shadow hover:bg-blue-700"
            >
              Center Farm
            </button>
          </form>
          <div className="mt-6">
            <MapContainer center={[37.003, -121.557]} zoom={13} style={{ height: '400px', width: '100%' }}>
              <TileLayer
                url="https://server.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer/tile/{z}/{y}/{x}"
              />
              <MapEvents />
              {pins.map((pin, index) => (
                <Marker key={index} position={pin} icon={customIcon} />
              ))}
              {pins.length === 4 && (
                <Polygon positions={pins} color="blue" />
              )}
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

export default Farm;
