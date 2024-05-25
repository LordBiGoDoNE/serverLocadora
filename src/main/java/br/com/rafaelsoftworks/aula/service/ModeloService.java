package br.com.rafaelsoftworks.aula.service;

import br.com.rafaelsoftworks.aula.exception.ValorNaoExistenteNaBaseDeDadosException;
import br.com.rafaelsoftworks.aula.filter.ModeloFilter;
import br.com.rafaelsoftworks.aula.model.dto.ModeloDTO;
import br.com.rafaelsoftworks.aula.model.entity.Modelo;
import br.com.rafaelsoftworks.aula.repository.cadastro.ModeloRepositoryJdbcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ModeloService {

    @Autowired
    ModeloRepositoryJdbcClient repository;

    /**
     * Busca todos os Modelos na base de dados.
     *
     * @param filter Filtro de Modelo com base nos parametros passados pela requisição.
     * @return Retorna lista de {@link ModeloDTO}.
     */
    public List<ModeloDTO> obterTodosModelos(ModeloFilter filter) {
        return repository.buscarTodosModelosDTO(filter);
    }

    /**
     * Método de inserção de Modelo, passando pela regra e validando se o id já existe ou não.
     *
     * @param modelo Modelo a ser inserido com base na ID.
     * @throws ValorNaoExistenteNaBaseDeDadosException Caso já existir um Modelo com o mesmo ID informado.
     */
    @Transactional
    public ModeloDTO criarModelo(Modelo modelo) {
        Integer idModelo = repository.criarModelo(modelo);

        return repository.buscarModeloPorId(idModelo).get();
    }

    /**
     * Método de atualização de Modelo, passando pela regra e validando se o id já existe ou não.
     *
     * @param modelo Modelo a ser atualizado com base na ID.
     * @throws ValorNaoExistenteNaBaseDeDadosException Caso já existir um Modelo com o mesmo ID informado.
     */
    @Transactional
    public ModeloDTO atualizarModelo(Modelo modelo) {
        checarSeExisteModelo(modelo.getId());

        repository.atualizarModelo(modelo);

        return buscarModeloPorId(modelo.getId());
    }

    /**
     * Método de exclusão de Modelo.
     *
     * @param id do Modelo a ser deletado.
     */
    @Transactional
    public String deletarModelo(Integer id) {
        checarSeExisteModelo(id);

        Integer quantidadeLinhasDeletadas = repository.deletarModelo(id);

        return String.format("Modelo deletado com sucesso. Linhas afetadas %d", quantidadeLinhasDeletadas);
    }

    /**
     * Busca Modelo pelo ID na base de dados.
     *
     * @param id do {@link Modelo} a ser consultado.
     * @return Retorna {@link ModeloDTO} com base no {@link Modelo} consultado.
     */
    public ModeloDTO buscarModeloPorId(Integer id) {
        return repository.buscarModeloPorId(id)
                .orElseThrow(() -> new ValorNaoExistenteNaBaseDeDadosException(id.toString()));
    }

    /**
     * Verifica se o Modelo existe na base de dados.
     *
     * @param idModelo do {@link Modelo} a ser consultado.
     * @throws ValorNaoExistenteNaBaseDeDadosException caso não exista um Modelo com determinado ID.
     */
    private void checarSeExisteModelo(Integer idModelo) {
        boolean bool = repository.checarExistenciaModeloPorId(idModelo);

        if (!bool) {
            throw new ValorNaoExistenteNaBaseDeDadosException(idModelo.toString());
        }
    }

}