package br.com.rafaelsoftworks.aula.controller;

import br.com.rafaelsoftworks.aula.model.entity.Fabricante;
import br.com.rafaelsoftworks.aula.service.FabricanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/fabricante")
public class FabricanteController {

    @Autowired
    FabricanteService service;

    @GetMapping
    public ResponseEntity<List<Fabricante>> getFabricantes(){
        return ResponseEntity.ok(service.obterTodosFabricante());
    }
}
