import React, { useState, useEffect } from "react";

import Sidebar from "./Sidebar";
import UserDashboard from "./UserDashboard";
function Farm() {
  const [data, setData] = useState([]);

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
    // Implement view logic here
  };

  const handleDelete = (id) => {
    alert(`Delete Farm with ID: ${id}`);
    // Implement delete logic here
  };

  return (
    <div className="flex h-screen ">
      <Sidebar />
      <div className="flex-1 flex justify-center items-start pt-40">
        <div className="w-3/4">
          <div className="relative">
          {/* Create Farm Button */}
          <button
            className="absolute top-1/2 right-0 transform -translate-y-1/2 bg-indigo-500 text-white py-2 px-4 rounded shadow hover:bg-indigo-700"
            onClick={() => alert('Create Farm Button Clicked')}
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
    </div>
  );
}

export default Farm;
