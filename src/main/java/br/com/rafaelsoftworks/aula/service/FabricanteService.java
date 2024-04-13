package br.com.rafaelsoftworks.aula.service;

import br.com.rafaelsoftworks.aula.model.entity.Fabricante;
import br.com.rafaelsoftworks.aula.repository.fabricante.FabricanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FabricanteService {

    @Autowired
    FabricanteRepository repository;

    public List<Fabricante> obterTodosFabricante() {
        return repository.findAll();
    }
}
