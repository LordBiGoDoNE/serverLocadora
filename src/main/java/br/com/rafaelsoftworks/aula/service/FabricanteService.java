package br.com.rafaelsoftworks.aula.service;

import br.com.rafaelsoftworks.aula.exception.ValorJaExistenteNaBaseDeDadosException;
import br.com.rafaelsoftworks.aula.exception.ValorNaoExistenteNaBaseDeDadosException;
import br.com.rafaelsoftworks.aula.model.entity.Fabricante;
import br.com.rafaelsoftworks.aula.repository.cadastro.FabricanteRepository;
import br.com.rafaelsoftworks.aula.repository.cadastro.FabricanteRepositoryJdbcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Classe de Regra de negocio referente a Fabricante.
 */
@Service
public class FabricanteService {

    @Autowired
    FabricanteRepository repository;

    @Autowired
    FabricanteRepositoryJdbcClient repositoryJdbcClient;

    public FabricanteService(FabricanteRepository repository, FabricanteRepositoryJdbcClient repositoryJdbcClient) {
        this.repository = repository;
        this.repositoryJdbcClient = repositoryJdbcClient;
    }


    public List<Fabricante> obterTodosFabricante() {
        return repositoryJdbcClient.buscarTodosFabricantes();
    }

    /**
     * Método de obtenção pelo ID.
     *
     * @param id Do Fabricante a ser Consultado.
     * @return @{@link Fabricante} filtrado.
     */
    public Fabricante obterFabricantePorId(Integer id) {
        return repository.findById(id).get();
    }

    /**
     * Método de inserção de Fabricante, passando pela regra e validando se o id já existe ou não.
     *
     * @param id   Do Fabricante a ser inserido, por padrâo é 0 e assim o banco que irá gerar o ID.
     * @param nome Do Fabricante a ser inserido.
     * @throws ValorJaExistenteNaBaseDeDadosException Caso já existir um fabricante com o mesmo ID informado.
     */
    public void inserirFabricante(Integer id, String nome) {
        Fabricante fabricante = new Fabricante();
        fabricante.setId(id);
        fabricante.setNome(nome);

        if (repository.existsById(id)) {
            throw new ValorJaExistenteNaBaseDeDadosException();
        }

        repository.save(fabricante);
    }

    /**
     * Método de inserção de Fabricante, passando pela regra e validando se o id já existe ou não.
     *
     * @param id do Fabricante a ser deletado.
     */
    public void deletarFabricante(Integer id) {
        repository.deleteById(id);
    }

    /**
     * Método de atualização de Fabricante, passando pela regra e validando se o id já existe ou não.
     *
     * @param fabricante Fabricante a ser atualizado com base na ID.
     * @throws ValorNaoExistenteNaBaseDeDadosException Caso já existir um fabricante com o mesmo ID informado.
     */
    public void atualizarFabricante(Fabricante fabricante) {
        Optional<Fabricante> fabricanteOptional = repository.findById(fabricante.getId());

        Fabricante fabricanteBanco = fabricanteOptional.orElseThrow(
                () -> new ValorNaoExistenteNaBaseDeDadosException(String.valueOf(fabricante.getId()))
        );

        fabricanteBanco.setNome(fabricante.getNome());

        repository.save(fabricanteBanco);
    }
}
