import React, { useState, useEffect } from "react";
import axios from 'axios';
import Sidebar from "./Sidebar";
import Modal from 'react-modal';

function Crops() {
  const [crops, setCrops] = useState([]);
  const [isHistoryModalOpen, setIsHistoryModalOpen] = useState(false);
  const [historyData, setHistoryData] = useState([]);
  const [isCreateModalOpen, setIsCreateModalOpen] = useState(false);
  const [form, setForm] = useState({
    cropSpecies: '',
    transplantAmount: '',
    cultivatedAmount: '',
    activeFlag: false,
    status: '',
    startDate: '',
    endDate: ''
  });

  useEffect(() => {
    const farmId = sessionStorage.getItem('farmId');
    axios.get(`http://localhost:8080/CropSense/AppServer/CropController/fetchActiveCropsForFarm?farmId=${farmId}`)
      .then(response => {
        setCrops(response.data);
      })
      .catch(error => {
        console.error('There was an error fetching the crops data!', error);
      });
  }, []);

  const openHistoryModal = () => {
    const farmId = sessionStorage.getItem('farmId');
    axios.get(`http://localhost:8080/CropSense/AppServer/CropController/fetchAllCropsForFarm?farmId=${farmId}`)
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
    window.location.reload();
    setForm({
      cropSpecies: '',
      transplantAmount: '',
      cultivatedAmount: '',
      activeFlag: false,
      status: '',
      startDate: '',
      endDate: ''
    });
  };

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setForm({
      ...form,
      [name]: type === 'checkbox' ? checked : value
    });
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
      endDate: form.endDate
    };

    try {
      const response = await axios.post('http://localhost:8080/CropSense/AppServer/CropController/createCrop', newCrop);
      setCrops([...crops, response.data]);
      window.location.reload();
      closeCreateModal();
    } catch (error) {
      console.error('Error creating crop:', error);
      window.alert('Failed to create crop');
    }
  };

  return (
    <div className="flex h-screen">
      <Sidebar />
      <div className="flex-1 flex justify-center items-start pt-40">
        <div className="w-3/4">
          <div className="relative mb-8">
            {/* History Button */}
            <button
              className="absolute top-1/2 right-32 transform -translate-y-1/2 bg-indigo-500 text-white py-2 px-4 rounded shadow hover:bg-indigo-700"
              onClick={openHistoryModal}
            >
              History
            </button>
            {/* Create Crop Button */}
            <button
              className="absolute top-1/2 right-0 transform -translate-y-1/2 bg-green-500 text-white py-2 px-4 rounded shadow hover:bg-green-700"
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
              </tr>
            </thead>
            <tbody>
              {crops.map((crop) => (
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
        <div className="bg-white p-6 rounded-lg w-3/4">
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

      {/* Modal for creating crop */}
      <Modal
        isOpen={isCreateModalOpen}
        onRequestClose={closeCreateModal}
        contentLabel="Create Crop"
        className="fixed inset-0 flex items-center justify-center bg-gray-800 bg-opacity-75"
        ariaHideApp={false}
      >
        <div className="bg-white p-6 rounded-lg w-3/4">
          <h2 className="text-3xl mb-4 font-bold">Create Crop</h2>
          <form onSubmit={handleSubmit}>
            <div className="mb-4">
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
            <div className="mb-4">
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
            <div className="mb-4">
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
            <div className="mb-4">
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
            <div className="mb-4">
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
            <div className="mb-4">
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
            <button
              type="submit"
              className="bg-green-500 text-white py-2 px-4 rounded shadow hover:bg-green-700"
            >
              Create Crop
            </button>
            <button
              type="button"
              onClick={closeCreateModal}
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
