// Inicializacao das paginas
document.addEventListener('DOMContentLoaded', () => { 'use strict'

  document.getElementById('searchField').addEventListener('keydown', (e) => {
    if (e.key === 'Enter') {
      e.preventDefault();
      document.querySelector('#formSearch')?.requestSubmit();
    }
  });

  document.getElementById('bd-theme').addEventListener('click', (e) => {
    console.log(e);
    document.body.dataset.bsTheme = (document.body.dataset.bsTheme === 'dark') ? 'light' : 'dark';
  });

});

// Formatacao de data (dd/mm/aaaa)
const formatDate = function(field_name) {

  var field = document.getElementById(field_name);

  field.addEventListener('input', (e) => {
    console.debug(e);
    let v = field.value.replace(/\D/g, '').slice(0,8); // keep digits only, max 8
    if (v.length >= 3) v = v.slice(0,2) + '/' + v.slice(2);  // insert slash after DD
    if (v.length >= 6) v = v.slice(0,5) + '/' + v.slice(5);  // insert slash after MM
    field.value = v;
  });

  field.addEventListener('blur', () => {
    const parts = field.value.split('/');
    if (parts.length === 3) {
      const dd = parts[0].padStart(2,'0');
      const mm = parts[1].padStart(2,'0');
      const yyyy = parts[2];
      field.value = `${dd}/${mm}/${yyyy}`;
    }
  });

};
