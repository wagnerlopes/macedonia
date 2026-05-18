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

insert into ocs(cnpj, descricao, especialidade, endereco, numero, complemento, municipio, uf, telefone, contato) values('062106505000192', 'Hospital Cruz Azul', 'Hopital Geral', 'Rua das Cruzes', '115', 'fundos', 'São Paulo', 'SP', '(91) 98234-4532', 'Maria das Dores');
insert into ocs(cnpj, descricao, especialidade, endereco, numero, complemento, municipio, uf, telefone, contato) values('061062212000198', 'Hospital Edmundo Vasconcelos', 'Maternidade', 'Avenida Principal', '265', 'Parque Clinico', 'Itajubá', 'MG', '(35) 97521-6789', 'Clara Gimenez');
insert into ocs(cnpj, descricao, especialidade, endereco, numero, complemento, municipio, uf, telefone, contato) values('061699567000192', 'Hospital São Paulo', 'Hospital Geral', 'Avenida Principal', '265', 'Parque Clinico', 'Chapecó', 'SC', '(43) 97521-6789', 'Clara Gimenez');
insert into ocs(cnpj, descricao, especialidade, endereco, numero, complemento, municipio, uf, telefone, contato) values('061599908000158', 'Beneficiência Portuguesa', 'Hospital e Maternidade', 'Avenida Principal', '265', 'Parque Clinico', 'Resende', 'RJ', '(21) 97521-6789', 'Clara Gimenez');

insert into dth(codigo, descricao, unidade, valor, ocs_id) values('1', 'UTI', 'DIARIA', 534.67, 1);
insert into dth(codigo, descricao, unidade, valor, ocs_id) values('2', 'Enfermaria', 'DIARIA', 215.98, 1);
insert into dth(codigo, descricao, unidade, valor, ocs_id) values('3', 'Quarto', 'DIARIA', 748.18, 1);
insert into dth(codigo, descricao, unidade, valor, ocs_id) values('1', 'UTI', 'DIARIA', 1025.37, 2);
insert into dth(codigo, descricao, unidade, valor, ocs_id) values('2', 'Enfermaria', 'DIARIA', 825.18, 2);
insert into dth(codigo, descricao, unidade, valor, ocs_id) values('3', 'Quarto', 'DIARIA', 1351.93, 2);

insert into procedimento_medico(amb90, amb92, amb96, amb99, tuss, descricao, ch_qtd, auxiliares_qtd, porte_anestesico) values ('00010022', '', '', '', '10101020', 'Consulta em domicílio', 50, 0, 0);
insert into procedimento_medico(amb90, amb92, amb96, amb99, tuss, descricao, ch_qtd, auxiliares_qtd, porte_anestesico) values ('00010014', '00010014', '00010014','00010014', '10101012', 'Consulta em consultório', 80, 0, 0);
insert into procedimento_medico(amb90, amb92, amb96, amb99, tuss, descricao, ch_qtd, auxiliares_qtd, porte_anestesico) values ('', '', '','00010065', '10101039', 'Consulta em pronto-socorro', 100, 0, 0);
insert into procedimento_medico(amb90, amb92, amb96, amb99, tuss, descricao, ch_qtd, auxiliares_qtd, porte_anestesico) values ('00020010', '00020010', '00020010','00020010', '10102019', 'Visita hospitalar (paciente internado)', 90, 0, 0);

insert into profissional(cpf, nome, crm, crm_uf, especialidade_codigo) values ('88888888888', 'Manoel Gomes', '45675', 'MG', 3);
insert into profissional(cpf, nome, crm, crm_uf, especialidade_codigo) values ('99999999999', 'Joaquim Teixeira', '112456', 'SP', 1);

insert into ocs_pm(ocs_id, pm_id, valor) values(1, 1, 321.74);
insert into ocs_pm(ocs_id, pm_id, valor) values(2, 1, 247.74);

insert into contrato(ocs_id, inicio_data, final_data, ch_valor) values (1, '2020-05-14', '2030-12-26', 20);
insert into contrato(ocs_id, inicio_data, final_data, ch_valor) values (2, '2021-08-23', '2031-05-12', 20);

insert into guia_encaminhamento(beneficiario_cpf, ocs_id, emissao_data, guia_nr, responsavel_cpf, solicitante_cpf, valor_total) values ('11111111111', 1, '2026-02-10', '145', '99999999999', '88888888888', 1654.87);
insert into guia_encaminhamento(beneficiario_cpf, ocs_id, emissao_data, guia_nr, responsavel_cpf, solicitante_cpf, valor_total) values ('22222222222', 2, '2026-03-15', '146', '99999999999', '88888888888', 321.18);
insert into guia_encaminhamento(beneficiario_cpf, ocs_id, emissao_data, guia_nr, responsavel_cpf, solicitante_cpf, valor_total) values ('22222222222', 1, '2026-01-21', '147', '99999999999', '88888888888', 528.16);
insert into guia_encaminhamento(beneficiario_cpf, ocs_id, emissao_data, guia_nr, responsavel_cpf, solicitante_cpf, valor_total) values ('33333333333', 2, '2026-02-23', '148', '99999999999', '88888888888', 159.98);
insert into guia_encaminhamento(beneficiario_cpf, ocs_id, emissao_data, guia_nr, responsavel_cpf, solicitante_cpf, valor_total) values ('44444444444', 1, '2026-04-16', '149', '99999999999', '88888888888', 692.38);

/*
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
*/
