/* Guias: calcular total e copiar dados de procedimentos */

const limpa_numero = (numero_formatado) => {return Number(numero_formatado.replace(/[^\d,-]/g, "").replace(",", "."))};

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


