package br.com.wagnersoft.macedonia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.wagnersoft.macedonia.model.GuiaEncaminhamento;

/**
 * Repositório de dados para a entidade {@link GuiaEncaminhamento}.
 * <p>
 * Provê operações de acesso ao banco de dados e consultas customizadas
 * para gerenciamento de guia de encaminhamento.
 * </p>
 * 
 * @since 1.0
 * @version 1.0
 * @author Wagner Lopes
 */
public interface GuiaEncaminhamentoRepository extends JpaRepository<GuiaEncaminhamento, Integer> {

  @Query("SELECT g FROM GuiaEncaminhamento g WHERE g.guiaNr = :numero")
  List<GuiaEncaminhamento> findByGuiaNr(@Param("numero") Integer numero);

  @Query("SELECT g FROM GuiaEncaminhamento g WHERE g.beneficiario.id = :cpf")
  List<GuiaEncaminhamento> findByBeneficiario(@Param("cpf") String cpf);

  @Query("SELECT g FROM GuiaEncaminhamento g WHERE g.ocs.id = :id")
  List<GuiaEncaminhamento> findByOcs(@Param("id") Integer id);

  @Query("SELECT MONTH(g.emissaoData) AS mes, COUNT(g) " +
      "FROM GuiaEncaminhamento g WHERE YEAR(g.emissaoData) = :ano " +
      "GROUP BY MONTH(g.emissaoData) " +
      "ORDER BY MONTH(g.emissaoData)")
  List<Object[]> countByMonth(@Param("ano") Integer ano);

}
