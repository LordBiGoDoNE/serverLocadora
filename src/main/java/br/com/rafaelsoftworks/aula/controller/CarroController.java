package br.com.rafaelsoftworks.aula.controller;

import br.com.rafaelsoftworks.aula.filter.CarroFilter;
import br.com.rafaelsoftworks.aula.model.entity.Carro;
import br.com.rafaelsoftworks.aula.model.entity.Modelo;
import br.com.rafaelsoftworks.aula.service.CarroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/carro")
public class CarroController {

    @Autowired
    CarroService service;

    @GetMapping
    public ResponseEntity<List<Carro>> getCarros(@RequestParam Map<String,String> parametros) {
        CarroFilter filtro = new CarroFilter(parametros);
        return ResponseEntity.status(HttpStatus.OK).body(service.obterTodosCarros(filtro));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Carro> getCarro(@PathVariable(value = "id") Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarCarroPorId(id));
    }

    @PostMapping
    public ResponseEntity<Carro> insertCarro(@RequestBody Carro carro) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criarCarro(carro));
    }

    @PatchMapping
    public ResponseEntity<Carro> atualizarCarro(@RequestBody Carro carro) {
        return ResponseEntity.status(HttpStatus.OK).body(service.atualizarCarro(carro));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarCarro(@PathVariable(value = "id") Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.deletarCarro(id));
    }
}
