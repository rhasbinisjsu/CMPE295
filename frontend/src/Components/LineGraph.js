// LineGraph.js
import React from 'react';
import { Line } from 'react-chartjs-2';
import 'chart.js/auto';



const LineGraph = ({ title, data, categories, color }) => {
  const chartData = {
    labels: categories,
    datasets: [
      {
        label: title,
        data: data,
        borderColor: color,
        fill: true,
      },
    ],
  };

  const options = {
    scales: {
      x: {
        type: 'category',
        labels: categories,
      },
      y: {
        beginAtZero: true,
      },
    },
  };

  return (
    <div>
      <h2>{title}</h2>
      <Line data={chartData} options={options} />
    </div>
  );
};

export default LineGraph;

