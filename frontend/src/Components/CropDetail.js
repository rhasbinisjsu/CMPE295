// import React, { useState } from 'react';
// import LineGraph from './LineGraph';
// import { Tab } from '@headlessui/react';
// import Chart from 'react-apexcharts';
// import { CameraIcon } from '@heroicons/react/24/solid';


// const mockSoilMetricsData = [
//   { title: 'Moisture Levels', data: [10, 20, 30, 40, 50] },
//   { title: 'pH Levels', data: [15, 25, 35, 45, 55] },
//   { title: 'Nitrogen Levels', data: [20, 30, 40, 50, 60] },
//   { title: 'Phosphorous Levels', data: [25, 35, 45, 55, 65] },
//   { title: 'Potassium Levels', data: [30, 40, 50, 60, 70] }
// ];

// const mockDiseaseData = [
//   { disease: 'Disease A', location: { latitude: '35.6895', longitude: '139.6917' } },
//   { disease: 'Disease B', location: { latitude: '34.0522', longitude: '-118.2437' } }
// ];

// const mockCropPercentageData = {
//   series: [
//     { name: 'Crop Percentage', data: [10, 15, 20, 25, 30] }
//   ],
//   categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May']
// };

// const CropDetail = () => {
//   const [selectedMetricIndex, setSelectedMetricIndex] = useState(0);

//   return (
//     <div className="container mx-auto p-6 bg-gray-100">
//       <h1 className="text-3xl font-bold mb-6">Crop Detail</h1>
//       <div className="flex justify-center mt-6">
//         <div className="bg-white p-4 rounded-lg shadow justify-center w-1/2 ">
//         <h2 className="text-2xl font-semibold mb-4">Soil Metrics</h2>
//         <Tab.Group selectedIndex={selectedMetricIndex} onChange={setSelectedMetricIndex}>
//           <Tab.List className="flex space-x-2 mb-4">
//             {mockSoilMetricsData.map((metric, index) => (
//               <Tab
//                 key={index}
//                 className={({ selected }) =>
//                   `px-4 py-2 text-sm font-medium leading-5 text-white ${
//                     selected ? 'bg-blue-500' : 'bg-blue-300'
//                   } rounded-lg focus:outline-none`
//                 }
//               >
//                 {metric.title}
//               </Tab>
//             ))}
//           </Tab.List>
//           <Tab.Panels>
//             {mockSoilMetricsData.map((metric, index) => (
//               <Tab.Panel key={index}>
//                 <LineGraph title={metric.title} data={metric.data} />
//               </Tab.Panel>
//             ))}
//           </Tab.Panels>
//         </Tab.Group>
//       </div>
//       </div>
//       <div className="flex justify-center mt-6">
//         <div className="mt-6 grid grid-cols-1 md:grid-cols-2 gap-6">


//           <div className="bg-white p-4 rounded-lg shadow">
//             <h2 className="text-xl font-semibold mb-4">Latest Detected Diseases</h2>
//             <table className="min-w-full bg-white">
//               <thead>
//                 <tr>
//                   <th className="py-2 px-4 border-b text-left">Disease</th>
//                   <th className="py-2 px-8 border-b text-left">Latitude</th>
//                   <th className="py-2 px-4 border-b text-left">Longitude</th>
//                 </tr>
//               </thead>
//               <tbody>
//                 {mockDiseaseData.map((item, index) => (
//                   <tr key={index}>
//                     <td className="py-2 px-4 border-b">{item.disease}</td>
//                     <td className="py-2 px-8 border-b">{item.location.latitude}</td>
//                     <td className="py-2 px-4 border-b">{item.location.longitude}</td>
//                   </tr>
//                 ))}
//               </tbody>
//             </table>
//           </div>

//         <div className="relative flex flex-col bg-clip-border rounded-xl bg-white text-gray-700 shadow-md">
//         <div className="bg-clip-border mx-4 rounded-xl overflow-hidden bg-gradient-to-tr from-blue-600 to-blue-400 text-white shadow-blue-500/40 shadow-lg absolute -mt-4 grid h-16 w-16 place-items-center">
//           <CameraIcon className="h-6 w-6 text-white" />
//         </div>
//         <div className="p-4 text-right">
//           <p className="block antialiased font-sans text-m leading-normal font-normal text-blue-gray-600">Diseased Crop Percentage</p>
//           <h4 className="block antialiased tracking-normal font-sans text-2xl font-semibold leading-snug text-blue-gray-900">3</h4>
//         </div>
//         <div className="border-t border-blue-gray-50 p-4">
//           <p className="block antialiased font-sans text-base leading-relaxed font-normal text-blue-gray-600">
//             <strong className="text-green-500">+25%</strong>&nbsp;than last year
//           </p>
//         </div>
//       </div>

