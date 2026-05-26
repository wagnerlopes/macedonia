/* globals Chart:false */

(() => {
  'use strict'

  // Graphs
  const ctx = document.getElementById('myChart')
  const xValues = ["Jan", "Fev", "Mar", "Abr", "Mai", "Jun"];
  const yValues = [10, 13, 25, 22, 15, 9];
  const barColors = ["blue"]; //["red", "yellow", "green", "blue", "orange", "brown"];
  
  new Chart(ctx, {
    type: 'bar',
    data: {
      labels: xValues,
      datasets: [{
        data: yValues,
        lineTension: 0,
        backgroundColor: barColors, // 'transparent',
        borderColor: '#007bff',
        borderWidth: 0,
        pointBackgroundColor: '#007bff'
      }]
    },
    options: {
      plugins: {
        legend: {
          display: false
        },
        tooltip: {
          boxPadding: 3
        }
      }
    }
  })
})()
