package br.com.rafaelsoftworks.aula.controller;

import br.com.rafaelsoftworks.aula.model.entity.Fabricante;
import br.com.rafaelsoftworks.aula.service.FabricanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/fabricante")
public class FabricanteController {

    @Autowired
    FabricanteService service;

    @GetMapping
    public ResponseEntity<List<Fabricante>> getFabricantes() {
        return ResponseEntity.status(HttpStatus.OK).body(service.obterTodosFabricante());
    }

    @PostMapping
    public ResponseEntity<String> salvarFabricante(@RequestBody Fabricante fabricante) {
        try {
            service.inserirFabricante(fabricante.getNome());

            return ResponseEntity.status(HttpStatus.CREATED).body("Fabricante salvo com sucesso!");
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body("Erro ao inserir novo Fabricante");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarFabricante(@PathVariable(value = "id") Integer id) {
        try {
            service.deletarFabricante(id);

            return ResponseEntity.status(HttpStatus.CREATED).body("Fabricante excluido com sucesso!");
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body("Erro ao excluir Fabricante");
        }
    }

    @PatchMapping
    public ResponseEntity<String> atualizarFabricante(@RequestBody Fabricante fabricante) {
        try {
            service.atualizarFabricante(fabricante);

            return ResponseEntity.status(HttpStatus.CREATED).body("Fabricante atualizado com sucesso!");
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body("Erro ao atualizar Fabricante: " + ex.getMessage());
        }
    }
}
