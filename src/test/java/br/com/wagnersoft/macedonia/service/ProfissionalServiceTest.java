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

import br.com.wagnersoft.macedonia.model.GuiaEncaminhamento;
import br.com.wagnersoft.macedonia.model.Profissional;
import br.com.wagnersoft.macedonia.repository.ProfissionalRepository;

@ExtendWith(MockitoExtension.class)
class ProfissionalServiceTest {

  @Mock
  private ProfissionalRepository rep;

  @InjectMocks
  private ProfissionalService service;

  private Profissional b1;

  private Profissional b2;

  public ProfissionalServiceTest() {
    MockitoAnnotations.openMocks(this);
  }

  @BeforeEach
  void setup() {
    b1 = new Profissional();
    b1.setCpf("11111111111");
    b1.setNome("Ana");

    b2 = new Profissional();
    b2.setCpf("22222222222");
    b2.setNome("Carlos");
  }

  @Test
  void findByCpf_deveRetornarProfissionalQuandoExiste() {

    when(rep.findById("11111111111")).thenReturn(Optional.of(b1));

    Optional<Profissional> result = service.findByCpf("111.111.111-11");

    assertThat(result).isPresent()
                      .get()
                      .extracting(Profissional::getNome)
                      .isEqualTo("Ana");
  }

  @Test
  void findByCpf_deveRetornarEmptyQuandoCpfNulo() {
    Optional<Profissional> result = service.findByCpf(null);
    assertThat(result).isEmpty();
  }

  @Test
  void findByCpf_deveRetornarEmptyQuandoCpfVazio() {
    Optional<Profissional> result = service.findByCpf("   ");
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
    List<Profissional> result = service.findByNome(null);
    assertThat(result).isEmpty();
  }

  @Test
  void findByNome_deveRetornarListaVaziaQuandoNomeVazio() {
    List<Profissional> result = service.findByNome("   ");
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
  void mapAll_deveRetornarMapaVazioQuandoNaoHaProfissionals() {
    when(rep.findAll(any(Sort.class))).thenReturn(List.of());
    var mapa = service.mapAll();
    assertThat(mapa).isEmpty();
  }

  @Test
  void mapAll_deveManterPrimeiroProfissionalQuandoCpfDuplicado() {
    Profissional b3 = new Profissional();
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
  void remove_deveIgnorarCpfInexistente() {
    when(rep.findById("99999999999")).thenReturn(Optional.empty());
    service.remove("99999999999");
    verify(rep, never()).delete(any());
  }
  
  @Test
  void remove_deveExcluirProfissionalExistente() {
    when(rep.findById("22222222222")).thenReturn(Optional.of(b2));
    service.remove("22222222222");
    verify(rep).delete(b2);
  }

  @Test
  void remove_deveIgnorarProfissionalExistenteEGuiaExistente() {

    when(rep.findById("22222222222")).thenReturn(Optional.of(b2));
    
    GuiaEncaminhamento guia = new GuiaEncaminhamento();
    guia.setId(1);
    
    b2.addGuiaResponsavel(guia);

    service.remove("22222222222");
    
    b2.addGuiaSolicitante(guia);

    service.remove("22222222222");

    b2.removeGuiaResponsavel(guia);

    service.remove("22222222222");
    
    verify(rep, never()).delete(any());
  }
  
  @Test
  void add_deveIgnorarCpfNulo() {

    service.add(null);

    verify(rep, never()).save(any());
  }

  @Test
  void add_deveSalvarProfissionalInexistente() {

    when(rep.findById("22222222222")).thenReturn(Optional.empty());

    service.add(b2);

    verify(rep).findById("22222222222");
    verify(rep).save(b2);
  }

  @Test
  void add_deveSalvarProfissionalExistente() {
    b2.setNome("Carlos Atualizado");

    when(rep.findById("22222222222")).thenReturn(Optional.of(b2));
    service.add(b2);

    verify(rep).findById("22222222222");
    verify(rep).save(argThat(b -> b.getCpf().equals("22222222222") && b.getNome().equals("Carlos Atualizado")));
  }

}