//       </div>
//       </div>

//       <div className="flex justify-center mt-6">
//         <div className="mt-6 bg-white p-4 rounded-lg shadow h-fit w-1/2">
//         <h2 className="text-2xl font-semibold mb-4">Crop Disease Percentage</h2>
//         <LineGraph  data={mockCropPercentageData.series[0].data} categories={mockCropPercentageData.categories} />
//         </div>
//       </div>


//     </div>
//   );
// };

// export default CropDetail;




// import React, { useState, useEffect } from 'react';
// import LineGraph from './LineGraph'; // Assuming you have this component
// import { Tab } from '@headlessui/react';
// import axios from 'axios';

// const CropDetail = () => {
//   const [selectedMetricIndex, setSelectedMetricIndex] = useState(0);
//   const [soilMetricsData, setSoilMetricsData] = useState(null);

//   useEffect(() => {
//     const cropId = sessionStorage.getItem('cropId') || 9; // Use cropId from session storage or default to 9
//     axios.get(`http://localhost:8081/CropSense/MetricsServer/SoilMetricsController/getIndividualizedMetricsForCrop?cropId=${cropId}`)
//       .then(response => {
//         setSoilMetricsData(response.data);
//       })
//       .catch(error => {
//         console.error('There was an error fetching the soil metrics data!', error);
//       });
//   }, []);

//   if (!soilMetricsData) {
//     return <div>Loading...</div>;
//   }

//   const soilMetricsTabs = [
//     { title: 'Moisture Levels', data: soilMetricsData.moisture },
//     { title: 'pH Levels', data: soilMetricsData.ph },
//     { title: 'Nitrogen Levels', data: soilMetricsData.nitrogen },
//     { title: 'Phosphorous Levels', data: soilMetricsData.phosphorous },
//     { title: 'Potassium Levels', data: soilMetricsData.potassium }
//   ];

//   const formatDataForGraph = (data) => {
//     const categories = Object.keys(data);
//     const seriesData = categories.map(date => parseFloat(data[date]));
//     return { categories, seriesData };
//   };

//   return (
//     <div className="container mx-auto p-6 bg-gray-100">
//       <h1 className="text-3xl font-bold mb-6">Crop Detail</h1>
//       <div className="flex justify-center mt-6">
//         <div className="bg-white p-4 rounded-lg shadow justify-center w-1/2">
//           <h2 className="text-2xl font-semibold mb-4">Soil Metrics</h2>
//           <Tab.Group selectedIndex={selectedMetricIndex} onChange={setSelectedMetricIndex}>
//             <Tab.List className="flex space-x-2 mb-4">
//               {soilMetricsTabs.map((metric, index) => (
//                 <Tab
//                   key={index}
//                   className={({ selected }) =>
//                     `px-4 py-2 text-sm font-medium leading-5 text-white ${
//                       selected ? 'bg-blue-500' : 'bg-blue-300'
//                     } rounded-lg focus:outline-none`
//                   }
//                 >
//                   {metric.title}
//                 </Tab>
//               ))}
//             </Tab.List>
//             <Tab.Panels>
//               {soilMetricsTabs.map((metric, index) => (
//                 <Tab.Panel key={index}>
//                   <LineGraph title={metric.title} data={formatDataForGraph(metric.data).seriesData} categories={formatDataForGraph(metric.data).categories} />
//                 </Tab.Panel>
//               ))}
//             </Tab.Panels>
//           </Tab.Group>
//         </div>
//       </div>
//     </div>
//   );
// };

// export default CropDetail;



//-------------

// import React, { useState, useEffect } from 'react';
// import LineGraph from './LineGraph'; // Assuming you have this component
// import { Tab } from '@headlessui/react';
// import axios from 'axios';

// const CropDetail = () => {
//   const [selectedMetricIndex, setSelectedMetricIndex] = useState(0);
//   const [soilMetricsData, setSoilMetricsData] = useState(null);
//   const [diseaseData, setDiseaseData] = useState(null);

