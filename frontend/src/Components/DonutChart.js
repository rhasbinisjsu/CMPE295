import React from 'react';
import Chart from 'react-apexcharts';

const DonutChart = () => {
  const options = {
    series: [35, 23, 2, 5],
    chart: {
      height: 320,
      width: "100%",
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
              label: "Unique Crops",
              fontFamily: "Inter, sans-serif",
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
              offsetY: -20,
              formatter: function (value) {
                return value;
              },
            },
          },
          size: "80%",
        },
      },
    },
    fill: {
      type: 'gradient',
      gradient: {
        shade: 'light',
        type: 'horizontal', // 'horizontal' or 'vertical'
        shadeIntensity: 0.5,
        gradientToColors: ["#4DA1FF", "#4D7F1F", "#FF7D05", "#E323FF"], // Gradient end colors
        inverseColors: false,
        opacityFrom: 0.11,
        opacityTo: 1,
        stops: [0, 100],
      },
    },
    labels: ["Rice", "Wheat", "Corn", "Tobacco"],
    dataLabels: {
      enabled: false,
    },
    legend: {
      position: "bottom",
      fontFamily: "Inter, sans-serif",
    },
    yaxis: {
      labels: {
        formatter: function (value) {
          return value + "k";
        },
      },
    },
    xaxis: {
      labels: {
        formatter: function (value) {
          return value + "k";
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
    <Chart options={options} series={options.series} type="donut" height={320} />
  );
};

export default DonutChart;
