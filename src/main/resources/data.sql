insert into beneficiario(cpf, nome, nascimento_data) values('11111111111', 'Jose Gomes', '2000-03-01');
insert into beneficiario(cpf, nome, nascimento_data) values('22222222222', 'Maria Tome', '1995-10-06');
insert into beneficiario(cpf, nome, nascimento_data) values('33333333333', 'Luiz Frias', '1987-08-15');
insert into beneficiario(cpf, nome, nascimento_data) values('44444444444', 'Adelaide Terra', '2001-02-04');
insert into beneficiario(cpf, nome, nascimento_data) values('55555555555', 'Fernando Pires', '2010-12-07');

insert into cbo(codigo, descricao) values('2251-10', 'Médico clínico geral');
insert into cbo(codigo, descricao) values('2251-15', 'Médico especialista');
insert into cbo(codigo, descricao) values('3221-05', 'Enfermeiro');
insert into cbo(codigo, descricao) values('3222-05', 'Técnico de enfermagem');
insert into cbo(codigo, descricao) values('2235-05', 'Fisioterapeuta');
insert into cbo(codigo, descricao) values('2231-10', 'Terapeuta ocupacional');
insert into cbo(codigo, descricao) values('2511-10', 'Psicólogo clínico');
insert into cbo(codigo, descricao) values('3223-05', 'Auxiliar de enfermagem');
insert into cbo(codigo, descricao) values('2265-10', 'Dentista');
insert into cbo(codigo, descricao) values('2241-10', 'Nutricionista');

insert into ocs(cnpj, descricao, especialidade, endereco, numero, complemento, municipio, uf, telefone, contato) values('062106505000192', 'Hospital Cruz Azul', '01', 'Rua das Cruzes', '115', 'fundos', 'São Paulo', 'SP', '(91) 98234-4532', 'Maria das Dores');
insert into ocs(cnpj, descricao, especialidade, endereco, numero, complemento, municipio, uf, telefone, contato) values('061062212000198', 'Hospital Edmundo Vasconcelos', '02', 'Avenida Principal', '265', 'Parque Clinico', 'Itajubá', 'MG', '(35) 97521-6789', 'Clara Gimenez');
insert into ocs(cnpj, descricao, especialidade, endereco, numero, complemento, municipio, uf, telefone, contato) values('061699567000192', 'Hospital São Paulo', '03', 'Avenida Principal', '265', 'Parque Clinico', 'Chapecó', 'SC', '(43) 97521-6789', 'Clara Gimenez');
insert into ocs(cnpj, descricao, especialidade, endereco, numero, complemento, municipio, uf, telefone, contato) values('061599908000158', 'Beneficiência Portuguesa', '04', 'Avenida Principal', '265', 'Parque Clinico', 'Resende', 'RJ', '(21) 97521-6789', 'Clara Gimenez');

insert into dth(codigo, descricao, unidade_medida, valor_unitario, ocs_id) values('1', 'UTI', '01', 534.67, 1);
insert into dth(codigo, descricao, unidade_medida, valor_unitario, ocs_id) values('2', 'Enfermaria', '01', 215.98, 1);
insert into dth(codigo, descricao, unidade_medida, valor_unitario, ocs_id) values('3', 'Quarto', '01', 748.18, 1);
insert into dth(codigo, descricao, unidade_medida, valor_unitario, ocs_id) values('1', 'UTI', '01', 1025.37, 2);
insert into dth(codigo, descricao, unidade_medida, valor_unitario, ocs_id) values('2', 'Enfermaria', '01', 825.18, 2);
insert into dth(codigo, descricao, unidade_medida, valor_unitario, ocs_id) values('3', 'Quarto', '01', 1351.93, 2);

