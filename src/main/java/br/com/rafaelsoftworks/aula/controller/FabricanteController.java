package br.com.rafaelsoftworks.aula.controller;

import br.com.rafaelsoftworks.aula.exception.AbstractMinhaException;
import br.com.rafaelsoftworks.aula.model.entity.Fabricante;
import br.com.rafaelsoftworks.aula.model.json.response.ExceptionResponse;
import br.com.rafaelsoftworks.aula.service.FabricanteService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
    
    @GetMapping("/{id}")
    public ResponseEntity<Fabricante> getFabricantes(@PathVariable(value = "id") Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.obterFabricantePorId(id));
    }

    @PostMapping
    public ResponseEntity<String> salvarFabricante(@RequestBody Fabricante fabricante) {
        service.inserirFabricante(fabricante.getId(), fabricante.getNome());

        return ResponseEntity.status(HttpStatus.CREATED).body("Fabricante salvo com sucesso!");
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

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleAbstractMinhaException(AbstractMinhaException ex, HttpServletRequest request) throws IOException {
        return ResponseEntity.internalServerError().body(new ExceptionResponse(ex, request.getRequestURI()));
    }
}
