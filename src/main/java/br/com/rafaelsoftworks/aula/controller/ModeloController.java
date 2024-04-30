package br.com.rafaelsoftworks.aula.controller;

import br.com.rafaelsoftworks.aula.model.entity.Fabricante;
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
    public ResponseEntity<List<Fabricante>> getFabricantes() {
        return ResponseEntity.status(HttpStatus.OK).body(service.obterTodosFabricante());
    }
}
