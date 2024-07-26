import React, { useState, useEffect } from 'react';
import LineGraph from './LineGraph';
import { Tab } from '@headlessui/react';
import axios from 'axios';
import { CameraIcon } from '@heroicons/react/24/solid';
import { parseISO, format } from 'date-fns';




const CropDetail = () => {
  const METRICS_SERVER_IP=process.env.REACT_APP_METRICS_SERVER_IP;
  const METRICS_SEVER_PORT=process.env.REACT_APP_METRICS_SERVER_PORT;
  const APP_SERVER_IP=process.env.REACT_APP_APP_SERVER_IP;
  const APP_SERVER_PORT=process.env.REACT_APP_APP_SERVER_PORT;

  console.log('METRICS_SERVER_IP:', process.env.REACT_APP_METRICS_SERVER_IP);
  console.log('METRICS_SERVER_PORT:', process.env.REACT_APP_METRICS_SERVER_PORT);
  
  const [selectedMetricIndex, setSelectedMetricIndex] = useState(0);
  const [soilMetricsData, setSoilMetricsData] = useState(null);
  const [diseaseData, setDiseaseData] = useState(null);
  const [diseaseRate, setDiseaseRate] = useState(null);
  const [diseaseRateForAllDates, setDiseaseRateForAllDates] = useState(null);
  const [speciesAnomalies, setSpeciesAnomalies] = useState(null);
  const [speciesAnomalyCount, setSpeciesAnomalyCount] = useState(0);
  const [speciesAnomalyCountOverTime, setSpeciesAnomalyCountOverTime] = useState(null);

  useEffect(() => {
    const cropId = sessionStorage.getItem('cropId') || 9;

    // Fetch soil metrics data
    axios.get(`http://${METRICS_SERVER_IP}:${METRICS_SEVER_PORT}/CropSense/MetricsServer/SoilMetricsController/getIndividualizedMetricsForCrop?cropId=${cropId}`)
      .then(response => {
        setSoilMetricsData(response.data);
      })
      .catch(error => {
        console.error('There was an error fetching the soil metrics data!', error);
      });

    // Fetch disease data
    axios.get(`http://${METRICS_SERVER_IP}:${METRICS_SEVER_PORT}/CropSense/MetricsServer/DiseaseMetricsController/getLatestDetectedDiseases?cropId=${cropId}`)
      .then(response => {
        setDiseaseData(response.data);
      })
      .catch(error => {
        console.error('There was an error fetching the disease data!', error);
      });

    // Fetch disease rate data
    axios.get(`http://${METRICS_SERVER_IP}:${METRICS_SEVER_PORT}/CropSense/MetricsServer/DiseaseMetricsController/calculateLatestDiseaseRate?cropId=${cropId}`)
      .then(response => {
        setDiseaseRate(response.data);
      })
      .catch(error => {
        console.error('There was an error fetching the disease rate data!', error);
      });

    // Fetch disease rate for all dates data
    axios.get(`http://${METRICS_SERVER_IP}:${METRICS_SEVER_PORT}/CropSense/MetricsServer/DiseaseMetricsController/calculateDiseaseRateForAllDates?cropId=${cropId}`)
      .then(response => {
        setDiseaseRateForAllDates(response.data);
      })
      .catch(error => {
        console.error('There was an error fetching the disease rate for all dates data!', error);
      });

    // Fetch species anomalies data
    axios.get(`http://${METRICS_SERVER_IP}:${METRICS_SEVER_PORT}/CropSense/MetricsServer/SpeciesMetricsController/compileLatestSpeciesAnomaliesForCrop?cropId=${cropId}`)
      .then(response => {
        setSpeciesAnomalies(response.data);
      })
      .catch(error => {
        console.error('There was an error fetching the species anomalies data!', error);
      });

    // Fetch species anomaly count data
    axios.get(`http://${METRICS_SERVER_IP}:${METRICS_SEVER_PORT}/CropSense/MetricsServer/SpeciesMetricsController/countLatestSpeciesAnomaliesForCrop?cropId=${cropId}`)
      .then(response => {
        setSpeciesAnomalyCount(response.data);
      })
      .catch(error => {
        console.error('There was an error fetching the species anomaly count data!', error);
      });

    // Fetch species anomaly count over time data
    axios.get(`http://${METRICS_SERVER_IP}:${METRICS_SEVER_PORT}/CropSense/MetricsServer/SpeciesMetricsController/calculateSpeciesAnomalyCountForCropOverTime?cropId=${cropId}`)
      .then(response => {
        setSpeciesAnomalyCountOverTime(response.data);
      })
      .catch(error => {
        console.error('There was an error fetching the species anomaly count over time data!', error);
      });

  }, []);

  if (!soilMetricsData || !diseaseData || diseaseRate === null || !diseaseRateForAllDates || !speciesAnomalies || speciesAnomalyCountOverTime === null) {
    return <div>Loading...</div>;
  }

  const soilMetricsTabs = [
    { title: 'Moisture Levels', data: soilMetricsData.moisture, color: '#1f77b4' },
    { title: 'pH Levels', data: soilMetricsData.ph, color: '#ff7f0e' },
    { title: 'Nitrogen Levels', data: soilMetricsData.nitrogen, color: '#2ca02c' },
    { title: 'Phosphorous Levels', data: soilMetricsData.phosphorous, color: '#d62728' },
    { title: 'Potassium Levels', data: soilMetricsData.potassium, color: '#9467bd' }
  ];

  const formatDataForGraph = (data) => {
    const categories = Object.keys(data).map(date => format(parseISO(date), 'MMM dd'));
    const seriesData = Object.values(data).map(value => parseFloat(value));
    return { categories, seriesData };
  };

  const diseaseRatePercentage = (diseaseRate).toFixed(2);
  const formattedDiseaseRateForAllDates = formatDataForGraph(diseaseRateForAllDates);
  const formattedSpeciesAnomalyCountOverTime = formatDataForGraph(speciesAnomalyCountOverTime);

  const anomalyList = [];
  Object.keys(speciesAnomalies).forEach((species) => {
    speciesAnomalies[species].forEach((anomaly) => {
      anomalyList.push({
        species,
        lat: anomaly.lat,
        lng: anomaly.lng,
      });
    });
  });

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
                  <LineGraph title={metric.title} data={formatDataForGraph(metric.data).seriesData} categories={formatDataForGraph(metric.data).categories} color={metric.color} />
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
            <p className="block antialiased font-sans text-m leading-normal font-normal text-blue-gray-600">Diseased Area</p>
            <h4 className="block antialiased tracking-normal font-sans text-2xl font-semibold leading-snug text-blue-900">{diseaseRatePercentage}%</h4>
          </div>
          <div className="border-t border-blue-gray-50 p-4">
            <p className="block antialiased font-sans text-base leading-relaxed font-normal text-blue-gray-600">
              Percentage of cultivated area affected by diseases.
            </p>
          </div>
        </div>
      </div>

      {/* Disease Rate Line Graph */}
      <div className="flex justify-center mt-6">
        <div className="mt-6 bg-white p-4 rounded-lg shadow-md h-fit w-1/2">
          <h2 className="text-2xl font-semibold mb-4">Disease Rate</h2>
          <LineGraph title="Disease Rate" data={formattedDiseaseRateForAllDates.seriesData} categories={formattedDiseaseRateForAllDates.categories} color='#1c4d80' />
        </div>
      </div>

      {/* Species Anomalies */}
      <div className="flex justify-center mt-6 space-x-6">
        <div className="bg-white p-4 rounded-lg shadow w-1/2">
          <h2 className="text-2xl font-semibold mb-4">Latest Species Anomalies</h2>
          <table className="min-w-full bg-white">
            <thead>
              <tr>
                <th className="py-2 px-4 border-b text-left">Species</th>
                <th className="py-2 px-4 border-b text-left">Latitude</th>
                <th className="py-2 px-4 border-b text-left">Longitude</th>
              </tr>
            </thead>
            <tbody>
              {anomalyList.map((anomaly, index) => (
                <tr key={index}>
                  <td className="py-2 px-4 border-b">{anomaly.species}</td>
                  <td className="py-2 px-4 border-b">{anomaly.lat}</td>
                  <td className="py-2 px-4 border-b">{anomaly.lng}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>

      {/* Anomaly Count */}
      <div className="flex justify-center mt-6">
        <div className="relative flex flex-col bg-clip-border rounded-xl bg-white text-gray-700 shadow-md w-1/4">
          <div className="bg-clip-border mx-4 rounded-xl overflow-hidden bg-gradient-to-tr from-pink-600 to-pink-400 text-white shadow-blue-500/40 shadow-lg absolute -mt-4 grid h-16 w-16 place-items-center">
            <CameraIcon className="h-6 w-6 text-white" />
          </div>
          <div className="p-4 text-right">
            <p className="block antialiased text-xl font-semibold leading-normal font-normal text-blue-gray-600">Anomaly Count</p>
            <h4 className="block antialiased tracking-normal font-sans text-2xl font-semibold leading-snug text-blue-900">{speciesAnomalyCount}</h4>
          </div>
          <div className="border-t border-blue-gray-50 p-4">
            <p className="block antialiased font-sans text-base leading-relaxed font-normal text-blue-gray-600">
              {speciesAnomalyCount} anomalies detected in the latest data.
            </p>
          </div>
        </div>
      </div>

      {/* Anomaly Count Line Graph */}
      <div className="flex justify-center mt-6">
        <div className="mt-6 bg-white p-4 rounded-lg shadow-md h-fit w-1/2">
          <h2 className="text-2xl font-semibold mb-4">Anomaly Count Over Time</h2>
          <LineGraph title="Anomaly Count Over Time" data={formattedSpeciesAnomalyCountOverTime.seriesData} categories={formattedSpeciesAnomalyCountOverTime.categories} color='#ec4899' />
        </div>
      </div>
    </div>
  );
};

export default CropDetail;
