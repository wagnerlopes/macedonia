package br.com.wagnersoft.macedonia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.wagnersoft.macedonia.model.Profissional;

public interface ProfissionalRepository extends JpaRepository<Profissional, String> {

  @Query("SELECT p FROM Profissional p WHERE LOWER(p.nome) LIKE LOWER(CONCAT(:nome, '%'))")
  List<Profissional> findByNome(@Param("nome") String nome);

  @Query("SELECT p FROM Profissional p WHERE p.registroProfissional.numero = :numero")
  List<Profissional> findByRegistroProfissional(@Param("numero") String numero);

  @Query("SELECT p FROM Profissional p WHERE p.cbo.codigo = :codigo ORDER BY p.nome")
  List<Profissional> findByCBO(@Param("codigo") int codigo);

}
