import React, { useState, useEffect } from 'react';
import { useHistory } from 'react-router-dom';
import LineGraph from './LineGraph';

const FarmDetail = () => {
  const history = useHistory();
  const [farm, setFarm] = useState(null);

  useEffect(() => {
    // Mock data for initial design
    const mockData = {
      name: 'Mock Farm',
      lineChartData: [
        {
          name: 'Metric 1',
          data: [10, 41, 35, 51, 49, 62, 69, 91, 148],
        },
        {
          name: 'Metric 2',
          data: [20, 30, 40, 60, 70, 80, 90, 100, 110],
        },
        {
          name: 'Metric 3',
          data: [5, 15, 25, 35, 45, 55, 65, 75, 85],
        },
        {
          name: 'Metric 4',
          data: [30, 40, 50, 60, 70, 80, 90, 100, 110],
        },
        {
          name: 'Metric 5',
          data: [15, 25, 35, 45, 55, 65, 75, 85, 95],
        },
      ],

    };
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
          percentage={farm.percentage}
          comparisonText={farm.comparisonText}
          lineChartData={farm.lineChartData}
        />
      </div>
    </div>
  );
};

export default FarmDetail;
