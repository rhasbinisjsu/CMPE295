import React, { useState, useEffect, useRef } from "react";
import axios from 'axios';
import Modal from 'react-modal';
import { MapContainer, TileLayer, Marker, Polygon, useMap, useMapEvents } from 'react-leaflet';
import L from 'leaflet';
import 'leaflet/dist/leaflet.css';
import 'leaflet.gridlayer.googlemutant';
import { useHistory } from 'react-router-dom';

function Crops() {

  const METRICS_SERVER_IP = process.env.REACT_APP_METRICS_SERVER_IP;
  const METRICS_SERVER_PORT = process.env.REACT_APP_METRICS_SERVER_PORT;
  const APP_SERVER_IP = process.env.REACT_APP_APP_SERVER_IP;
  const APP_SERVER_PORT = process.env.REACT_APP_APP_SERVER_PORT;

  const history = useHistory();
  const [crops, setCrops] = useState([]);
  const [isHistoryModalOpen, setIsHistoryModalOpen] = useState(false);
  const [historyData, setHistoryData] = useState([]);
  const [isCreateModalOpen, setIsCreateModalOpen] = useState(false);
  const [isUpdateModalOpen, setIsUpdateModalOpen] = useState(false);
  const [form, setForm] = useState({
  
    cropSpecies: '',
    transplantAmount: '',
    cultivatedAmount: '',
    activeFlag: false,
    status: '',
    startDate: '',
    endDate: ''
  });
  const [cropPins, setCropPins] = useState([]);
  const [mapCenter, setMapCenter] = useState([37.1951194, -121.7262247]);
  const mapRef = useRef(null);
  const pinCounter = useRef(0);

  const fetchCrops = async () => {
    const farmId = sessionStorage.getItem('farmId');
    try {
      const response = await axios.get(`http://${APP_SERVER_IP}:${APP_SERVER_PORT}/CropSense/AppServer/CropController/fetchActiveCropsForFarm?farmId=${farmId}`);
      console.log(response.data);
      setCrops(response.data);
    } catch (error) {
      console.error("Error fetching active crops:", error);
    }
  };
  
  
  
  useEffect(() => {
    fetchCrops();
  }, []);


  
  const handleView = (id) => {
    sessionStorage.setItem('cropId', id); 
    history.push(`/cropDetail`); 
  };

  const openHistoryModal = () => {
    const farmId = sessionStorage.getItem('farmId');
    axios.get(`http://${APP_SERVER_IP}:${APP_SERVER_PORT}/CropSense/AppServer/CropController/fetchAllCropsForFarm?farmId=${farmId}`)
      .then(response => {
        setHistoryData(response.data);
        setIsHistoryModalOpen(true);
      })
      .catch(error => {
        console.error('There was an error fetching the crop history!', error);
      });
  };

  const closeHistoryModal = () => {
    setIsHistoryModalOpen(false);
  };

  const openCreateModal = () => {
    setIsCreateModalOpen(true);
  };

  const closeCreateModal = () => {
    setIsCreateModalOpen(false);
    setForm({
      cropSpecies: '',
      transplantAmount: '',
      cultivatedAmount: '',
      activeFlag: false,
      status: '',
      startDate: '',
      endDate: ''
    });
    setCropPins([]);
  };

  const openUpdateModal = (crop) => {
    setForm({
      cropId: crop.cropId,
      cropSpecies: crop.cropSpecies,
      transplantAmount: crop.transplantAmount,
      cultivatedAmount: crop.cultivatedAmount,
      activeFlag: crop.activeFlag,
      status: crop.status,
      startDate: crop.startDate,
      endDate: crop.endDate
    });
    setIsUpdateModalOpen(true);
  };

  const closeUpdateModal = () => {
    setIsUpdateModalOpen(false);
    setForm({
      cropSpecies: '',
      transplantAmount: '',
      cultivatedAmount: '',
      activeFlag: false,
      status: '',
      startDate: '',
      endDate: ''
    });
    setCropPins([]);
  };

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setForm({
      ...form,
      [name]: type === 'checkbox' ? checked : value
    });
  };

  const addCropPin = (e) => {
    if (cropPins.length < 4) {
      const newPin = {
        id: pinCounter.current++, // Unique ID for each pin
        lat: e.latlng.lat,
        lng: e.latlng.lng
      };
      setCropPins([...cropPins, newPin]);
      console.log("Pin added:", newPin);
    }
  };

  const MapEvents = () => {
    const map = useMap();
    useMapEvents({
      click: addCropPin,
    });

    useEffect(() => {
      mapRef.current = map;
    }, [map]);

    return null;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const farmId = sessionStorage.getItem('farmId');

    const newCrop = {
      farmId: farmId,
      cropSpecies: form.cropSpecies,
      transplantAmount: form.transplantAmount,
      cultivatedAmount: form.cultivatedAmount,
      activeFlag: form.activeFlag,
      status: form.status,
      startDate: form.startDate,
      endDate: form.endDate,
      coordinates: cropPins
    };

    try {
      const response = await axios.post(`http://${APP_SERVER_IP}:${APP_SERVER_PORT}/CropSense/AppServer/CropController/createCrop`, newCrop);
      const cropId = response.data; 
      sessionStorage.setItem('cropId', cropId);
      console.log("Crop created with ID:", cropId);


      const fenceResponse = await axios.post(`http://${APP_SERVER_IP}:${APP_SERVER_PORT}/CropSense/AppServer/FarmFenceController/createFarmFence`, {
        cropId: cropId,
      });
      const fenceId = fenceResponse.data;
      sessionStorage.setItem('cropFenceId', fenceId);
      console.log("Crop fence created with ID:", fenceId);

      const coordinatesData = cropPins.map((pin, index) => ({
        chainId: pin.id,
        fenceId: fenceId,
        fenceType: "Crop",
        lng: pin.lng,
        lat: pin.lat,
      }));
      

      await axios.post(`http://${APP_SERVER_IP}:${APP_SERVER_PORT}/CropSense/AppServer/FenceCoordinateController/createFenceCoordinates`, coordinatesData);
      console.log("Crop Fence coordinates created successfully.");
      console.log(coordinatesData)

    } catch (error) {
      console.error("Error: ", error);
    }
  
    closeCreateModal();
    window.location.reload();
  };

  const handleUpdateSubmit = async (e) => {
    e.preventDefault();
    const updatedCrop = {
      cropId: form.cropId,
      cropSpecies: form.cropSpecies,
      transplantAmount: form.transplantAmount,
      cultivatedAmount: form.cultivatedAmount,
      activeFlag: form.activeFlag,
      status: form.status,
      startDate: form.startDate,
      endDate: form.endDate
    };

    try {
      const response = await axios.put(`http://${APP_SERVER_IP}:${APP_SERVER_PORT}/CropSense/AppServer/CropController/updateCrop`, updatedCrop);
      setCrops(crops.map(crop => (crop.cropId === updatedCrop.cropId ? updatedCrop : crop)));
      closeUpdateModal();
    } catch (error) {
      console.error('Error updating crop:', error);
      window.alert('Failed to update crop');
    }
  };

  const handleDelete = async (cropId) => {
    try {
      console.log(cropId)
      await axios.delete(`http://${APP_SERVER_IP}:${APP_SERVER_PORT}/CropSense/AppServer/CropController/deleteCrop/${cropId}`);
      alert(`Crop with ID: ${cropId} deleted successfully.`);

      setCrops(crops.filter(crop => crop.cropId !== cropId));
    } catch (error) {
      console.error('Error deleting crop:', error);
      window.alert('Failed to delete crop');
    }
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
        <div className="w-fit">
          <div className="relative mb-8">
           
            <button
              className="absolute top-1/2 right-32 transform -translate-y-1/2 bg-blue-500 text-white py-2 px-4 rounded shadow hover:bg-blue-700"
              onClick={openHistoryModal}
            >
              History
            </button>
            
            <button
              className="absolute top-1/2 right-0 transform -translate-y-1/2 bg-indigo-500 text-white py-2 px-4 rounded shadow hover:bg-indigo-700"
              onClick={openCreateModal}
            >
              Create Crop
            </button>
          </div>
          <h1 className="text-3xl font-bold mb-4">Crops</h1>
          <table className="min-w-full bg-white">
            <thead>
              <tr>
                <th className="py-2 px-4 border-b">ID</th>
                <th className="py-2 px-4 border-b">Species</th>
                <th className="py-2 px-4 border-b">Transplant Amount</th>
                <th className="py-2 px-4 border-b">Cultivated Amount</th>
                <th className="py-2 px-4 border-b">Status</th>
                <th className="py-2 px-4 border-b">Start Date</th>
                <th className="py-2 px-4 border-b">End Date</th>
                <th className="py-2 px-4 border-b">Actions</th>
              </tr>
            </thead>
            <tbody>
              {crops.map((crop) => (
                <tr key={crop.cropId} onClick={() => handleView(crop.cropId)}>
                  <td className="py-4 px-4 border-b text-center">
                    <button className="text-blue-500 underline">
                      {crop.cropId}
                    </button>
                  </td>
                  <td className="py-4 px-4 border-b text-center">{crop.cropSpecies}</td>
                  <td className="py-4 px-4 border-b text-center">{crop.transplantAmount}</td>
                  <td className="py-4 px-4 border-b text-center">{crop.cultivatedAmount}</td>
                  <td className="py-4 px-4 border-b text-center">{crop.status}</td>
                  <td className="py-4 px-4 border-b text-center">{crop.startDate}</td>
                  <td className="py-4 px-4 border-b text-center">{crop.endDate}</td>
                  <td className="py-4 px-4 border-b text-center">
                    <button
                      className="bg-yellow-500 text-white py-1 px-3 rounded mr-4 hover:bg-yellow-700"
                      onClick={() => openUpdateModal(crop)}
                    >
                      Update
                    </button>
                    <button
                      className="bg-red-500 text-white py-1 px-3 rounded hover:bg-red-700"
                      onClick={() => handleDelete(crop.cropId)}
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

      {/* Modal for crop history */}
      <Modal
        isOpen={isHistoryModalOpen}
        onRequestClose={closeHistoryModal}
        contentLabel="Crop History"
        className="fixed inset-0 flex items-center justify-center bg-gray-800 bg-opacity-75"
        ariaHideApp={false}
      >
        <div className="bg-white p-6 rounded-lg w-1/2 max-h-full overflow-auto">
          <h2 className="text-3xl mb-4 font-bold">Crop History</h2>
          <table className="min-w-full bg-white">
            <thead>
              <tr>
                <th className="py-2 px-4 border-b">ID</th>
                <th className="py-2 px-4 border-b">Species</th>
                <th className="py-2 px-4 border-b">Transplant Amount</th>
                <th className="py-2 px-4 border-b">Cultivated Amount</th>
                <th className="py-2 px-4 border-b">Status</th>
                <th className="py-2 px-4 border-b">Start Date</th>
                <th className="py-2 px-4 border-b">End Date</th>
              </tr>
            </thead>
            <tbody>
              {historyData.map((crop) => (
                <tr key={crop.cropId}>
                  <td className="py-4 px-4 border-b text-center">{crop.cropId}</td>
                  <td className="py-4 px-4 border-b text-center">{crop.cropSpecies}</td>
                  <td className="py-4 px-4 border-b text-center">{crop.transplantAmount}</td>
                  <td className="py-4 px-4 border-b text-center">{crop.cultivatedAmount}</td>
                  <td className="py-4 px-4 border-b text-center">{crop.status}</td>
                  <td className="py-4 px-4 border-b text-center">{crop.startDate}</td>
                  <td className="py-4 px-4 border-b text-center">{crop.endDate}</td>
                </tr>
              ))}
            </tbody>
          </table>
          <button
            onClick={closeHistoryModal}
            className="mt-4 bg-red-500 text-white py-2 px-4 rounded shadow hover:bg-red-700"
          >
            Close
          </button>
        </div>
      </Modal>

  
      <Modal
        isOpen={isCreateModalOpen}
        onRequestClose={closeCreateModal}
        contentLabel="Create Crop"
        className="fixed inset-0 flex items-center justify-center bg-gray-800 bg-opacity-75"
        ariaHideApp={false}
      >
        <div className="bg-white p-6 rounded-lg w-1/2 max-h-full overflow-auto">
          <h2 className="text-3xl mb-4 font-bold">Create Crop</h2>
          <form onSubmit={handleSubmit}>
            <div className="mt-6">
              <MapContainer center={mapCenter} zoom={14} style={{ height: '400px', width: '100%' }} ref={mapRef}>
                <TileLayer
                  url="http://{s}.google.com/vt/lyrs=s&x={x}&y={y}&z={z}"
                  subdomains={['mt0', 'mt1', 'mt2', 'mt3']}
                />
                <MapEvents />
                {cropPins.map((pin, index) => (
                  <Marker key={index} position={pin} icon={customIcon} />
                ))}
                {cropPins.length > 2 && (
                  <Polygon positions={cropPins} color="blue" />
                )}
              </MapContainer>
            </div>
            <div className="mb-4 mt-5 w-1/2">
              <label className="block text-gray-700">Crop Species</label>
              <input
                type="text"
                name="cropSpecies"
                value={form.cropSpecies}
                onChange={handleChange}
                className="w-full px-3 py-2 border rounded"
                required
              />
            </div>
            <div className="flex space-x-6 ">
            <div className="mb-4 w-fit">
              <label className="block text-gray-700">Transplant Amount</label>
              <input
                type="number"
                name="transplantAmount"
                value={form.transplantAmount}
                onChange={handleChange}
                className="w-full px-3 py-2 border rounded"
                required
              />
            </div>
            <div className="mb-4 w-fit">
              <label className="block text-gray-700">Cultivated Amount</label>
              <input
                type="number"
                name="cultivatedAmount"
                value={form.cultivatedAmount}
                onChange={handleChange}
                className="w-full px-3 py-2 border rounded"
                required
              />
            </div>
              <div className="mb-4">
              <label className="block text-gray-700">Active</label>
              <input
                type="checkbox"
                name="activeFlag"
                checked={form.activeFlag}
                onChange={handleChange}
                className="mr-2"
              />
            </div>
            <div className="mb-4 w-1/4">
              <label className="block text-gray-700">Status</label>
              <input
                type="text"
                name="status"
                value={form.status}
                onChange={handleChange}
                className="w-full px-3 py-2 border rounded"
                required
              />
            </div>
            </div>

            <div className="flex space-x-4">
              <div className="mb-4 w-1/2">
              <label className="block text-gray-700">Start Date</label>
              <input
                type="date"
                name="startDate"
                value={form.startDate}
                onChange={handleChange}
                className="w-full px-3 py-2 border rounded"
                required
              />
            </div>
            <div className="mb-4 w-1/2">
              <label className="block text-gray-700">End Date</label>
              <input
                type="date"
                name="endDate"
                value={form.endDate}
                onChange={handleChange}
                className="w-full px-3 py-2 border rounded"
                required
              />
            </div>

            </div>

            <button
              type="submit"
              className="mt-4 bg-indigo-500 text-white py-2 px-4 rounded shadow hover:bg-indigo-700"
            >
              Create Crop
            </button>
            <button
              type="button"
              onClick={closeCreateModal}
              className="ml-4 bg-gray-500 text-white py-2 px-4 rounded shadow hover:bg-gray-700"
            >
              Cancel
            </button>
          </form>
        </div>
      </Modal>

  
      <Modal
        isOpen={isUpdateModalOpen}
        onRequestClose={closeUpdateModal}
        contentLabel="Update Crop"
        className="fixed inset-0 flex items-center justify-center bg-gray-800 bg-opacity-75"
        ariaHideApp={false}
      >
        <div className="bg-white p-6 rounded-lg w-1/2 max-h-full overflow-auto">
          <h2 className="text-3xl mb-4 font-bold">Update Crop</h2>
          <form onSubmit={handleUpdateSubmit}>
            <div className="mb-4 mt-5 w-1/2">
              <label className="block text-gray-700">Crop Species</label>
              <input
                type="text"
                name="cropSpecies"
                value={form.cropSpecies}
                onChange={handleChange}
                className="w-full px-3 py-2 border rounded"
                required
              />
            </div>
            <div className="flex space-x-6 ">
            <div className="mb-4 w-1/5">
              <label className="block text-gray-700">Transplant Amount</label>
              <input
                type="number"
                name="transplantAmount"
                value={form.transplantAmount}
                onChange={handleChange}
                className="w-full px-3 py-2 border rounded"
                required
              />
            </div>
            <div className="mb-4 w-1/5">
              <label className="block text-gray-700">Cultivated Amount</label>
              <input
                type="number"
                name="cultivatedAmount"
                value={form.cultivatedAmount}
                onChange={handleChange}
                className="w-full px-3 py-2 border rounded"
                required
              />
            </div>
              <div className="mb-4">
              <label className="block text-gray-700">Active</label>
              <input
                type="checkbox"
                name="activeFlag"
                checked={form.activeFlag}
                onChange={handleChange}
                className="mr-2"
              />
            </div>
            <div className="mb-4 w-1/4">
              <label className="block text-gray-700">Status</label>
              <input
                type="text"
                name="status"
                value={form.status}
                onChange={handleChange}
                className="w-full px-3 py-2 border rounded"
                required
              />
            </div>
            </div>

            <div className="flex space-x-4">
              <div className="mb-4 w-1/2">
              <label className="block text-gray-700">Start Date</label>
              <input
                type="date"
                name="startDate"
                value={form.startDate}
                onChange={handleChange}
                className="w-full px-3 py-2 border rounded"
                required
              />
            </div>
            <div className="mb-4 w-1/2">
              <label className="block text-gray-700">End Date</label>
              <input
                type="date"
                name="endDate"
                value={form.endDate}
                onChange={handleChange}
                className="w-full px-3 py-2 border rounded"
                required
              />
            </div>

            </div>

            <button
              type="submit"
              className="mt-4 bg-yellow-500 text-white py-2 px-4 rounded shadow hover:bg-yellow-700"
            >
              Update Crop
            </button>
            <button
              type="button"
              onClick={closeUpdateModal}
              className="ml-4 bg-red-500 text-white py-2 px-4 rounded shadow hover:bg-red-700"
            >
              Cancel
            </button>
          </form>
        </div>
      </Modal>
    </div>
  );
}

export default Crops;