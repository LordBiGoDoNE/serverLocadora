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

    public void inserirFabricante(String nome) {
        Fabricante fabricante = new Fabricante();
        fabricante.setNome(nome);

        repository.save(fabricante);
    }

    public void deletarFabricante(Integer id) {
        repository.deletarPorID(id);
    }

    public void atualizarFabricante(Fabricante fabricante) {
        Fabricante fabricanteBanco = repository.findById(fabricante.getId()).orElseThrow(
                () -> new RuntimeException("Fabricante não encontrado")
        );

        fabricanteBanco.setNome(fabricante.getNome());

        repository.save(fabricanteBanco);
    }
}