//   useEffect(() => {
//     const cropId = sessionStorage.getItem('cropId') || 9; // Use cropId from session storage or default to 9
    
//     // Fetch soil metrics data
//     axios.get(`http://localhost:8081/CropSense/MetricsServer/SoilMetricsController/getIndividualizedMetricsForCrop?cropId=${cropId}`)
//       .then(response => {
//         setSoilMetricsData(response.data);
//       })
//       .catch(error => {
//         console.error('There was an error fetching the soil metrics data!', error);
//       });
    
//     // Fetch disease data
//     axios.get(`http://localhost:8081/CropSense/MetricsServer/DiseaseMetricsController/getLatestDetectedDiseases?cropId=${cropId}`)
//       .then(response => {
//         setDiseaseData(response.data);
//       })
//       .catch(error => {
//         console.error('There was an error fetching the disease data!', error);
//       });
//   }, []);

//   if (!soilMetricsData || !diseaseData) {
//     return <div>Loading...</div>;
//   }

//   const soilMetricsTabs = [
//     { title: 'Moisture Levels', data: soilMetricsData.moisture },
//     { title: 'pH Levels', data: soilMetricsData.ph },
//     { title: 'Nitrogen Levels', data: soilMetricsData.nitrogen },
//     { title: 'Phosphorous Levels', data: soilMetricsData.phosphorous },
//     { title: 'Potassium Levels', data: soilMetricsData.potassium }
//   ];

//   const formatDataForGraph = (data) => {
//     const categories = Object.keys(data);
//     const seriesData = categories.map(date => parseFloat(data[date]));
//     return { categories, seriesData };
//   };

//   return (
//     <div className="container mx-auto p-6 bg-gray-100">
//       <h1 className="text-3xl font-bold mb-6">Crop Detail</h1>
      
//       {/* Soil Metrics Line Graphs */}
//       <div className="flex justify-center mt-6">
//         <div className="bg-white p-4 rounded-lg shadow justify-center w-1/2">
//           <h2 className="text-2xl font-semibold mb-4">Soil Metrics</h2>
//           <Tab.Group selectedIndex={selectedMetricIndex} onChange={setSelectedMetricIndex}>
//             <Tab.List className="flex space-x-2 mb-4">
//               {soilMetricsTabs.map((metric, index) => (
//                 <Tab
//                   key={index}
//                   className={({ selected }) =>
//                     `px-4 py-2 text-sm font-medium leading-5 text-white ${
//                       selected ? 'bg-blue-500' : 'bg-blue-300'
//                     } rounded-lg focus:outline-none`
//                   }
//                 >
//                   {metric.title}
//                 </Tab>
//               ))}
//             </Tab.List>
//             <Tab.Panels>
//               {soilMetricsTabs.map((metric, index) => (
//                 <Tab.Panel key={index}>
//                   <LineGraph title={metric.title} data={formatDataForGraph(metric.data).seriesData} categories={formatDataForGraph(metric.data).categories} />
//                 </Tab.Panel>
//               ))}
//             </Tab.Panels>
//           </Tab.Group>
//         </div>
//       </div>

//       {/* Latest Detected Diseases */}
//       <div className="flex justify-center mt-6">
//         <div className="bg-white p-4 rounded-lg shadow justify-center w-1/2">
//           <h2 className="text-2xl font-semibold mb-4">Latest Detected Diseases</h2>
//           <table className="min-w-full bg-white">
//             <thead>
//               <tr>
//                 <th className="py-2 px-4 border-b text-left">Disease</th>
//                 <th className="py-2 px-4 border-b text-left">Latitude</th>
//                 <th className="py-2 px-4 border-b text-left">Longitude</th>
//               </tr>
//             </thead>
//             <tbody>
//               {Object.entries(diseaseData).map(([disease, locations], index) => (
//                 locations.map((location, locIndex) => (
//                   <tr key={`${index}-${locIndex}`}>
//                     <td className="py-2 px-4 border-b">{disease}</td>
//                     <td className="py-2 px-4 border-b">{location.lat}</td>
//                     <td className="py-2 px-4 border-b">{location.lng}</td>
//                   </tr>
//                 ))
//               ))}
//             </tbody>
//           </table>
//         </div>
//       </div>
//     </div>
//   );
// };

// export default CropDetail;



