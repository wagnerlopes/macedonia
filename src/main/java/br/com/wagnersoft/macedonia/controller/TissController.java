package br.com.wagnersoft.macedonia.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.wagnersoft.macedonia.service.TissService;
import br.com.wagnersoft.macedonia.tiss.GuiaFaturamento;
import br.com.wagnersoft.macedonia.tiss.TissReponseDTO;

/**
 * Rest Controller Spring MVC responsável por gerenciar as requisições relacionadas aos <strong>beneficiários</strong>.
 * <p>
 * Centraliza os endpoints referentes a consulta da <strong>API TISS</strong> 
 * no sistema através do caminho base {@code /api/tiss}.
 * </p>
 * @since 1.0
 * @version 1.0
 * @author Wagner Lopes
 */
@RestController
@RequestMapping("/api/tiss")
public class TissController {

  private static final Logger logger = LoggerFactory.getLogger(TissController.class);

  @Autowired
  private TissService tissSvc;

  public TissController() {
    super();
    logger.debug("{} loaded", TissController.class.getSimpleName());
  }

  @GetMapping
  public ResponseEntity<TissReponseDTO> getAll() {
    logger.info("+++ TISS +++");
    final TissReponseDTO dto = TissReponseDTO.builder().guiaFaturamento(GuiaFaturamento.empty()).build();
    return ResponseEntity.ok(dto);
  }    

  @GetMapping("/{id}")
  public ResponseEntity<TissReponseDTO> getById(@PathVariable Integer id) {
    final TissReponseDTO dto = TissReponseDTO.builder().guiaFaturamento(tissSvc.findById(id)).build();
    logger.debug("{}", dto);
    return ResponseEntity.ok(dto);
  }

}
