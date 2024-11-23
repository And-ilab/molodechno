package com.cl932.rsmw.controller;

import com.cl932.rsmw.RsmwProxy;
import com.cl932.rsmw.entity.CurrentValue;
import com.cl932.rsmw.repository.CurrentRepository;
import com.cl932.rsmw.util.MsExtractor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@Slf4j
public class TestController {
    final
    RsmwProxy rsmwProxy;
    MsExtractor msExtractor;
    final
    CurrentRepository currentRepository;

    public TestController(RsmwProxy rsmwProxy, MsExtractor msExtractor, CurrentRepository currentRepository) {
        this.rsmwProxy = rsmwProxy;
        this.msExtractor = msExtractor;
        this.currentRepository = currentRepository;
    }

    @GetMapping("/test")
    public ResponseEntity<?> test() throws SQLException, JsonProcessingException {
        StringBuilder sb = new StringBuilder();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        int counter = 0;
        var records = ((List<CurrentValue>)currentRepository.findAll()).reversed();
        for (int i = 0; i < 10; i++) {
            sb.append(ow.writeValueAsString(records.get(i)));
        }
        String res = sb.toString();
        System.out.println(res);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/v1/agent/resend")
    public ResponseEntity<String> testApi(@RequestBody String s) {
        log.info(s);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/forceRecord")
    public ResponseEntity<String> forceRecord() {
        rsmwProxy.writeDay();
        return new ResponseEntity<>("DONE", HttpStatus.OK);
    }
    @GetMapping("/forceClear")
    public ResponseEntity<String> forceClear() throws SQLException {
        msExtractor.clearOldPeriod();
        return new ResponseEntity<>("DONE", HttpStatus.OK);
    }

    @GetMapping("/forcePeriod")
    public ResponseEntity<String> forcePeriod() throws SQLException {
        rsmwProxy.writePeriod();
        return new ResponseEntity<>("DONE", HttpStatus.OK);
    }
}