//------------
// import React, { useState, useEffect } from 'react';
// import LineGraph from './LineGraph'; // Assuming you have this component
// import { Tab } from '@headlessui/react';
// import axios from 'axios';
// import { CameraIcon } from '@heroicons/react/24/solid';

// const CropDetail = () => {
//   const [selectedMetricIndex, setSelectedMetricIndex] = useState(0);
//   const [soilMetricsData, setSoilMetricsData] = useState(null);
//   const [diseaseData, setDiseaseData] = useState(null);
//   const [diseaseRate, setDiseaseRate] = useState(null);

//   useEffect(() => {
//     const cropId = sessionStorage.getItem('cropId') || 9; // Use cropId from session storage or default to 9

//     // Fetch soil metrics data
//     axios.get(`http://localhost:8081/CropSense/MetricsServer/SoilMetricsController/getIndividualizedMetricsForCrop?cropId=${cropId}`)
//       .then(response => {
//         setSoilMetricsData(response.data);
//       })
//       .catch(error => {
//         console.error('There was an error fetching the soil metrics data!', error);
//       });

//     // Fetch disease data
//     axios.get(`http://localhost:8081/CropSense/MetricsServer/DiseaseMetricsController/getLatestDetectedDiseases?cropId=${cropId}`)
//       .then(response => {
//         setDiseaseData(response.data);
//       })
//       .catch(error => {
//         console.error('There was an error fetching the disease data!', error);
//       });

//     // Fetch disease rate data
//     axios.get(`http://localhost:8081/CropSense/MetricsServer/DiseaseMetricsController/calculateLatestDiseaseRate?cropId=${cropId}`)
//       .then(response => {
//         setDiseaseRate(response.data);
//       })
//       .catch(error => {
//         console.error('There was an error fetching the disease rate data!', error);
//       });
//   }, []);

//   if (!soilMetricsData || !diseaseData || diseaseRate === null) {
//     return <div>Loading...</div>;
//   }

//   const soilMetricsTabs = [
//     { title: 'Moisture Levels', data: soilMetricsData.moisture },
//     { title: 'pH Levels', data: soilMetricsData.ph },
//     { title: 'Nitrogen Levels', data: soilMetricsData.nitrogen },
//     { title: 'Phosphorous Levels', data: soilMetricsData.phosphorous },
//     { title: 'Potassium Levels', data: soilMetricsData.potassium }
//   ];

//   const formatDataForGraph = (data) => {
//     const categories = Object.keys(data);
//     const seriesData = categories.map(date => parseFloat(data[date]));
//     return { categories, seriesData };
//   };

//   const diseaseRatePercentage = (diseaseRate * 100).toFixed(2); // Convert to percentage and format to 2 decimal places

//   return (
//     <div className="container mx-auto p-6 bg-gray-100">
//       <h1 className="text-3xl font-bold mb-6">Crop Detail</h1>
      
//       {/* Soil Metrics Line Graphs */}
//       <div className="flex justify-center mt-6">
//         <div className="bg-white p-4 rounded-lg shadow justify-center w-1/2">
//           <h2 className="text-2xl font-semibold mb-4">Soil Metrics</h2>
//           <Tab.Group selectedIndex={selectedMetricIndex} onChange={setSelectedMetricIndex}>
//             <Tab.List className="flex space-x-2 mb-4">
//               {soilMetricsTabs.map((metric, index) => (
//                 <Tab
//                   key={index}
//                   className={({ selected }) =>
//                     `px-4 py-2 text-sm font-medium leading-5 text-white ${
//                       selected ? 'bg-blue-500' : 'bg-blue-300'
//                     } rounded-lg focus:outline-none`
//                   }
//                 >
//                   {metric.title}
//                 </Tab>
//               ))}
//             </Tab.List>
//             <Tab.Panels>
//               {soilMetricsTabs.map((metric, index) => (
//                 <Tab.Panel key={index}>
//                   <LineGraph title={metric.title} data={formatDataForGraph(metric.data).seriesData} categories={formatDataForGraph(metric.data).categories} />
//                 </Tab.Panel>
//               ))}
//             </Tab.Panels>
//           </Tab.Group>
//         </div>
//       </div>

