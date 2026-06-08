/* Guias: copiar dados de procedimentos */

const calculaTotal = function(field_name) {


 const qtd = document.getElementById(field_name).value;
   
 const match = field_name.match(/procedimentos(\d+)\.pmQtd/);
 const num = match ? match[1] : null; // "0" ou null se não bater
 const n = num !== null ? Number(num) : null; // 0 ou null

 const base_field = document.getElementById("procedimentos" + n + ".valorBase");
 console.log("BASE =" + base_field.value);

 //const valor_base = Number(base_field.value.replace(/[^\d,.-]/g, "").replace(",", "."));
 
 const valor_total = base_field.value * qtd;
 
 console.log(valor_total);

 const formatado = valor_total.toLocaleString("pt-BR", { style: "currency", currency: "BRL" }); 

 console.log(formatado);

 const total_field = document.getElementById("procedimentos" + n + ".valorTotal");

 total_field.value = formatado;
 
}

