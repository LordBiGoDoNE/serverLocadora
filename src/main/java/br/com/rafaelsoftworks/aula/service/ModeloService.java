package br.com.rafaelsoftworks.aula.service;

import br.com.rafaelsoftworks.aula.model.entity.Fabricante;
import br.com.rafaelsoftworks.aula.repository.cadastro.FabricanteRepository;
import br.com.rafaelsoftworks.aula.repository.cadastro.FabricanteRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModeloService {

    @Autowired
    FabricanteRepositoryCustom repositoryJdbcClient;

    public List<Fabricante> obterTodosFabricante() {
        return repositoryJdbcClient.buscarTodosFabricantes();
    }

}