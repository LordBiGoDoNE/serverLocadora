package br.com.rafaelsoftworks.aula.service;

import br.com.rafaelsoftworks.aula.exception.ValorNaoExistenteNaBaseDeDadosException;
import br.com.rafaelsoftworks.aula.filter.CarroFilter;
import br.com.rafaelsoftworks.aula.model.dto.CarroDTO;
import br.com.rafaelsoftworks.aula.model.entity.Carro;
import br.com.rafaelsoftworks.aula.repository.cadastro.CarroRepositoryJdbcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CarroService {

    @Autowired
    CarroRepositoryJdbcClient repository;

    /**
     * Busca todos os Carros na base de dados.
     *
     * @param filtro Filtro de Carro com base nos parametros passados pela requisição.
     * @return Retorna lista de {@link CarroDTO}.
     */
    public List<CarroDTO> obterTodosCarros(CarroFilter filtro) {
        return repository.buscarTodosCarros(filtro);
    }

    /**
     * Método de inserção de {@link Carro}, passando pela regra e validando se o id já existe ou não.
     *
     * @param carro {@link Carro} a ser inserido com base na ID.
     * @throws ValorNaoExistenteNaBaseDeDadosException Caso já existir um Carro com o mesmo ID informado.
     */
    @Transactional
    public CarroDTO criarCarro(Carro carro) {
        validarCarro(carro);

        Integer idCarro = repository.criarCarro(carro);

        return buscarCarroPorId(idCarro);
    }

    /**
     * Método de atualização de {@link Carro}, passando pela regra e validando se o id já existe ou não.
     *
     * @param carro {@link Carro} a ser atualizado com base na ID.
     * @throws ValorNaoExistenteNaBaseDeDadosException Caso já existir um Carro com o mesmo ID informado.
     */
    @Transactional
    public CarroDTO atualizarCarro(Carro carro) {
        checarSeExisteCarro(carro.getId());

        validarCarro(carro);

        repository.atualizarCarro(carro);

        return buscarCarroPorId(carro.getId());
    }

    /**
     * Método de exclusão de Carro.
     *
     * @param id do Carro a ser deletado.
     */
    @Transactional
    public String deletarCarro(Integer id) {
        checarSeExisteCarro(id);

        Integer quantidadeLinhasDeletadas = repository.deletarCarro(id);

        return String.format("Carro deletado com sucesso. Linhas afetadas %d", quantidadeLinhasDeletadas);
    }

    /**
     * Busca Carro pelo ID na base de dados.
     *
     * @param id do {@link Carro} a ser consultado.
     * @return Retorna {@link CarroDTO} com base no {@link Carro} consultado.
     */
    public CarroDTO buscarCarroPorId(Integer id) {
        return repository.buscarCarroPorId(id)
                .orElseThrow(() -> new ValorNaoExistenteNaBaseDeDadosException(id.toString()));
    }

    /**
     * Verifica se o Carro existe na base de dados.
     *
     * @param id do {@link Carro} a ser consultado.
     * @throws ValorNaoExistenteNaBaseDeDadosException caso não exista um Carro com determinado ID.
     */
    private void checarSeExisteCarro(Integer id) {
        boolean bool = repository.checarExistenciaCarroPorId(id);

        if (!bool) {
            throw new ValorNaoExistenteNaBaseDeDadosException(id.toString());
        }
    }

    /**
     * Executa validação de carro.
     *
     * @param carro {@link Carro} a ser validado.
     * @throws ValorNaoExistenteNaBaseDeDadosException caso não exista um Carro com determinado ID.
     */
    private void validarCarro(Carro carro) {
        carro.setPlaca(carro.getPlaca().toUpperCase());
        carro.setCor(carro.getCor().toUpperCase());
    }
}