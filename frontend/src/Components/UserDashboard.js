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

      <div className="flex-1 p-4 mt-10 ">
        {/* Boxes Container */}
        <div className="grid gap-6 grid-cols-1 md:grid-cols-2 xl:grid-cols-4 mb-12">
          <div className="relative flex flex-col bg-clip-border rounded-xl bg-white text-gray-700 shadow-md">
            <div className="bg-clip-border mx-4 rounded-xl overflow-hidden bg-gradient-to-tr from-blue-600 to-blue-400 text-white shadow-blue-500/40 shadow-lg absolute -mt-4 grid h-16 w-16 place-items-center">
              <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" aria-hidden="true" className="w-6 h-6 text-white">
                <path d="M12 7.5a2.25 2.25 0 100 4.5 2.25 2.25 0 000-4.5z"></path>
                <path fillRule="evenodd" d="M1.5 4.875C1.5 3.839 2.34 3 3.375 3h17.25c1.035 0 1.875.84 1.875 1.875v9.75c0 1.036-.84 1.875-1.875 1.875H3.375A1.875 1.875 0 011.5 14.625v-9.75zM8.25 9.75a3.75 3.75 0 117.5 0 3.75 3.75 0 01-7.5 0zM18.75 9a.75.75 0 00-.75.75v.008c0 .414.336.75.75.75h.008a.75.75 0 00.75-.75V9.75a.75.75 0 00-.75-.75h-.008zM4.5 9.75A.75.75 0 015.25 9h.008a.75.75 0 01.75.75v.008a.75.75 0 01-.75.75H5.25a.75.75 0 01-.75-.75V9.75z" clipRule="evenodd"></path>
                <path d="M2.25 18a.75.75 0 000 1.5c5.4 0 10.63.722 15.6 2.075 1.19.324 2.4-.558 2.4-1.82V18.75a.75.75 0 00-.75-.75H2.25z"></path>
              </svg>
            </div>
            <div className="p-4 text-right">
              <p className="block antialiased font-sans text-m leading-normal font-normal text-blue-gray-600">No. of Farms</p>
              <h4 className="block antialiased tracking-normal font-sans text-2xl font-semibold leading-snug text-blue-gray-900">3</h4>
            </div>
            <div className="border-t border-blue-gray-50 p-4">
              <p className="block antialiased font-sans text-base leading-relaxed font-normal text-blue-gray-600">
                <strong className="text-green-500">+25%</strong>&nbsp;than last year
              </p>
            </div>
          </div>
          <div className="relative flex flex-col bg-clip-border rounded-xl bg-white text-gray-700 shadow-md">
            <div className="bg-clip-border mx-4 rounded-xl overflow-hidden bg-gradient-to-tr from-pink-600 to-pink-400 text-white shadow-pink-500/40 shadow-lg absolute -mt-4 grid h-16 w-16 place-items-center">
              <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" aria-hidden="true" className="w-6 h-6 text-white">
                <path fillRule="evenodd" d="M7.5 6a4.5 4.5 0 119 0 4.5 4.5 0 01-9 0zM3.751 20.105a8.25 8.25 0 0116.498 0 .75.75 0 01-.437.695A18.683 18.683 0 0112 22.5c-2.786 0-5.433-.608-7.812-1.7a.75.75 0 01-.437-.695z" clipRule="evenodd"></path>
              </svg>
            </div>
            <div className="p-4 text-right">
              <p className="block antialiased font-sans text-sm leading-normal font-normal text-blue-gray-600">No. of Crops</p>
              <h4 className="block antialiased tracking-normal font-sans text-2xl font-semibold leading-snug text-blue-gray-900">12</h4>
            </div>
            <div className="border-t border-blue-gray-50 p-4">
              <p className="block antialiased font-sans text-base leading-relaxed font-normal text-blue-gray-600">
                <strong className="text-green-500">+13%</strong>&nbsp;than last year
              </p>
            </div>
          </div>
          <div className="relative flex flex-col bg-clip-border rounded-xl bg-white text-gray-700 shadow-md">
            <div className="bg-clip-border mx-4 rounded-xl overflow-hidden bg-gradient-to-tr from-green-600 to-green-400 text-white shadow-green-500/40 shadow-lg absolute -mt-4 grid h-16 w-16 place-items-center">
              <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" aria-hidden="true" className="w-6 h-6 text-white">
                <path d="M6.25 6.375a4.125 4.125 0 118.25 0 4.125 4.125 0 01-8.25 0zM3.25 19.125a7.125 7.125 0 0114.25 0v.003l-.001.119a.75.75 0 01-.363.63 13.067 13.067 0 01-6.761 1.873c-2.472 0-4.786-.684-6.76-1.873a.75.75 0 01-.364-.63l-.001-.122zM19.75 7.5a.75.75 0 00-1.5 0v2.25H16a.75.75 0 000 1.5h2.25v2.25a.75.75 0 001.5 0v-2.25H22a.75.75 0 000-1.5h-2.25V7.5z"></path>
              </svg>
            </div>
            <div className="p-4 text-right">
              <p className="block antialiased font-sans text-sm leading-normal font-normal text-blue-gray-600">Soil Health Index</p>
              <h4 className="block antialiased tracking-normal font-sans text-2xl font-semibold leading-snug text-blue-gray-900">89</h4>
            </div>
            <div className="border-t border-blue-gray-50 p-4">
              <p className="block antialiased font-sans text-base leading-relaxed font-normal text-blue-gray-600">
                <strong className="text-green-500">Healthy</strong>&nbsp;
              </p>
            </div>
          </div>
          <div className="relative flex flex-col bg-clip-border rounded-xl bg-white text-gray-700 shadow-md">
            <div className="bg-clip-border mx-4 rounded-xl overflow-hidden bg-gradient-to-tr from-orange-600 to-orange-400 text-white shadow-orange-500/40 shadow-lg absolute -mt-4 grid h-16 w-16 place-items-center">
              <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" aria-hidden="true" className="w-6 h-6 text-white">
                <path d="M18.375 2.25c-1.035 0-1.875.84-1.875 1.875v15.75c0 1.035.84 1.875 1.875 1.875h.75c1.035 0 1.875-.84 1.875-1.875V4.125c0-1.036-.84-1.875-1.875-1.875h-.75zM9.75 8.625c0-1.036.84-1.875 1.875-1.875h.75c1.036 0 1.875.84 1.875 1.875v11.25c0 1.035-.84 1.875-1.875 1.875h-.75a1.875 1.875 0 01-1.875-1.875V8.625zM3 13.125c0-1.036.84-1.875 1.875-1.875h.75c1.036 0 1.875.84 1.875 1.875v6.75c0 1.035-.84 1.875-1.875 1.875h-.75A1.875 1.875 0 013 19.875v-6.75z"></path>
              </svg>
            </div>
            <div className="p-4 text-right">
              <p className="block antialiased font-sans text-sm leading-normal font-normal text-blue-gray-600">Growth Rate</p>
              <h4 className="block antialiased tracking-normal font-sans text-2xl font-semibold leading-snug text-blue-gray-900">85%</h4>
            </div>
            <div className="border-t border-blue-gray-50 p-4">
              <p className="block antialiased font-sans text-base leading-relaxed font-normal text-blue-gray-600">
                <strong className="text-green-500">Normal</strong>&nbsp;
              </p>
            </div>
          </div>
        </div>

        {/* Graphs Container */}
        <div className="grid gap-6 md:grid-cols-2 lg:grid-cols-2">
          <div>
            <div className="h-full py-6 px-6 rounded-xl border border-gray-200 bg-white">
              <h5 className="text-xl text-gray-700">Produce</h5>
              <div className="my-8">
                <h1 className="text-5xl font-bold text-green-600">+15%</h1>
                <span className="text-gray-500">Compared to last year </span>
              </div>
              <svg className="w-full" viewBox="0 0 218 69" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path d="M0 67.5C27.8998 67.5 24.6002 15 52.5 15C80.3998 15 77.1002 29 105 29C132.9 29 128.6 52 156.5 52C184.4 52 189.127 63.8158 217.027 63.8158" stroke="url(#paint0_linear_622:13664)" strokeWidth="3" strokeLinecap="round" />
                <path d="M0 67.5C27.2601 67.5 30.7399 31 58 31C85.2601 31 80.7399 2 108 2C135.26 2 134.74 43 162 43C189.26 43 190.74 63.665 218 63.665" stroke="url(#paint1_linear_622:13664)" strokeWidth="3" strokeLinecap="round" />
                <defs>
                  <linearGradient id="paint0_linear_622:13664" x1="217.027" y1="15" x2="7.91244" y2="15" gradientUnits="userSpaceOnUse">
                    <stop stopColor="#4DFFDF" />
                    <stop offset="1" stopColor="#4DA1FF" />
                  </linearGradient>
                  <linearGradient id="paint1_linear_622:13664" x1="218" y1="18.3748" x2="5.4362" y2="18.9795" gradientUnits="userSpaceOnUse">
                    <stop stopColor="#E323FF" />
                    <stop offset="1" stopColor="#7517F8" />
                  </linearGradient>
                </defs>
              </svg>
              <table className="mt-6 -mb-2 w-full text-gray-600">
                <tbody>
                  <tr>
                    <td className="py-2">2024</td>
                    <td className="text-gray-500">96 tons</td>
                    <td>
                      <svg className="w-16 ml-auto" viewBox="0 0 68 21" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <rect opacity="0.4" width="17" height="21" rx="1" fill="#e4e4f2" />
                        <rect opacity="0.4" x="19" width="14" height="21" rx="1" fill="#e4e4f2" />
                        <rect opacity="0.4" x="35" width="14" height="21" rx="1" fill="#e4e4f2" />
                        <rect opacity="0.4" x="51" width="17" height="21" rx="1" fill="#e4e4f2" />
                        <path d="M0 7C8.62687 7 7.61194 16 17.7612 16C27.9104 16 25.3731 9 34 9C42.6269 9 44.5157 5 51.2537 5C57.7936 5 59.3731 14.5 68 14.5" stroke="url(#paint0_linear_622:13631)" strokeWidth="2" strokeLinejoin="round" />
                        <defs>
                          <linearGradient id="paint0_linear_622:13631" x1="68" y1="7.74997" x2="1.69701" y2="8.10029" gradientUnits="userSpaceOnUse">
                            <stop stopColor="#E323FF" />
                            <stop offset="1" stopColor="#7517F8" />
                          </linearGradient>
                        </defs>
                      </svg>
                    </td>
                  </tr>
                  <tr>

                                <td class="py-2">2023</td>
                                <td class="text-gray-500">80 tons</td>
                                <td>
                                    <svg class="w-16 ml-auto" viewBox="0 0 68 21" fill="none" xmlns="http://www.w3.org/2000/svg">
                                        <rect opacity="0.4" width="17" height="21" rx="1" fill="#e4e4f2"/>
                                        <rect opacity="0.4" x="19" width="14" height="21" rx="1" fill="#e4e4f2"/>
                                        <rect opacity="0.4" x="35" width="14" height="21" rx="1" fill="#e4e4f2"/>
                                        <rect opacity="0.4" x="51" width="17" height="21" rx="1" fill="#e4e4f2"/>
                                        <path d="M0 12.929C8.69077 12.929 7.66833 9.47584 17.8928 9.47584C28.1172 9.47584 25.5611 15.9524 34.2519 15.9524C42.9426 15.9524 44.8455 10.929 51.6334 10.929C58.2217 10.929 59.3092 5 68 5" stroke="url(#paint0_linear_622:13640)" stroke-width="2" stroke-linejoin="round"/>
                                        <defs>
                                        <linearGradient id="paint0_linear_622:13640" x1="34" y1="5" x2="34" y2="15.9524" gradientUnits="userSpaceOnUse">
                                        <stop stop-color="#8AFF6C"/>
                                        <stop offset="1" stop-color="#02C751"/>
                                        </linearGradient>
                                        </defs>
                                    </svg>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <div>
                <div className="lg:h-full py-8 px-6 text-gray-600 rounded-xl border border-gray-200 bg-white">
                    <DonutChart/>
                    <div>
                        <h5 className="text-xl text-gray-600 text-center">Crops</h5>
                        <div className="mt-2 flex justify-center gap-4">
                            <h3 className="text-3xl font-bold text-gray-700">58</h3>
                            <div className="flex items-end gap-1 text-green-500">
                                <svg className="w-3" viewBox="0 0 12 15" fill="none" xmlns="http://www.w3.org/2000/svg">
                                    <path d="M6.00001 0L12 8H-3.05176e-05L6.00001 0Z" fill="currentColor"/>
                                </svg>
                                <span>20%</span>
                            </div>
                        </div>
                        <span className="block text-center text-gray-500">Compared to last year 48</span>
                    </div>
                    <table className="w-full text-gray-600">
                        <tbody>
                        {/* Your existing table code */}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

  );
}

export default UserDashboard;