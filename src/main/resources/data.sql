insert into beneficiario(cpf, nome, nascimento_data) values('11111111111', 'Jose Gomes', '2000-03-01');
insert into beneficiario(cpf, nome, nascimento_data) values('22222222222', 'Maria Tome', '1995-10-06');
insert into beneficiario(cpf, nome, nascimento_data) values('33333333333', 'Luiz Frias', '1987-08-15');
insert into beneficiario(cpf, nome, nascimento_data) values('44444444444', 'Adelaide Terra', '2001-02-04');
insert into beneficiario(cpf, nome, nascimento_data) values('55555555555', 'Fernando Pires', '2010-12-07');

insert into especialidade(codigo, descricao) values(1, 'Cardiologia');
insert into especialidade(codigo, descricao) values(2, 'Oftalmologia');
insert into especialidade(codigo, descricao) values(3, 'Ginecologia');
insert into especialidade(codigo, descricao) values(4, 'Nutrição');
insert into especialidade(codigo, descricao) values(5, 'Ortopedia');

insert into ocs(cnpj, descricao, especialidade, endereco, numero, complemento, municipio, uf, telefone, contato) values('00394452053304', 'Hospital Santa Cruz', 'cardiologia', 'Rua das Cruzes', '115', 'fundos', 'Rio Branco', 'AC', '(91) 98234-4532', 'Maria das Dores');
insert into ocs(cnpj, descricao, especialidade, endereco, numero, complemento, municipio, uf, telefone, contato) values('09216007000110', 'Hospital Grande', 'hospital geral', 'Avenida Principal', '265', 'Parque Clinico', 'Chapeco', 'SC', '(43) 97521-6789', 'Clara Gimenez');

insert into procedimento_medico(amb90, descricao, ch_qtd, auxiliares_qtd, porte_anestesico) values ('00010022', 'Consulta hospitalar', 50, 0, 0);

insert into profissional(cpf, nome, crm, crm_uf, especialidade_codigo) values ('99999999999', 'Joaquim Teixeira', '112456', 'SP', 1);

insert into tipo_acomodacao values ('02', 'Quarto privativo / Particular');
insert into tipo_acomodacao values ('26', 'Enfermaria de 4 ou mais leitos');

insert into tipo_atendimento values ('04', 'Consulta');
insert into tipo_atendimento values ('05', 'Exame ambulatorial');
insert into tipo_atendimento values ('07', 'Internação');
insert into tipo_atendimento values ('11', 'Pronto Socorro');

insert into tipo_consulta values ('01', 'Primeira consulta');
insert into tipo_consulta values ('02', 'Retorno');
insert into tipo_consulta values ('03', 'Pré-natal');
insert into tipo_consulta values ('04', 'Por encaminhamento');

insert into tipo_guia_consulta values ('01', 'Guia de consulta');
insert into tipo_guia_consulta values ('02', 'Guia SP/SADT');
insert into tipo_guia_consulta values ('03', 'Guia de internação');
insert into tipo_guia_consulta values ('04', 'Guia de honorários');
insert into tipo_guia_consulta values ('05', 'Guia de resumo de internação');



