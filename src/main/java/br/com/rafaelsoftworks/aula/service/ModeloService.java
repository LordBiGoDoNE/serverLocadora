package br.com.rafaelsoftworks.aula.service;

import br.com.rafaelsoftworks.aula.exception.ValorNaoExistenteNaBaseDeDadosException;
import br.com.rafaelsoftworks.aula.model.entity.Fabricante;
import br.com.rafaelsoftworks.aula.model.entity.Modelo;
import br.com.rafaelsoftworks.aula.repository.cadastro.ModeloRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ModeloService {

    @Autowired
    ModeloRepositoryCustom repository;

    public List<Modelo> obterTodosModelos(Integer idFabricante) {
        return repository.buscarTodosModelos(idFabricante);
    }

    @Transactional
    public Modelo criarModelo(Modelo modelo) {
        Integer idModelo = repository.criarModelo(modelo);

        return repository.buscarModeloPorId(idModelo).get();
    }

    @Transactional
    public Modelo atualizarModelo(Modelo modelo) {
        checarSeExisteModelo(modelo.getId());

        repository.atualizarModelo(modelo);

        return buscarModeloPorId(modelo.getId());
    }

    @Transactional
    public String deletarModelo(Integer idModelo) {
        checarSeExisteModelo(idModelo);

        Integer quantidadeLinhasDeletadas = repository.deletarModelo(idModelo);

        return String.format("Modelo deletado com sucesso. Linhas afetadas %d", quantidadeLinhasDeletadas);
    }

    public Modelo buscarModeloPorId(Integer idModelo) {
        return repository.buscarModeloPorId(idModelo)
                .orElseThrow(() -> new ValorNaoExistenteNaBaseDeDadosException(idModelo.toString()));
    }

    private void checarSeExisteModelo(Integer idModelo) {
        boolean bool = repository.checarExistenciaModeloPorId(idModelo);

        if (!bool) {
            throw new ValorNaoExistenteNaBaseDeDadosException(idModelo.toString());
        }
    }

}