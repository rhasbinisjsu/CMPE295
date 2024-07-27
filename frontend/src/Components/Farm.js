import React, { useState, useEffect, useRef } from "react";
import Modal from 'react-modal';
import { MapContainer, TileLayer, Marker, Polygon, useMap, useMapEvents } from 'react-leaflet';
import L from 'leaflet';
import 'leaflet/dist/leaflet.css';
import Sidebar from "./Sidebar";
import { MapPinIcon } from '@heroicons/react/solid';
import axios from 'axios';
import { useHistory } from 'react-router-dom';
import 'leaflet.gridlayer.googlemutant';

import UserDashboard from "./UserDashboard";

function Farm() {

  const METRICS_SERVER_IP = process.env.REACT_APP_METRICS_SERVER_IP;
  const METRICS_SERVER_PORT = process.env.REACT_APP_METRICS_SERVER_PORT;
  const APP_SERVER_IP = process.env.REACT_APP_APP_SERVER_IP;
  const APP_SERVER_PORT = process.env.REACT_APP_APP_SERVER_PORT;
  const FARM_FENCE_API_URL = `http://44.204.8.116:8000/CropSense/AppServer/FarmFenceController/createFarmFence`;

  const history = useHistory();
  const [data, setData] = useState([]);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isUpdateModalOpen, setIsUpdateModalOpen] = useState(false);
  const [form, setForm] = useState({
    id: '',
    name: '',
    street: '',
    city: '',
    zip: '',
  });
  const [pins, setPins] = useState([]);
  const [mapCenter, setMapCenter] = useState([37.1951194, -121.7262247]);
  const mapRef = useRef(null);
  const pinCounter = useRef(0);


  const fetchFarms = async () => {
    const userId = sessionStorage.getItem('userId'); // Get userId from sessionStorage
    try {
      const response = await axios.get(`http://${APP_SERVER_IP}:${APP_SERVER_PORT}/CropSense/AppServer/FarmController/fetchFarmsForOwner?ownerId=${userId}`);
      setData(response.data);
    } catch (error) {
      console.error("Error fetching farms:", error);
    }
  };

  useEffect(() => {
    fetchFarms(); // Fetch farms on component mount
  }, []);

  const handleView = (id) => {
    sessionStorage.setItem('farmId', id);
    alert(`View Farm with ID: ${id}`);
    // history.push(`/Farm`);
    history.push('/Crops')
  };

  const handleUpdate = (farm) => {
    setForm({
      id: farm.id,
      name: farm.name,
      street: farm.address.line1,
      city: farm.address.city,
      zip: farm.address.zip,
    });
    setPins([]);
    setIsUpdateModalOpen(true);
  };

  const handleDelete = async (id) => {
    try{
      console.log(id)
      await axios.delete(`http://${APP_SERVER_IP}:${APP_SERVER_PORT}/CropSense/AppServer/FarmController/deleteFarm?farmId=${id}`);
      alert(`Farm with ID: ${id} deleted successfully.`);
      
      setData(data.filter(farm => farm.id !== id));
    } catch (error) {
      console.error("Error deleting farm:", error);
      alert("Error deleting farm.");
    }
  };

  const openModal = () => {
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
    setForm({ id: '', name: '', street: '', city: '', zip: '' });
    setPins([]);
    pinCounter.current = 0; // Reset pin counter when modal closes
  };

  const closeUpdateModal = () => {
    setIsUpdateModalOpen(false);
    setForm({ id: '', name: '', street: '', city: '', zip: '' });
    setPins([]);
    pinCounter.current = 0; // Reset pin counter when modal closes
  };

  const handleChange = (e) => {
    setForm({
      ...form,
      [e.target.name]: e.target.value,
    });
  };

  const handleCenterFarm = async () => {
    const address = `${form.street}, ${form.city}, ${form.zip}`;
    const geocodeUrl = `https://maps.googleapis.com/maps/api/geocode/json?address=${encodeURIComponent(address)}&key=AIzaSyAox0FCdmVGMR71gBdrCW3yGlgNTStu7m4`;

    try {
      const response = await axios.get(geocodeUrl);
      console.log("Geocode API Response:", response.data);

      if (response.data.status === "OK" && response.data.results.length > 0) {
        const { lat, lng } = response.data.results[0].geometry.location;
        setMapCenter([lat, lng]);
        if (mapRef.current) {
          mapRef.current.setView([lat, lng], 14);
        }
      } else {
        console.error("Geocoding failed: No results found.");
      }
    } catch (error) {
      console.error("Error geocoding the address:", error);
    }
  };

  const addPin = (e) => {
    if (pins.length < 4) {
      const newPin = {
        id: pinCounter.current++, // Unique ID for each pin
        lat: e.latlng.lat,
        lng: e.latlng.lng
      };
      setPins([...pins, newPin]);
      console.log("Pin added:", newPin);
    }
  };

  const MapEvents = () => {
    const map = useMap();
    useMapEvents({
      click: addPin,
    });

    useEffect(() => {
      mapRef.current = map;
    }, [map]);

    return null;
  };


  const handleSubmit = async (e) => {
    e.preventDefault();
    
    

    const farmData = {
      ownerId: sessionStorage.getItem('userId'), 
      name: form.name,
      address: {
        line1: form.street,
        line2: "",
        city: form.city,
        state: "CA", 
        zip: form.zip,
        country: "USA" 
      }
    };
  
    try {
      // Call the API to create the farm
      const farmResponse = await axios.post('http://44.204.8.116:8000/CropSense/AppServer/FarmController/createFarm', farmData);
      const farmId = farmResponse.data; 
      sessionStorage.setItem('farmId', farmId);
      console.log("Farm created with ID:", farmId);
  
      // Call the API to create the farm fence
      const fenceResponse = await axios.post('http://44.204.8.116:8000/CropSense/AppServer/FarmFenceController/createFarmFence', {
        farmId: farmId,
      });
      const fenceId = fenceResponse.data;
      sessionStorage.setItem('fenceId', fenceId);
      console.log("Farm fence created with ID:", fenceId);
  
      // Prepare the coordinates data
      const coordinatesData = pins.map((pin, index) => ({
        chainId: pin.id,
        fenceId: fenceId,
        fenceType: "Farm",
        lng: pin.lng,
        lat: pin.lat,
      }));
  
      // Call the API to create fence coordinates
      await axios.post(`http://${APP_SERVER_IP}:${APP_SERVER_PORT}/CropSense/AppServer/FenceCoordinateController/createFenceCoordinates`, coordinatesData);
      console.log("Fence coordinates created successfully.");
      console.log(coordinatesData)
  
    } catch (error) {
      console.error("Error: ", error);
    }
  
    closeModal();
    window.location.reload();
  };
  
  

  const handleUpdateSubmit = async (e) => {
    e.preventDefault();
    const updatedFarm = {
      id: form.id,
      name: form.name,
      location: `${form.street}, ${form.city}, ${form.zip}`,
    };
    setData(data.map(farm => (farm.id === updatedFarm.id ? updatedFarm : farm)));
    closeUpdateModal();
  };

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
    className: ""
  });

  return (
    <div className="flex h-screen">
      <div className="flex-1 flex justify-center items-start pt-10">
        <div className="w-3/4">
          <div className="relative">
            <button
              className="absolute top-1/2 right-0 transform -translate-y-1/2 bg-indigo-500 text-white py-2 px-4 rounded shadow hover:bg-indigo-700"
              onClick={openModal}
            >
              Create Farm
            </button>
          </div>
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
                    <td className="py-4 px-4 border-b text-center">
                      <button
                        className="text-blue-500 hover:text-blue-700"
                        onClick={() => handleView(farm.id)}
                      >
                        {farm.name}
                      </button>
                    </td>
                    
                    <td className="border border-gray-300 px-4 py-2">
                      {farm.address.line1}, {farm.address.city}, {farm.address.zip}
                    </td>
                    <td className="py-4 px-4 border-b text-center">
                      <button
                        className="bg-yellow-500 text-white py-1 px-2 rounded shadow hover:bg-yellow-700 mr-2"
                        onClick={() => handleUpdate(farm)}
                      >
                        Update
                      </button>
                      <button
                        className="bg-red-500 text-white py-1 px-2 rounded shadow hover:bg-red-700"
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

          <Modal
            isOpen={isModalOpen}
            onRequestClose={closeModal}
            contentLabel="Create Farm"
            className="fixed inset-0 flex items-center justify-center bg-gray-800 bg-opacity-75"
            ariaHideApp={false}
          >
            <div className="bg-white p-6 rounded-lg w-1/2 max-h-full overflow-auto">
              <h2 className="text-3xl mb-4 font-bold">Create Farm</h2>
              <form onSubmit={handleSubmit}>
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
                <div className="mt-6">
                  <MapContainer center={mapCenter} zoom={14} style={{ height: '400px', width: '100%' }} ref={mapRef}>
                    <TileLayer
                      url="http://{s}.google.com/vt/lyrs=s&x={x}&y={y}&z={z}"
                      subdomains={['mt0', 'mt1', 'mt2', 'mt3']}
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
                <button
                  type="submit"
                  className="mt-4 bg-green-500 text-white py-2 px-4 rounded shadow hover:bg-green-700"
                >
                  Create
                </button>
                <button
                  type="button"
                  onClick={closeModal}
                  className="ml-4 bg-red-500 text-white py-2 px-4 rounded shadow hover:bg-red-700"
                >
                  Cancel
                </button>
              </form>
            </div>
          </Modal>

          <Modal
            isOpen={isUpdateModalOpen}
            onRequestClose={closeUpdateModal}
            contentLabel="Update Farm"
            className="fixed inset-0 flex items-center justify-center bg-gray-800 bg-opacity-75"
            ariaHideApp={false}
          >
            <div className="bg-white p-6 rounded-lg w-1/2 max-h-full overflow-auto">
              <h2 className="text-3xl mb-4 font-bold">Update Farm</h2>
              <form onSubmit={handleUpdateSubmit}>

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
                <div className="mt-6">
                  <MapContainer center={mapCenter} zoom={14} style={{ height: '400px', width: '100%' }} ref={mapRef}>
                    <TileLayer
                      url="http://{s}.google.com/vt/lyrs=s&x={x}&y={y}&z={z}"
                      subdomains={['mt0', 'mt1', 'mt2', 'mt3']}
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
                <button
                  type="submit"
                  className="mt-4 bg-yellow-500 text-white py-2 px-4 rounded shadow hover:bg-yellow-700"
                >
                  Update
                </button>
                <button
                  type="button"
                  onClick={closeUpdateModal}
                  className="ml-4 bg-gray-500 text-white py-2 px-4 rounded shadow hover:bg-gray-700"
                >
                  Cancel
                </button>
              </form>
            </div>
          </Modal>
        </div>
      </div>
    </div>
  );
}

export default Farm;

