package br.com.rafaelsoftworks.aula.service;

import br.com.rafaelsoftworks.aula.exception.ValorJaExistenteNaBaseDeDadosException;
import br.com.rafaelsoftworks.aula.model.entity.Fabricante;
import br.com.rafaelsoftworks.aula.repository.cadastro.FabricanteRepository;
import br.com.rafaelsoftworks.aula.repository.cadastro.FabricanteRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FabricanteService {

    @Autowired
    FabricanteRepository repository;

    @Autowired
    FabricanteRepositoryCustom repositoryJdbcClient;

    public FabricanteService(FabricanteRepository repository, FabricanteRepositoryCustom repositoryJdbcClient) {
        this.repository = repository;
        this.repositoryJdbcClient = repositoryJdbcClient;
    }

    public List<Fabricante> obterTodosFabricante() {
        return repositoryJdbcClient.buscarTodosFabricantes();
    }

    public void inserirFabricante(Integer id, String nome) {
        Fabricante fabricante = new Fabricante();
        fabricante.setId(id);
        fabricante.setNome(nome);

        if (repository.existsById(id)) {
            throw new ValorJaExistenteNaBaseDeDadosException();
        }

        repository.save(fabricante);
    }

    public void deletarFabricante(Integer id) {
        repository.deletarPorID(id);
    }

    public void atualizarFabricante(Fabricante fabricante) {
        Optional<Fabricante> fabricanteOptional = repository.findById(fabricante.getId());

        Fabricante fabricanteBanco = fabricanteOptional.orElseThrow(
                () -> new RuntimeException("Fabricante não encontrado")
        );

        fabricanteBanco.setNome(fabricante.getNome());

        repository.save(fabricanteBanco);
    }
}
