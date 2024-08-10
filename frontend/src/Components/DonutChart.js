import React, { useState, useEffect } from 'react';
import Chart from 'react-apexcharts';
import axios from 'axios';

const DonutChart = () => {
  const [series, setSeries] = useState([]);
  const [labels, setLabels] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      const userId = sessionStorage.getItem('userId');

      if (!userId) {
        console.error("userId is not set in session storage");
        return;
      }

      const METRICS_SERVER_IP=process.env.REACT_APP_METRICS_SERVER_IP;
      const METRICS_SERVER_PORT=process.env.REACT_APP_METRICS_SERVER_PORT;
      const url = `http://${METRICS_SERVER_IP}:${METRICS_SERVER_PORT}/CropSense/MetricsServer/CropMetricsController/getActiveCropSpeciesForOwner?ownerId=${userId}`;

      try {
        const response = await axios.get(url);
        console.log("API Response:", response.data);

        if (response.data && typeof response.data === 'object') {
          const seriesData = Object.values(response.data);
          const labelsData = Object.keys(response.data);

          setSeries(seriesData);
          setLabels(labelsData);
        } else {
          console.error("Unexpected API response format:", response.data);
        }
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };

    fetchData();
  }, []);

  const options = {
    series: series,
    chart: {
      height: 400, 
      width: 400, 
      type: "donut",
    },
    stroke: {
      colors: ["transparent"],
      lineCap: "round",
    },
    plotOptions: {
      pie: {
        donut: {
          labels: {
            show: true,
            name: {
              show: true,
              fontFamily: "Inter, sans-serif",
              offsetY: 20,
            },
            total: {
              showAlways: true,
              show: true,
              label: "Active Crops",
              fontFamily: "Inter, sans-serif",
              fontSize: '16px',
              formatter: function (w) {
                const sum = w.globals.seriesTotals.reduce((a, b) => {
                  return a + b;
                }, 0);
                return sum;
              },
            },
            value: {
              show: true,
              fontFamily: "Inter, sans-serif",
              fontSize: '20px',
              offsetY: -20,
              formatter: function (value) {
                return value;
              },
            },
          },
          size: "75%",
        },
      },
    },
    labels: labels,
    dataLabels: {
      enabled: false,
    },
    legend: {
      position: "right",
      fontFamily: "Inter, sans-serif",
      fontSize: '18px', 
      markers: {
        width: 10,
        height: 10,
      },
      itemMargin: {
        horizontal: 10,
        vertical: 5,
      },
    },
    yaxis: {
      labels: {
        formatter: function (value) {
          return value;
        },
      },
    },
    xaxis: {
      labels: {
        formatter: function (value) {
          return value;
        },
      },
      axisTicks: {
        show: false,
      },
      axisBorder: {
        show: false,
      },
    },
  };

  return (
      <Chart options={options} series={series} type="donut" height={400} width={400} />
  );
};

export default DonutChart;
