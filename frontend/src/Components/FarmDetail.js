import React, { useState, useEffect } from 'react';
import { useHistory } from 'react-router-dom';
import LineGraph from './LineGraph';

const FarmDetail = () => {
  const history = useHistory();
  const [farm, setFarm] = useState(null);

  useEffect(() => {
    const mockData = [
      { title: 'Soil Metric 1', data: [10, 20, 30, 40, 50] },
      { title: 'Soil Metric 2', data: [15, 25, 35, 45, 55] },

    ];
    setFarm(mockData);
  }, []);

  const backToFarm = () => {
    history.push('/Farm');
  };

  if (!farm) {
    return <div>Loading...</div>;
  }

  return (
    <div className="p-4">
      <button onClick={backToFarm} className="bg-indigo-500 text-white py-2 px-4 rounded">
        Back to Farms
      </button>
      <h1 className="text-2xl font-bold mt-4">{farm.name}</h1>
      <div className="relative flex flex-col bg-clip-border w-2/3 rounded-xl bg-white text-gray-700 shadow-md">
        <LineGraph
          title="Soil Analysis"
          lineChartData={farm.data}
        />
      </div>
    </div>
  );
};

export default FarmDetail;
