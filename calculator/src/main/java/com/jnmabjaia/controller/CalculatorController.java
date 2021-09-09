package com.jnmabjaia.controller;

import com.jnmabjaia.constantes.RabbitMQConstantes;
import com.jnmabjaia.dto.OperacaoDto;
import com.jnmabjaia.services.RabbitMQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

@RestController
public class CalculatorController {
    @Autowired
    private RabbitMQService rabbitMQService;

    @GetMapping("/sum")
    public ResponseEntity<?> sum(@RequestParam(value = "a", required = true, defaultValue = "0") String a, @RequestParam(value = "b", required = true, defaultValue = "0") String b) {
        BigDecimal val1 = new BigDecimal(a).setScale(2, RoundingMode.CEILING);
        BigDecimal val2 = new BigDecimal(b).setScale(2, RoundingMode.CEILING);
        BigDecimal resultado = val1.add(val2).setScale(2, RoundingMode.CEILING);

        OperacaoDto msg = new OperacaoDto(a,b);
        this.rabbitMQService.enviaMensagem(RabbitMQConstantes.FILA_REST, msg);

        return  new ResponseEntity("Result: "+resultado.toPlainString(), HttpStatus.OK);
    }

    @GetMapping("/subtract")
    public ResponseEntity<?> subtract(@RequestParam(value = "a", required = true, defaultValue = "0") String a, @RequestParam(value = "b", required = true, defaultValue = "0") String b) {
        BigDecimal val1 = new BigDecimal(a).setScale(2, RoundingMode.CEILING);
        BigDecimal val2 = new BigDecimal(b).setScale(2, RoundingMode.CEILING);
        BigDecimal resultado = val1.subtract(val2).setScale(2, RoundingMode.CEILING);
        return  new ResponseEntity("Result: "+resultado.toPlainString(), HttpStatus.OK);
    }

    @GetMapping("/multiply")
    public ResponseEntity<?> multiply(@RequestParam(value = "a", required = true, defaultValue = "0") String a, @RequestParam(value = "b", required = true, defaultValue = "0") String b) {
        BigDecimal val1 = new BigDecimal(a).setScale(2, RoundingMode.CEILING);
        BigDecimal val2 = new BigDecimal(b).setScale(2, RoundingMode.CEILING);
        BigDecimal resultado = val1.multiply(val2).setScale(2, RoundingMode.CEILING);
        return  new ResponseEntity("Result: "+resultado.toPlainString(), HttpStatus.OK);
    }

    @GetMapping("/divide")
    public ResponseEntity<?> divide(@RequestParam(value = "a", required = true, defaultValue = "0") String a, @RequestParam(value = "b", required = true, defaultValue = "1") String b) {
        BigDecimal val1 = new BigDecimal(a).setScale(2, RoundingMode.CEILING);
        BigDecimal val2 = new BigDecimal(b).setScale(2, RoundingMode.CEILING);

        if(b.equals("0")){
            return  new ResponseEntity("Result: O denominador deve ser diferente de zero", HttpStatus.OK);
        }else {
            BigDecimal resultado = val1.divide(val2, RoundingMode.HALF_EVEN);
            return new ResponseEntity("Result: " + resultado.toPlainString(), HttpStatus.OK);
        }
    }

}
