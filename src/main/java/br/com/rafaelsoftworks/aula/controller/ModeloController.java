package br.com.rafaelsoftworks.aula.controller;

import br.com.rafaelsoftworks.aula.filter.ModeloFilter;
import br.com.rafaelsoftworks.aula.model.dto.ModeloDTO;
import br.com.rafaelsoftworks.aula.model.entity.Modelo;
import br.com.rafaelsoftworks.aula.service.ModeloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/modelo")
public class ModeloController implements CrudController<Modelo>{

    @Autowired
    ModeloService service;

    @Override
    public ResponseEntity<?> get(Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarModeloPorId(id));
    }

    @Override
    public ResponseEntity<List<?>> getAll(Map<String, String> parametros) {
        ModeloFilter filter = new ModeloFilter(parametros);

        return ResponseEntity.status(HttpStatus.OK).body(service.obterTodosModelos(filter));
    }

    @Override
    public ResponseEntity<?> insert(Modelo modelo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criarModelo(modelo));
    }

    @Override
    public ResponseEntity<?> update(Modelo modelo) {
        return ResponseEntity.status(HttpStatus.OK).body(service.atualizarModelo(modelo));
    }

    @Override
    public ResponseEntity<String> delete(Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.deletarModelo(id));
    }
}
