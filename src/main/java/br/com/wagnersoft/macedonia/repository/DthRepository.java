package br.com.wagnersoft.macedonia.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.wagnersoft.macedonia.model.Dth;
import br.com.wagnersoft.macedonia.model.Ocs;

public interface DthRepository extends JpaRepository<Dth, Integer> {

  Optional<Dth> findById(int id);

  List<Ocs> findByOcs(Ocs ocs);

  @Query("SELECT d FROM Dth d WHERE d.codigo = :codigo")
  List<Dth> findByCodigo(@Param("codigo") String codigo);

}
