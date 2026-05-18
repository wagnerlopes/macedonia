package br.com.wagnersoft.macedonia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.wagnersoft.macedonia.model.CBO;

public interface CboRepository extends JpaRepository<CBO, String> {

	List<CBO> findByDescricao(String descricao);

}
