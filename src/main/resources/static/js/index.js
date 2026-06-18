document.addEventListener('DOMContentLoaded', () => {

  'use strict'

  // placeholder visual
  document.getElementById('status').textContent = 'Carregando dados...';

  const ctx = document.getElementById('myChart');
  
  const xValues = ["Jan","Fev","Mar","Abr","Mai","Jun"];

  // inicializa com zeros (evita layout shift)
  const initialData = [0,0,0,0,0,0];

  const chart = new Chart(ctx, {
    type: 'bar',
    data: { labels: xValues, datasets: [{ data: initialData, backgroundColor: 'blue' }]},
    options: {
      plugins: {
        legend: { display: false },
        tooltip: { boxPadding: 3 }
      }
    }
  });

  async function loadAndRender() {
    try {
      const base = window.location.pathname.split('/').slice(0,2).join('/') || '';
      const res = await fetch(`${base}/api/chart`);
      if (!res.ok) throw new Error('HTTP ' + res.status);
      const json = await res.json(); // { x: [...], y: [...] }
      console.log(res);
      // atualiza labels se necessário
      if (json.xValues) chart.data.labels = json.xValues;
      chart.data.datasets[0].data = json.yValues;
      chart.update();
      document.getElementById('status').remove();
    } catch(err) {
      document.getElementById('status').textContent = 'Erro ao carregar dados';
      console.error(err);
    }
  }

  loadAndRender();
  
});
