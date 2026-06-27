document.addEventListener('DOMContentLoaded', () => {

  'use strict'

  // Placeholder visual
  const status = document.getElementById('status');
  
  const ctx = document.getElementById('myChart');

  // Labels and Colors default
  const xValues = ["Jan","Fev","Mar","Abr","Mai","Jun","Jul","Ago","Set","Out","Nov","Dez"];

  const colorValues = ["blueviolet","indigo","blue","limegreen","green","yellowgreen","yellow","gold","orange","coral","red","darkred"];

  // inicializa com zeros (evita layout shift)
  const initialData = [0,0,0,0,0,0,0,0,0,0,0,0];

  const chart = new Chart(ctx, {
    type: 'bar',
    data: { labels: xValues, datasets: [{ data: initialData, backgroundColor: colorValues }]},
    options: {
      plugins: {
        legend: { display: false },
        tooltip: { boxPadding: 3 }
      }
    }
  });

  loadAndRender(chart, status);
  
});

// Data Chart from REST API
async function loadAndRender(chart, status) {
  try {
    status.textContent = 'Carregando dados...';
    const base = window.location.pathname.split('/').slice(0,2).join('/') || '';
    const res = await fetch(`${base}/api/chart`);
    if (!res.ok) throw new Error('HTTP ' + res.status);
    const json = await res.json(); // { x: [...], y: [...] }
    console.log(res);
    // Atualiza labels
    if (json.xValues) chart.data.labels = json.xValues;
    chart.data.datasets[0].data = json.yValues;
    chart.update();
    status.remove();
  } catch(err) {
    status.textContent = 'Erro ao carregar dados';
    console.error(err);
  }
}
