package br.com.wagnersoft.macedonia.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.wagnersoft.macedonia.service.TissService;
import br.com.wagnersoft.macedonia.type.GuiaFaturamento;
import br.com.wagnersoft.macedonia.type.TissReponseDTO;

@RestController
@RequestMapping("/api/tiss")
public class TissController {

	private static final Logger logger = LoggerFactory.getLogger(TissController.class);

	@Autowired
	private TissService tissSvc;
	
    public TissController() {
        super();
        logger.debug("{} inicializado.", TissController.class.getCanonicalName());
    }

    @GetMapping
    public ResponseEntity<TissReponseDTO> getAll() {
		logger.info("+++ TISS +++");
        final List<GuiaFaturamento> lista = tissSvc.listAll();
        logger.debug("{}", lista);
        final TissReponseDTO dto = TissReponseDTO.builder().guiaFaturamento(lista.get(0)).build();
        return ResponseEntity.ok(dto);
    }    

    @GetMapping("/{id}")
    public ResponseEntity<TissReponseDTO> getById(@PathVariable Integer id) {
        final GuiaFaturamento guia = tissSvc.findById(id);
        logger.debug("{}", guia);
        if (guia == null) return ResponseEntity.notFound().build();
        final TissReponseDTO dto = TissReponseDTO.builder().guiaFaturamento(guia).build();
        return ResponseEntity.ok(dto);
    }

}
