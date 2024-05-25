package br.com.rafaelsoftworks.aula.controller;

import br.com.rafaelsoftworks.aula.filter.CarroFilter;
import br.com.rafaelsoftworks.aula.model.entity.Carro;
import br.com.rafaelsoftworks.aula.service.CarroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/carro")
public class CarroController implements CrudController<Carro> {

    @Autowired
    CarroService service;

    @Override
    public ResponseEntity<?> get(Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarCarroPorId(id));
    }

    @Override
    public ResponseEntity<List<?>> getAll(Map<String, String> parametros) {
        CarroFilter filtro = new CarroFilter(parametros);
        return ResponseEntity.status(HttpStatus.OK).body(service.obterTodosCarros(filtro));
    }

    @Override
    public ResponseEntity<?> insert(Carro carro) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criarCarro(carro));
    }

    @Override
    public ResponseEntity<?> update(Carro carro) {
        return ResponseEntity.status(HttpStatus.OK).body(service.atualizarCarro(carro));
    }

    @Override
    public ResponseEntity<String> delete(Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.deletarCarro(id));
    }

}
