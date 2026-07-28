package br.com.wagnersoft.macedonia.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import br.com.wagnersoft.macedonia.model.Beneficiario;
import br.com.wagnersoft.macedonia.repository.BeneficiarioRepository;

@ExtendWith(MockitoExtension.class)
class BeneficiarioServiceTest {

  @Mock
  private BeneficiarioRepository rep;

  @InjectMocks
  private BeneficiarioService service;

  private Beneficiario b1;

  private Beneficiario b2;

  public BeneficiarioServiceTest() {
    MockitoAnnotations.openMocks(this);
  }

  @BeforeEach
  void setup() {
    b1 = new Beneficiario();
    b1.setCpf("11111111111");
    b1.setNome("Ana");

    b2 = new Beneficiario();
    b2.setCpf("22222222222");
    b2.setNome("Carlos");
  }

  @Test
  void findByCpf_deveRetornarBeneficiarioQuandoExiste() {

    when(rep.findById("11111111111")).thenReturn(Optional.of(b1));

    Optional<Beneficiario> result = service.findByCpf("111.111.111-11");

    assertThat(result).isPresent()
                      .get()
                      .extracting(Beneficiario::getNome)
                      .isEqualTo("Ana");
  }

  @Test
  void findByCpf_deveRetornarEmptyQuandoCpfNulo() {
    Optional<Beneficiario> result = service.findByCpf(null);
    assertThat(result).isEmpty();
  }

  @Test
  void findByCpf_deveRetornarEmptyQuandoCpfVazio() {
    Optional<Beneficiario> result = service.findByCpf("   ");
    assertThat(result).isEmpty();
  }

  @Test
  void findByNome_deveRetornarListaComNomePesquisado() {

    when(rep.findByNome("C")).thenReturn(List.of(b2));

    var lista = service.findByNome("C");

    assertThat(lista).isNotEmpty()
                     .containsExactly(b2);
  }

  @Test
  void findByNome_deveRetornarListaVaziaQuandoNomeNulo() {
    List<Beneficiario> result = service.findByNome(null);
    assertThat(result).isEmpty();
  }

  @Test
  void findByNome_deveRetornarListaVaziaQuandoNomeVazio() {
    List<Beneficiario> result = service.findByNome("   ");
    assertThat(result).isEmpty();
  }

  @Test
  void mapAll_deveRetornarMapaComCpfENome() {

    when(rep.findAll(any(Sort.class))).thenReturn(List.of(b1, b2));

    var mapa = service.mapAll();

    assertThat(mapa).containsEntry("11111111111", "Ana")
                    .containsEntry("22222222222", "Carlos")
                    .hasSize(2);
  }

  @Test
  void mapAll_deveRetornarMapaVazioQuandoNaoHaBeneficiarios() {
    when(rep.findAll(any(Sort.class))).thenReturn(List.of());

    var mapa = service.mapAll();

    assertThat(mapa).isEmpty();
  }

  @Test
  void mapAll_deveManterPrimeiroBeneficiarioQuandoCpfDuplicado() {
    Beneficiario b3 = new Beneficiario();
    b3.setCpf("11111111111");
    b3.setNome("Outro");

    when(rep.findAll(any(Sort.class))).thenReturn(List.of(b1, b3));

    var mapa = service.mapAll();

    assertThat(mapa).containsEntry("11111111111", "Ana") // mantém o primeiro
                    .hasSize(1);
  }

  @Test
  void remove_deveIgnorarCpfVazio() {

    service.remove("  ");

    verify(rep, never()).delete(any());
  }

  @Test
  void remove_deveIgnorarCpfNulo() {

    service.remove(null);

    verify(rep, never()).delete(any());
  }

  @Test
  void remove_deveExcluirBeneficiarioExistente() {

    when(rep.findById("22222222222")).thenReturn(Optional.of(b2));

    service.remove("22222222222");

    verify(rep).delete(b2);
  }

  @Test
  void add_deveIgnorarCpfNulo() {

    service.add(null);

    verify(rep, never()).save(any());
  }

  @Test
  void add_deveAtualizarBeneficiarioExistente() {

    when(rep.findById("22222222222")).thenReturn(Optional.of(b2));

    service.add(b2);

    verify(rep).findById("22222222222");
    verify(rep).save(b2);
  }

  @Test
  void add_deveSalvarBeneficiarioInexistente() {
    b2.setNome("Carlos Atualizado");

    when(rep.findById("22222222222")).thenReturn(Optional.empty());
    service.add(b2);

    verify(rep).findById("22222222222");
    verify(rep).save(argThat(b -> b.getCpf().equals("22222222222") && b.getNome().equals("Carlos Atualizado")));
  }

}