//       {/* Latest Detected Diseases and Disease Rate */}
//       <div className="flex justify-center mt-6 space-x-6">
//         <div className="bg-white p-4 rounded-lg shadow w-1/2">
//           <h2 className="text-2xl font-semibold mb-4">Latest Detected Diseases</h2>
//           <table className="min-w-full bg-white">
//             <thead>
//               <tr>
//                 <th className="py-2 px-4 border-b text-left">Disease</th>
//                 <th className="py-2 px-4 border-b text-left">Latitude</th>
//                 <th className="py-2 px-4 border-b text-left">Longitude</th>
//               </tr>
//             </thead>
//             <tbody>
//               {Object.entries(diseaseData).map(([disease, locations], index) => (
//                 locations.map((location, locIndex) => (
//                   <tr key={`${index}-${locIndex}`}>
//                     <td className="py-2 px-4 border-b">{disease}</td>
//                     <td className="py-2 px-4 border-b">{location.lat}</td>
//                     <td className="py-2 px-4 border-b">{location.lng}</td>
//                   </tr>
//                 ))
//               ))}
//             </tbody>
//           </table>
//         </div>

//         <div className="relative flex flex-col bg-clip-border rounded-xl bg-white text-gray-700 shadow-md w-1/4">
//           <div className="bg-clip-border mx-4 rounded-xl overflow-hidden bg-gradient-to-tr from-blue-600 to-blue-400 text-white shadow-blue-500/40 shadow-lg absolute -mt-4 grid h-16 w-16 place-items-center">
//             <CameraIcon className="h-6 w-6 text-white" />
//           </div>
//           <div className="p-4 text-right">
//             <p className="block antialiased font-sans text-m leading-normal font-normal text-blue-gray-600">Diseased Crop Percentage</p>
//             <h4 className="block antialiased tracking-normal font-sans text-2xl font-semibold leading-snug text-blue-gray-900">{diseaseRatePercentage}%</h4>
//           </div>
//         </div>
//       </div>
//     </div>
//   );
// };

// export default CropDetail;



//------------------

import React, { useState, useEffect } from 'react';
import LineGraph from './LineGraph'; // Assuming you have this component
import { Tab } from '@headlessui/react';
import axios from 'axios';
import { CameraIcon } from '@heroicons/react/24/solid';

