import React from 'react';
import Chart from 'react-apexcharts';

const LineGraph = ({ title,data }) => {

  const chartOptions = {
    chart: {
      type: 'line',
      width: '100%',
    },
    stroke: {
      curve: 'smooth',
    },
    series: [
      {
        name: title,
        data: data,
      }
    ],
    xaxis: {
      categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep'],
    },
    colors: ['#4DFFDF', '#4DA1FF', '#E323FF', '#7517F8', '#FFD422'],
  };

  return (
    <div className="h-full py-6 px-6 rounded-xl border border-gray-200 bg-white">
      <h5 className="text-xl text-gray-700">{title}</h5>

      <Chart options={chartOptions} series={chartOptions.series} type="line" />
    </div>
  );
};

export default LineGraph;
