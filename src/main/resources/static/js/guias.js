/* View guias.html: calcular total e copiar dados de procedimentos */

const limpa_numero = (numero_formatado) => {return Number(numero_formatado.replace(/[^\d,-]/g, "").replace(",", "."))};

const base = window.location.pathname.split('/').slice(0,2).join('/') || '';

// Calcula o Valor Total do Procedimento Medico = vlt unit * qtd
const calculaTotal = function(pm_id, pm_qtd) {

 console.log("ID = " + pm_id + " - Qtd = " + pm_qtd);
	
 const match = pm_id.match(/^([a-zA-Z]+)(\d+)\.pmQtd$/);

  if (match) {
   var proc = match[1]; // "procedimentos"
   var index = Number(match[2]); // "digito"
 }
  
 //const match = pm_id.match(/procedimentos(\d+)\.pmQtd/);
 //const n = match ? match[1] : null; // "0" ou null se não bater
 //const index = n !== null ? Number(n) : null; // 0 ou null

 const valor_unitario = limpa_numero(document.getElementById(proc + index + ".valorUnitario").value);

 const valor_total = valor_unitario * pm_qtd;

 const total_moeda = valor_total.toLocaleString("pt-BR", { style: "currency", currency: "BRL" });

 document.getElementById(proc + index + ".valorTotal").value = total_moeda;

 console.log(match);
 console.log("Vlr Unit = " + valor_unitario);
 console.log("Vlr Total = " + valor_total);
 console.log(total_moeda);
}

// Carrega os dados Procedimento Medico selecionado na lista
const opmLoad = function (idx, id) {
  console.log('ID = ' + id);
  fetch(`${base}/api/opm/${encodeURIComponent(id)}`)
    .then(response => {
      if (response.ok) return response.json();
      if (response.status === 404) throw new Error('Procedimento não encontrado');
      throw new Error('Erro na requisição: ' + response.status);
    })
    .then(data => {
      console.log(data);
	  document.getElementById('procedimentos' + idx + '.valorUnitario').value = data.valorUnitario.toLocaleString("pt-BR", { style: "currency", currency: "BRL" });
	  document.getElementById('procedimentos' + idx + '.unidadeMedida').value = data.unidadeMedida;
      document.getElementById('procedimentos' + idx + '.pm.tuss').value = data.tuss;
    })
    .catch(err => {
      console.error(err);
    });
}

// Eventos adicionados ao carregar a pagina
window.onload = function() {

  // Evento do Select 'ProcedimentoMedico'
  document.querySelectorAll('.list-pm').forEach(s => {
    s.addEventListener('change', e => {
      const name = e.target.name || e.target.id;
      const match = name.match(/\[(\d+)\]/);
      if(!match) return;
      const idx = match[1];
      const val = e.target.value;
      const ocspm_id = document.getElementById('ocspm_id').value;
	  console.log('IDX = ' + idx);
      console.log('PM_ID = ' + val);
      console.log('OCSPM_ID = ' + ocspm_id);
      opmLoad(idx, ocspm_id);
    });
  });

  // Input Qtd Procedimento Medico
  document.querySelectorAll('.pm-qtd').forEach(s => {
    s.addEventListener('change', e => {
	    const name = e.target.name || e.target.id;
	    const match = name.match(/\[(\d+)\]/);
	    if(!match) return;
	    const idx = match[1];
	    const val = e.target.value;
  	    console.log('IDX = ' + idx);
	    console.log('VLR = ' + val);
        calculaTotal(s.id, val)
	});
  });

}

//const selector = '[name="procedimentos['+ idx +'].valorTotal], #procedimentos'+ idx +'\\.valorTotal';
//const target = document.querySelector(selector);

// ao carregar com id vindo da URL, por exemplo ?id=123
//const params = new URLSearchParams(window.location.search);
//const id = params.get('id');
//if (id) carregarProcedimento(id);

// ou ao clicar um botão:
// document.getElementById('btnCarregar').addEventListener('click', () => carregarProcedimento(123));