const CropDetail = () => {
  const [selectedMetricIndex, setSelectedMetricIndex] = useState(0);
  const [soilMetricsData, setSoilMetricsData] = useState(null);
  const [diseaseData, setDiseaseData] = useState(null);
  const [diseaseRate, setDiseaseRate] = useState(null);
  const [diseaseRateForAllDates, setDiseaseRateForAllDates] = useState(null);

  useEffect(() => {
    const cropId = sessionStorage.getItem('cropId') || 9; // Use cropId from session storage or default to 9

    // Fetch soil metrics data
    axios.get(`http://localhost:8081/CropSense/MetricsServer/SoilMetricsController/getIndividualizedMetricsForCrop?cropId=${cropId}`)
      .then(response => {
        setSoilMetricsData(response.data);
      })
      .catch(error => {
        console.error('There was an error fetching the soil metrics data!', error);
      });

    // Fetch disease data
    axios.get(`http://localhost:8081/CropSense/MetricsServer/DiseaseMetricsController/getLatestDetectedDiseases?cropId=${cropId}`)
      .then(response => {
        setDiseaseData(response.data);
      })
      .catch(error => {
        console.error('There was an error fetching the disease data!', error);
      });

    // Fetch disease rate data
    axios.get(`http://localhost:8081/CropSense/MetricsServer/DiseaseMetricsController/calculateLatestDiseaseRate?cropId=${cropId}`)
      .then(response => {
        setDiseaseRate(response.data);
      })
      .catch(error => {
        console.error('There was an error fetching the disease rate data!', error);
      });

    // Fetch disease rate for all dates data
    axios.get(`http://localhost:8081/CropSense/MetricsServer/DiseaseMetricsController/calculateDiseaseRateForAllDates?cropId=${cropId}`)
      .then(response => {
        setDiseaseRateForAllDates(response.data);
      })
      .catch(error => {
        console.error('There was an error fetching the disease rate for all dates data!', error);
      });
  }, []);

  if (!soilMetricsData || !diseaseData || diseaseRate === null || !diseaseRateForAllDates) {
    return <div>Loading...</div>;
  }

  const soilMetricsTabs = [
    { title: 'Moisture Levels', data: soilMetricsData.moisture },
    { title: 'pH Levels', data: soilMetricsData.ph },
    { title: 'Nitrogen Levels', data: soilMetricsData.nitrogen },
    { title: 'Phosphorous Levels', data: soilMetricsData.phosphorous },
    { title: 'Potassium Levels', data: soilMetricsData.potassium }
  ];

  const formatDataForGraph = (data) => {
    const categories = Object.keys(data);
    const seriesData = categories.map(date => parseFloat(data[date]));
    return { categories, seriesData };
  };

  const diseaseRatePercentage = (diseaseRate).toFixed(2);
  const formattedDiseaseRateForAllDates = formatDataForGraph(diseaseRateForAllDates);

  return (
    <div className="container mx-auto p-6 bg-gray-100">
      <h1 className="text-3xl font-bold mb-6">Crop Detail</h1>
      
      {/* Soil Metrics Line Graphs */}
      <div className="flex justify-center mt-6">
        <div className="bg-white p-4 rounded-lg shadow justify-center w-1/2">
          <h2 className="text-2xl font-semibold mb-4">Soil Metrics</h2>
          <Tab.Group selectedIndex={selectedMetricIndex} onChange={setSelectedMetricIndex}>
            <Tab.List className="flex space-x-2 mb-4">
              {soilMetricsTabs.map((metric, index) => (
                <Tab
                  key={index}
                  className={({ selected }) =>
                    `px-4 py-2 text-sm font-medium leading-5 text-white ${
                      selected ? 'bg-blue-500' : 'bg-blue-300'
                    } rounded-lg focus:outline-none`
                  }
                >
                  {metric.title}
                </Tab>
              ))}
            </Tab.List>
            <Tab.Panels>
              {soilMetricsTabs.map((metric, index) => (
                <Tab.Panel key={index}>
                  <LineGraph title={metric.title} data={formatDataForGraph(metric.data).seriesData} categories={formatDataForGraph(metric.data).categories} />
                </Tab.Panel>
              ))}
            </Tab.Panels>
          </Tab.Group>
        </div>
      </div>

      {/* Latest Detected Diseases and Disease Rate */}
      <div className="flex justify-center mt-6 space-x-6">
        <div className="bg-white p-4 rounded-lg shadow w-1/2">
          <h2 className="text-2xl font-semibold mb-4">Latest Detected Diseases</h2>
          <table className="min-w-full bg-white">
            <thead>
              <tr>
                <th className="py-2 px-4 border-b text-left">Disease</th>
                <th className="py-2 px-4 border-b text-left">Latitude</th>
                <th className="py-2 px-4 border-b text-left">Longitude</th>
              </tr>
            </thead>
            <tbody>
              {Object.entries(diseaseData).map(([disease, locations], index) => (
                locations.map((location, locIndex) => (
                  <tr key={`${index}-${locIndex}`}>
                    <td className="py-2 px-4 border-b">{disease}</td>
                    <td className="py-2 px-4 border-b">{location.lat}</td>
                    <td className="py-2 px-4 border-b">{location.lng}</td>
                  </tr>
                ))
              ))}
            </tbody>
          </table>
        </div>

        <div className="relative flex flex-col bg-clip-border rounded-xl bg-white text-gray-700 shadow-md w-1/4">
          <div className="bg-clip-border mx-4 rounded-xl overflow-hidden bg-gradient-to-tr from-blue-600 to-blue-400 text-white shadow-blue-500/40 shadow-lg absolute -mt-4 grid h-16 w-16 place-items-center">
            <CameraIcon className="h-6 w-6 text-white" />
          </div>
          <div className="p-4 text-right">
            <p className="block antialiased font-sans text-m leading-normal font-normal text-blue-gray-600">Diseased Crop Percentage</p>
            <h4 className="block antialiased tracking-normal font-sans text-2xl font-semibold leading-snug text-blue-gray-900">{diseaseRatePercentage}%</h4>
          </div>
        </div>
      </div>

      {/* Disease Rate for All Dates Line Graph */}
      <div className="flex justify-center mt-6">
        <div className="mt-6 bg-white p-4 rounded-lg shadow h-fit w-1/2">
          <h2 className="text-2xl font-semibold mb-4">Disease Rate Over Time</h2>
          <LineGraph data={formattedDiseaseRateForAllDates.seriesData} categories={formattedDiseaseRateForAllDates.categories} />
        </div>
      </div>
    </div>
  );
};

export default CropDetail;

