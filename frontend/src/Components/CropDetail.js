import React, { useState } from 'react';
import LineGraph from './LineGraph'; // Assuming you have this component
import { Tab } from '@headlessui/react';
import Chart from 'react-apexcharts';
import { CameraIcon, BugAntIcon } from '@heroicons/react/24/solid';


const mockSoilMetricsData = [
  { title: 'Potassium', data: [20, 20, 5, 20, 10] },
  { title: 'Nitrogen', data: [30, 25, 30, 35, 25] },
  { title: 'ph', data: [7.5, 7, 6.9, 6, 6.5] },
  { title: 'Moisture', data: [25, 35, 45, 55, 65] },
  { title: 'Metric 5', data: [30, 40, 50, 60, 70] }
];

const mockDiseaseData = [
  { disease: 'Disease A', location: { latitude: '35.6895', longitude: '139.6917' } },
  { disease: 'Disease B', location: { latitude: '34.0522', longitude: '-118.2437' } }
];

const mockCropPercentageData = {
  series: [
    { name: 'Crop Percentage', data: [10, 15, 20, 25, 30] }
  ],
  categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May']
};

const CropDetail = () => {
  const [selectedMetricIndex, setSelectedMetricIndex] = useState(0);

  return (
    <div className="container mx-auto p-6 bg-gray-100">
      <h1 className="text-3xl font-bold mb-6">Crop Detail</h1>
      <div className="flex justify-center mt-6">
        <div className="bg-white p-4 rounded-lg shadow-md justify-center w-1/2 ">
        <h2 className="text-2xl font-semibold mb-4">Soil Metrics</h2>
        <Tab.Group selectedIndex={selectedMetricIndex} onChange={setSelectedMetricIndex}>
          <Tab.List className="flex space-x-2 mb-4">
            {mockSoilMetricsData.map((metric, index) => (
              <Tab
                key={index}
                className={({ selected }) =>
                  `px-4 py-2 text-sm font-medium leading-5 text-white ${
                    selected ? 'bg-yellow-700' : 'bg-yellow-500'
                  } rounded-lg focus:outline-none`
                }
              >
                {metric.title}
              </Tab>
            ))}
          </Tab.List>
          <Tab.Panels>
            {mockSoilMetricsData.map((metric, index) => (
              <Tab.Panel key={index} className="transition-opacity duration-500 ease-in-out">
                <LineGraph title={metric.title} data={metric.data} color='#eab308'/>
              </Tab.Panel>
            ))}
          </Tab.Panels>
        </Tab.Group>
      </div>
      </div>
      <div className="flex justify-center mt-6">
        <div className="mt-6 grid grid-cols-1 md:grid-cols-2 gap-6">


          <div className="bg-white p-4 rounded-lg shadow-md">
            <h2 className="text-xl font-semibold mb-4">Latest Detected Diseases</h2>
            <table className="min-w-full bg-white">
              <thead>
                <tr>
                  <th className="py-2 px-4 border-b text-left">Disease</th>
                  <th className="py-2 px-8 border-b text-left">Latitude</th>
                  <th className="py-2 px-4 border-b text-left">Longitude</th>
                </tr>
              </thead>
              <tbody>
                {mockDiseaseData.map((item, index) => (
                  <tr key={index}>
                    <td className="py-2 px-4 border-b">{item.disease}</td>
                    <td className="py-2 px-8 border-b">{item.location.latitude}</td>
                    <td className="py-2 px-4 border-b">{item.location.longitude}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>

        <div className="relative flex flex-col bg-clip-border rounded-xl bg-white text-gray-700 shadow-md">
        <div className="bg-clip-border mx-4 rounded-xl overflow-hidden bg-gradient-to-tr from-purple-600 to-purple-400 text-white shadow-blue-500/40 shadow-lg absolute -mt-4 grid h-16 w-16 place-items-center">
          <BugAntIcon className="h-6 w-6 text-white" />
        </div>
        <div className="p-4 text-right">
          <p className="block antialiased text-xl font-semibold leading-normal font-normal text-blue-gray-600">Latest Disease %</p>
          <h4 className="block antialiased tracking-normal font-sans text-2xl font-semibold leading-snug text-blue-900">8%</h4>
        </div>
        <div className="border-t border-blue-gray-50 p-4">
          <p className="block antialiased font-sans text-base leading-relaxed font-normal text-blue-gray-600">
            <strong className="text-green-500">-25%</strong>&nbsp;than last year
          </p>
        </div>
      </div>

      </div>
      </div>

      <div className="flex justify-center mt-6">
        <div className="mt-6 bg-white p-4 rounded-lg shadow-md h-fit w-1/2">
        <h2 className="text-2xl font-semibold mb-4">Crop Disease Percentage</h2>
        <LineGraph  data={mockCropPercentageData.series[0].data} categories={mockCropPercentageData.categories} color='#a855f7'/>
        </div>
      </div>
 <div className="flex justify-center mt-6">
        <div className="mt-6 grid grid-cols-1 md:grid-cols-2 gap-6">


          <div className="bg-white p-4 rounded-lg shadow-md">
            <h2 className="text-xl font-semibold mb-4">Latest Species Anomalies</h2>
            <table className="min-w-full bg-white">
              <thead>
                <tr>
                  <th className="py-2 px-4 border-b text-left">Species</th>
                  <th className="py-2 px-8 border-b text-left">Latitude</th>
                  <th className="py-2 px-4 border-b text-left">Longitude</th>
                </tr>
              </thead>
              <tbody>
                {mockDiseaseData.map((item, index) => (
                  <tr key={index}>
                    <td className="py-2 px-4 border-b">{item.disease}</td>
                    <td className="py-2 px-8 border-b">{item.location.latitude}</td>
                    <td className="py-2 px-4 border-b">{item.location.longitude}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>

        <div className="relative flex flex-col bg-clip-border rounded-xl bg-white text-gray-700 shadow-md ">
        <div className="bg-clip-border mx-4 rounded-xl overflow-hidden bg-gradient-to-tr from-pink-600 to-pink-400 text-white shadow-blue-500/40 shadow-lg absolute -mt-4 grid h-16 w-16 place-items-center">
          <CameraIcon className="h-6 w-6 text-white" />
        </div>
        <div className="p-4 text-right">
          <p className="block antialiased text-xl font-semibold leading-normal font-normal text-blue-gray-600">Anomaly Count</p>
          <h4 className="block antialiased tracking-normal font-sans text-2xl font-semibold leading-snug text-blue-900">8</h4>
        </div>
        <div className="border-t border-blue-gray-50 p-4">
          <p className="block antialiased font-sans text-base leading-relaxed font-normal text-blue-gray-600">
            <strong className="text-red-500">+5%</strong>&nbsp;than last week
          </p>
        </div>
      </div>

      </div>
      </div>

      <div className="flex justify-center mt-6">
        <div className="mt-6 bg-white p-4 rounded-lg shadow-md h-fit w-1/2">
        <h2 className="text-2xl font-semibold mb-4">Anomaly Count</h2>
        <LineGraph  data={mockCropPercentageData.series[0].data} categories={mockCropPercentageData.categories} color='#ec4899' />
        </div>
      </div>

    </div>
  );
};

export default CropDetail;
