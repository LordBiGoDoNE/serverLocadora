package br.com.rafaelsoftworks.aula.controller;

import br.com.rafaelsoftworks.aula.model.entity.Fabricante;
import br.com.rafaelsoftworks.aula.model.entity.Modelo;
import br.com.rafaelsoftworks.aula.service.FabricanteService;
import br.com.rafaelsoftworks.aula.service.ModeloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/modelo")
public class ModeloController {

    @Autowired
    ModeloService service;

    @GetMapping
    public ResponseEntity<List<Modelo>> getModelos(
            @RequestParam(defaultValue = "0", required = false) Integer idFabricante) {
        return ResponseEntity.status(HttpStatus.OK).body(service.obterTodosModelos(idFabricante));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Modelo> getModelo(@PathVariable(value = "id") Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarModeloPorId(id));
    }

    @PostMapping
    public ResponseEntity<Modelo> insertModelo(@RequestBody Modelo modelo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criarModelo(modelo));
    }

    @PatchMapping
    public ResponseEntity<Modelo> atualizarModelo(@RequestBody Modelo modelo) {
        return ResponseEntity.status(HttpStatus.OK).body(service.atualizarModelo(modelo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarModelo(@PathVariable(value = "id") Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.deletarModelo(id));
    }
}
