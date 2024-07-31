import React, {useEffect, useRef, useState} from "react";
import Sidebar from '../Components/Sidebar';
import {MapContainer, Marker, Polygon, TileLayer, useMap, useMapEvents} from "react-leaflet";
import L from "leaflet";
import 'leaflet.gridlayer.googlemutant';
import axios from "axios";
import DonutChart from './DonutChart';



function UserDashboard() {
  const [data, setData] = useState(null);
    const [pins, setPins] = useState([]);
    const [mapCenter, setMapCenter] = useState([36.9668383, -121.5013172])
    const [analysisResults, setAnalysisResults] = useState({ cropsIdentified: 0, diseaseRate: 0 });
    const mapRef = useRef(null);
    const pinCounter = useRef(0);
    const [form, setForm] = useState({
        id: '',
        name: '',
        street: '',
        city: '',
        zip: '',
    });

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

    const performAnalysis = () => {
        // Perform analysis logic here
        // This is just a mock result for demonstration
        setAnalysisResults({ cropsIdentified: 4, diseaseRate: 5.5 });
    };

    const reselectField = () => {
        setPins([]);
        setAnalysisResults({ cropsIdentified: 0, diseaseRate: 0 });
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
      <div className="flex flex-col bg-gray-100">
          <div className="flex">
              <div className="w-4/5 mt-6">
                  <MapContainer center={mapCenter} zoom={15} style={{ height: '70vh', width: '100%' }} ref={mapRef}>
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
              <div className="w-1/5 p-6">
                  <h2 className="text-2xl font-bold mb-4">Farm Analysis</h2>

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
                      <div className="mb-2 w-3/5 mr-1">
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

                  <div className="flex flex-col mt-4 gap-4">
                          <button
                              type="button"
                              onClick={handleCenterFarm}
                              className="bg-blue-500 text-white py-2 px-1 rounded shadow hover:bg-blue-700"
                          >
                              Center Farm
                          </button>
                          <button
                              className="bg-gray-500 text-white py-2 px-1 rounded shadow hover:bg-gray-700"
                              onClick={reselectField}
                          >
                              Reselect Farm
                          </button>

                      <button
                          className="bg-blue-500 text-white py-2 px-4 rounded shadow hover:bg-blue-700"
                          onClick={performAnalysis}
                      >
                          Perform Analysis
                      </button>
                  </div>
                  <div className="mt-6">
                      <h3 className="text-xl font-semibold">Analysis Results</h3>
                      <p className="mt-4"><strong>Crops Identified:</strong> {analysisResults.cropsIdentified}</p>
                      <p className="mt-2"><strong>Disease Rate:</strong> {analysisResults.diseaseRate}%</p>
                  </div>
              </div>
          </div>



          <div className="flex mt-8 ml-8">
              <div className="flex-1 p-4 mt-6 w-1/3">
                  <div className="relative flex flex-col bg-clip-border rounded-xl bg-white text-gray-700 shadow-md">
                      <div className="bg-clip-border mx-4 rounded-xl overflow-hidden bg-gradient-to-tr from-blue-600 to-blue-400 text-white shadow-blue-500/40 shadow-lg absolute -mt-4 grid h-16 w-16 place-items-center">
                          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" aria-hidden="true" className="w-6 h-6 text-white">
                              {/*<path d="M12 7.5a2.25 2.25 0 100 4.5 2.25 2.25 0 000-4.5z"></path>*/}
                              <path fillRule="evenodd" d="M1.5 4.875C1.5 3.839 2.34 3 3.375 3h17.25c1.035 0 1.875.84 1.875 1.875v9.75c0 1.036-.84 1.875-1.875 1.875H3.375A1.875 1.875 0 011.5 14.625v-9.75zM8.25 9.75a3.75 3.75 0 117.5 0 3.75 3.75 0 01-7.5 0zM18.75 9a.75.75 0 00-.75.75v.008c0 .414.336.75.75.75h.008a.75.75 0 00.75-.75V9.75a.75.75 0 00-.75-.75h-.008zM4.5 9.75A.75.75 0 015.25 9h.008a.75.75 0 01.75.75v.008a.75.75 0 01-.75.75H5.25a.75.75 0 01-.75-.75V9.75z" clipRule="evenodd"></path>
                              <path d="M2.25 18a.75.75 0 000 1.5c5.4 0 10.63.722 15.6 2.075 1.19.324 2.4-.558 2.4-1.82V18.75a.75.75 0 00-.75-.75H2.25z"></path>
                          </svg>
                      </div>
                      <div className="p-4 text-right">
                          <p className="block antialiased font-sans text-2xl leading-normal font-normal text-blue-gray-600">No. of Farms</p>
                          <h3 className="mt-4 block antialiased tracking-normal font-sans text-2xl font-semibold leading-snug text-blue-gray-900">3</h3>
                      </div>
                  </div>

                  <div className="relative flex flex-col bg-clip-border rounded-xl bg-white text-gray-700 shadow-md mt-8">
                      <div className="bg-clip-border mx-4 rounded-xl overflow-hidden bg-gradient-to-tr from-orange-600 to-orange-400 text-white shadow-orange-500/40 shadow-lg absolute -mt-4 grid h-16 w-16 place-items-center">
                          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" aria-hidden="true" className="w-6 h-6 text-white">
                              <path d="M18.375 2.25c-1.035 0-1.875.84-1.875 1.875v15.75c0 1.035.84 1.875 1.875 1.875h.75c1.035 0 1.875-.84 1.875-1.875V4.125c0-1.036-.84-1.875-1.875-1.875h-.75zM9.75 8.625c0-1.036.84-1.875 1.875-1.875h.75c1.036 0 1.875.84 1.875 1.875v11.25c0 1.035-.84 1.875-1.875 1.875h-.75a1.875 1.875 0 01-1.875-1.875V8.625zM3 13.125c0-1.036.84-1.875 1.875-1.875h.75c1.036 0 1.875.84 1.875 1.875v6.75c0 1.035-.84 1.875-1.875 1.875h-.75A1.875 1.875 0 013 19.875v-6.75z"></path>
                          </svg>
                      </div>
                      <div className="p-4 text-right">
                          <p className="block antialiased font-sans text-2xl leading-normal font-normal text-blue-gray-600">No. of Crops</p>
                          <h3 className="mt-4 block antialiased tracking-normal font-sans text-2xl font-semibold leading-snug text-blue-gray-900">85%</h3>
                      </div>
                  </div>
              </div>

              <div className="lg:h-full py-6  text-gray-600 rounded-xl border border-gray-200 bg-white w-1/2 ml-8 mr-8 mt-10 mb-4 flex flex-col justify-center items-center shadow-md">
                  <DonutChart />

              </div>
          </div>

      </div>

  );
}

export default UserDashboard;