package br.com.wagnersoft.macedonia.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.wagnersoft.macedonia.model.Beneficiario;

public interface BeneficiarioRepository extends JpaRepository<Beneficiario, String> {

	Optional<Beneficiario> findById(String cpf);
	
	List<Beneficiario> findByNome(String nome);

}
