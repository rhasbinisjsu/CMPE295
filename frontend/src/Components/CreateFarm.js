import React, { useState } from 'react';
import axios from 'axios';
import Sidebar from './Sidebar';

const CreateFarm = () => {
  const [form, setForm] = useState({
    farmName: '',
    addressLine1: '',
    addressLine2: '',
    addressCity: '',
    addressState: '',
    addressZip: '',
    addressCountry: '',
  });

  map = new google.maps.Map(htmlMapContainer,
    {
        center: initLocation,
        zoom: 15
    }
);
  // Handler for form submission
  const handleSubmit = async (e) => {
    e.preventDefault();

    const newFarm = {
      ownerId: sessionStorage.getItem('TenantId'), // Adjust based on your storage method
      name: form.farmName,
      address: {
        line1: form.addressLine1,
        line2: form.addressLine2,
        city: form.addressCity,
        state: form.addressState,
        zip: form.addressZip,
        country: form.addressCountry,
      },
    };

    try {
      const response = await axios.post('http://localhost:8080/CropSense/AppServer/FarmController/createFarm', newFarm);
      console.log('Farm creation successful:', response.data);

      // Assuming success handling includes fetching the newly created farm ID
      fetchFarmByName(sessionStorage.getItem('TenantId'), form.farmName);
    } catch (error) {
      console.error('Error creating farm:', error);
      // Handle error, show user alert, etc.
      window.alert('Failed to create the farm');
    }
  };

  // Fetch the newly created farm by name
  const fetchFarmByName = async (tenant, farmName) => {
    try {
      const response = await axios.get(`http://localhost:8080/CropSense/AppServer/FarmController/fetchFarmByOwnerAndName?ownerId=${tenant}&farmName=${farmName}`);
      const farmId = response.data.id;
      console.log('New farm ID:', farmId);

      // Proceed to create the farm fence
      createFarmFence(farmId);
    } catch (error) {
      console.error('Error fetching new farm:', error);
    }
  };

  // Create farm fence
  const createFarmFence = async (farmId) => {
    try {
      const response = await axios.post('http://localhost:8080/CropSense/AppServer/FarmFenceController/createFarmFence', { farmId });
      console.log('Farm fence creation successful:', response.data);
      window.alert('Successfully created farm and fence!');
    } catch (error) {
      console.error('Error creating farm fence:', error);
      window.alert('Failed to create farm fence');
    }
  };

  // Center map at the provided address
  const centerMapAtFarm = () => {
    const address = `${form.addressLine1} ${form.addressLine2} ${form.addressCity} ${form.addressState} ${form.addressZip} ${form.addressCountry}`;
    const geocoder = new window.google.maps.Geocoder();

    geocoder.geocode({ address }, (results, status) => {
      if (status === 'OK') {
        const { location } = results[0].geometry;
        map.setCenter(location);
      } else {
        console.error('Geocode was not successful for the following reason:', status);
      }
    });
  };

  // Handler for changing form fields
  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm({ ...form, [name]: value });
  };

  return (
    <div>
      <h1>Create a Farm</h1>
      <form onSubmit={handleSubmit}>
        <label><b>Farm Name:</b></label><br />
        <input type="text" name="farmName" value={form.farmName} onChange={handleChange} required /><br /><br />

        <label><b>Address:</b></label><br />
        <label>Line 1:</label><br />
        <input type="text" name="addressLine1" value={form.addressLine1} onChange={handleChange} /><br />
        <label>Line 2:</label><br />
        <input type="text" name="addressLine2" value={form.addressLine2} onChange={handleChange} /><br />
        <label>City:</label><br />
        <input type="text" name="addressCity" value={form.addressCity} onChange={handleChange} /><br />
        <label>State:</label><br />
        <input type="text" name="addressState" value={form.addressState} onChange={handleChange} /><br />
        <label>Zip:</label><br />
        <input type="text" name="addressZip" value={form.addressZip} onChange={handleChange} /><br />
        <label>Country:</label><br />
        <input type="text" name="addressCountry" value={form.addressCountry} onChange={handleChange} /><br /><br />

        <input type="submit" value="Create Farm" />
      </form>

      <br />
      <b>Mark Location GeoFence:</b><br /><br />
      <button onClick={centerMapAtFarm}>Center map</button><br /><br />
      <div id="farmMap" style={{ width: '100%', height: '400px' }}></div>
    </div>
  );
};

export default CreateFarm;
