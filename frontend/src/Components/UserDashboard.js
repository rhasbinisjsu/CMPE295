import React, { useState, useEffect } from "react";
import axios from 'axios';
import { Routes, Route, useHistory } from 'react-router-dom';
import Table from 'react-bootstrap/Table';
import Button from 'react-bootstrap/Button';
import Dropdown from 'react-bootstrap/Dropdown';
import Sidebar from '../Components/Sidebar';

function UserDashboard() {
  const history = useHistory();
  const [data, setData] = useState(null);

  return (
    <div className="flex">
      <Sidebar />
      <div className="flex-1 p-10">
        <h1 className="text-3xl font-bold mb-6">User Dashboard</h1>
        <Table>
          <thead className="header">
            <tr>
              <th>Crop #</th>
              <th>Name</th>
              <th>Location</th>
              <th>Vegetation</th>
              <th>Type</th>
              <th>Date</th>
              <th>Time</th>
              <th>Status</th>
            </tr>
          </thead>
          <tbody>
            {/* Map over your data to create rows */}
            {data && data.map((item, index) => (
              <tr key={index}>
                <td>{item.cropNumber}</td>
                <td>{item.name}</td>
                <td>{item.location}</td>
                <td>{item.vegetation}</td>
                <td>{item.type}</td>
                <td>{item.date}</td>
                <td>{item.time}</td>
                <td>{item.status}</td>
              </tr>
            ))}
          </tbody>
        </Table>
        <div className="mt-4 space-x-4">
          <Button>Sign Out</Button>
          <Button>Soil Health</Button>
          <Button>Show All Crops</Button>
        </div>
      </div>
    </div>
  );
}

export default UserDashboard;
