package br.com.wagnersoft.macedonia.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.wagnersoft.macedonia.model.GuiaEncaminhamento;

public interface GuiaEncaminhamentoRepository extends JpaRepository<GuiaEncaminhamento, Integer> {

	Optional<GuiaEncaminhamento> findById(int id);

	@Query("SELECT g FROM GuiaEncaminhamento g WHERE g.guiaNr = :numero")
	List<GuiaEncaminhamento> findByGuiaNr(@Param("numero") String numero);

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
