package com.cl932.rsmw.controller;

import com.cl932.rsmw.entity.PdkPdv;
import com.cl932.rsmw.repository.PdkPdvRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
public class PDZController {
    final
    PdkPdvRepository pdkPdvRepository;

    public PDZController(PdkPdvRepository pdkPdvRepository) {
        this.pdkPdvRepository = pdkPdvRepository;
    }

    @GetMapping("/pdz")
    public ResponseEntity<?> getPDZ() throws JsonProcessingException {
        var pdzInstance = ((List<PdkPdv>)pdkPdvRepository.findAll()).get(0);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(pdzInstance);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }
    @PostMapping("/pdz")
    public ResponseEntity<?> setPDZ(@RequestBody PDZDto pdz) throws JsonProcessingException {
        pdkPdvRepository.save(
                PdkPdv.builder()
                        .id(1L)
                        .PDK_CO(pdz.PDK_CO)
                        .PDK_CO2(pdz.PDK_CO2)
                        .PDK_NO(pdz.PDK_NO)
                        .PDK_NO2(pdz.PDK_NO2)
                        .PDK_NOx(pdz.PDK_NOx)
                        .PDK_SO2(pdz.PDK_SO2)
                        .PDK_CH4(pdz.PDK_CH4)
                        .PDK_dust(pdz.PDK_dust)
                        .PDV_CO(pdz.PDV_CO)
                        .PDV_CO2(pdz.PDV_CO2)
                        .PDV_NO(pdz.PDV_NO)
                        .PDV_NO2(pdz.PDV_NO2)
                        .PDV_NOx(pdz.PDV_NOx)
                        .PDV_SO2(pdz.PDV_SO2)
                        .PDV_CH4(pdz.PDV_CH4)
                        .PDV_dust(pdz.PDV_dust)
                        .build()
        );
        return new ResponseEntity<>("", HttpStatus.OK);
    }

}
