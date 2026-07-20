package br.com.wagnersoft.macedonia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.wagnersoft.macedonia.model.Cbo;

/**
 * CBO repository. 
 * 
 * @since 1.0
 * @version 1.0
 * @author Wagner Lopes
 */
public interface CboRepository extends JpaRepository<Cbo, String> {

  List<Cbo> findByDescricao(String descricao);

}
