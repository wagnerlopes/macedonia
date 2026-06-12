package br.com.wagnersoft.macedonia.controller;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import br.com.wagnersoft.macedonia.service.OcsPmService;

/** OcsPm Rest Controller.
 * @since 1.0
 * @version 1.0
 * @author Wagner Lopes
 */
@RestController
public class OcsPmRestController {

	private static final Logger logger = LoggerFactory.getLogger(OcsPmRestController.class);

	public record ProcedimentoDTO(Integer id, Integer ocs_id, Integer pm_id, String tuss, String unidadeMedida, BigDecimal valorUnitario) {};
	
    @Autowired
    private OcsPmService ocsPmSvc;

    @Autowired
    public OcsPmRestController() {
    }
    
    @GetMapping("/opm/{id}")
    public ResponseEntity<ProcedimentoDTO> findById(@PathVariable Integer id) {
    	logger.info("{}", id);
        return ocsPmSvc.findById(id)
        		    .map(opm -> new ProcedimentoDTO(opm.getId(), opm.getOcs().getId(), opm.getPm().getId(), opm.getPm().getTuss(), opm.getUnidadeMedida(), opm.getValorUnitario()))
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
    }    

}
