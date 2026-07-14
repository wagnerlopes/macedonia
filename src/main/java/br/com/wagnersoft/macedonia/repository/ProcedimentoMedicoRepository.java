package br.com.wagnersoft.macedonia.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.wagnersoft.macedonia.model.ProcedimentoMedico;

public interface ProcedimentoMedicoRepository extends JpaRepository<ProcedimentoMedico, Integer> {

  Optional<ProcedimentoMedico> findById(int id);

  List<ProcedimentoMedico> findByDescricao(String descricao);

  List<ProcedimentoMedico> findByAmb90(String amb90);

  List<ProcedimentoMedico> findByAmb92(String amb92);

  List<ProcedimentoMedico> findByAmb96(String amb96);

  List<ProcedimentoMedico> findByAmb99(String amb99);

  List<ProcedimentoMedico> findByTuss(String tuss);

}
