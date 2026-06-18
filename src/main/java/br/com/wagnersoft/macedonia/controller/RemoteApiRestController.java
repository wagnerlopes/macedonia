package br.com.wagnersoft.macedonia.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.wagnersoft.macedonia.service.GuiaEncaminhamentoService;
import br.com.wagnersoft.macedonia.service.OcsPmService;

/** Remote API Rest Controller.
 * @since 1.0
 * @version 1.0
 * @author Wagner Lopes
 */
@RestController
@RequestMapping("/api")
public class RemoteApiRestController {

  private static final Logger logger = LoggerFactory.getLogger(RemoteApiRestController.class);

  @Autowired
  private GuiaEncaminhamentoService guiaSvc;

  @Autowired
  private OcsPmService ocsPmSvc;

  public record ProcedimentoDTO(Integer id, Integer ocs_id, Integer pm_id, String tuss, String unidadeMedida, BigDecimal valorUnitario) {};

  public record ChartDTO(String[] xValues, Integer[] yValues) {};

  public RemoteApiRestController() {
    super();
    logger.info("{} loaded", RemoteApiRestController.class.getSimpleName());
  }

  @GetMapping("/opm/{id}")
  public ResponseEntity<ProcedimentoDTO> findById(@PathVariable Integer id) {
    logger.debug("ID = {}", id);
    return ocsPmSvc.findById(id)
        .map(opm -> new ProcedimentoDTO(opm.getId(), opm.getOcs().getId(), opm.getPm().getId(), opm.getPm().getTuss(), opm.getUnidadeMedida(), opm.getValorUnitario()))
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }    

  @GetMapping("/chart")
  public ResponseEntity<ChartDTO> computeChartValues() {
    String[] xValues = {"Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"};
    
    //Integer[] yValues = {10, 13, 25, 22, 15, 9, 30, 18, 21, 28, 12, 35}; // Teste values

    List<Object[]> mesData = guiaSvc.countByMonth(LocalDate.now().getYear());

    Map<Integer, Long> totalsByMonth = mesData.stream()
        .filter(Objects::nonNull)
        .collect(Collectors.toMap(
            row -> ((Number) row[0]).intValue(),  // mes
            row -> ((Number) row[1]).longValue(), // total
            Long::sum                             // em caso de duplicatas
        ));
    
    logger.debug("MAP DATA = {}", totalsByMonth);

    Integer[] yValues = IntStream.rangeClosed(1, xValues.length)
        .mapToObj(m -> totalsByMonth.getOrDefault(m, 0L).intValue())
        .toArray(Integer[]::new);

    return ResponseEntity.ok(new ChartDTO(xValues, yValues));
  }
  
}