insert into procedimento_medico(amb90, amb92, amb96, amb99, tuss, descricao, ch_qtd, auxiliares_qtd, porte_anestesico) values ('00010022', '', '', '', '10101020', 'Consulta em domicílio', 50, 0, 0);
insert into procedimento_medico(amb90, amb92, amb96, amb99, tuss, descricao, ch_qtd, auxiliares_qtd, porte_anestesico) values ('00010014', '00010014', '00010014','00010014', '10101012', 'Consulta em consultório', 80, 0, 0);
insert into procedimento_medico(amb90, amb92, amb96, amb99, tuss, descricao, ch_qtd, auxiliares_qtd, porte_anestesico) values ('', '', '','00010065', '10101039', 'Consulta em pronto-socorro', 100, 0, 0);
insert into procedimento_medico(amb90, amb92, amb96, amb99, tuss, descricao, ch_qtd, auxiliares_qtd, porte_anestesico) values ('00020010', '00020010', '00020010','00020010', '10102019', 'Visita hospitalar (paciente internado)', 90, 0, 0);

insert into registro_profissional(conselho, numero, uf) values ('CRM', '457890', 'SP');
insert into registro_profissional(conselho, numero, uf) values ('CRM', '1012453', 'MG');

insert into profissional(cpf, nome, registro_id, cbo_codigo) values ('88888888888', 'Manoel Gomes', 1, '2251-10');
insert into profissional(cpf, nome, registro_id, cbo_codigo) values ('99999999999', 'Joaquim Teixeira', 2,'2251-15');

insert into ocs_pm(ocs_id, pm_id, unidade_medida, valor_unitario) values(1, 1, '04', 321.74);
insert into ocs_pm(ocs_id, pm_id, unidade_medida, valor_unitario) values(2, 1, '04', 247.74);

insert into contrato(ocs_id, inicio_data, termino_data, ch_qtd) values (1, '2020-05-14', '2030-12-26', 10);
insert into contrato(ocs_id, inicio_data, termino_data, ch_qtd) values (2, '2021-08-23', '2031-05-12', 20);

insert into guia_encaminhamento(beneficiario_cpf, ocs_id, emissao_data, guia_nr, responsavel_cpf, solicitante_cpf, valor_total) values ('11111111111', 1, '2026-02-10', '145', '99999999999', '88888888888', 321.74);
insert into guia_encaminhamento(beneficiario_cpf, ocs_id, emissao_data, guia_nr, responsavel_cpf, solicitante_cpf, valor_total) values ('22222222222', 2, '2026-03-15', '146', '99999999999', '88888888888', 569.58);
insert into guia_encaminhamento(beneficiario_cpf, ocs_id, emissao_data, guia_nr, responsavel_cpf, solicitante_cpf, valor_total) values ('22222222222', 1, '2026-01-21', '147', '99999999999', '88888888888', 321.74);
insert into guia_encaminhamento(beneficiario_cpf, ocs_id, emissao_data, guia_nr, responsavel_cpf, solicitante_cpf, valor_total) values ('33333333333', 2, '2026-02-23', '148', '99999999999', '88888888888', 247.74);
insert into guia_encaminhamento(beneficiario_cpf, ocs_id, emissao_data, guia_nr, responsavel_cpf, solicitante_cpf, valor_total) values ('44444444444', 1, '2026-04-16', '149', '99999999999', '88888888888', 321.74);

insert into guia_pm (guia_id, pm_id, pm_qtd, valor_unitario, pos_auditoria, unidade_medida) values (1, 1, 1, 327.27, 250.00, '01');
insert into guia_pm (guia_id, pm_id, pm_qtd, valor_unitario, pos_auditoria, unidade_medida) values (2, 1, 1, 301.53, 270.00, '01');
insert into guia_pm (guia_id, pm_id, pm_qtd, valor_unitario, pos_auditoria, unidade_medida) values (2, 2, 1, 312.14, 210.00, '01');
insert into guia_pm (guia_id, pm_id, pm_qtd, valor_unitario, pos_auditoria, unidade_medida) values (3, 1, 1, 291.24, 290.00, '01');
insert into guia_pm (guia_id, pm_id, pm_qtd, valor_unitario, pos_auditoria, unidade_medida) values (4, 2, 1, 311.34, 300.00, '01');
insert into guia_pm (guia_id, pm_id, pm_qtd, valor_unitario, pos_auditoria, unidade_medida) values (5, 1, 1, 300.20, 290.00, '01');
