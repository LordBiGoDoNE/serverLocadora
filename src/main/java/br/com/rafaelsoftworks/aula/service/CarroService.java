package br.com.rafaelsoftworks.aula.service;

import br.com.rafaelsoftworks.aula.exception.ValorNaoExistenteNaBaseDeDadosException;
import br.com.rafaelsoftworks.aula.filter.CarroFilter;
import br.com.rafaelsoftworks.aula.model.entity.Carro;
import br.com.rafaelsoftworks.aula.repository.cadastro.CarroRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CarroService {

    @Autowired
    CarroRepositoryCustom repository;

    public List<Carro> obterTodosCarros(CarroFilter filtro) {
        return repository.buscarTodosCarros(filtro);
    }

    @Transactional
    public Carro criarCarro(Carro carro) {
        validarCarro(carro);

        Integer idCarro = repository.criarCarro(carro);

        return buscarCarroPorId(idCarro);
    }

    @Transactional
    public Carro atualizarCarro(Carro carro) {
        checarSeExisteCarro(carro.getId());

        validarCarro(carro);

        repository.atualizarCarro(carro);

        return buscarCarroPorId(carro.getId());
    }

    @Transactional
    public String deletarCarro(Integer idCarro) {
        checarSeExisteCarro(idCarro);

        Integer quantidadeLinhasDeletadas = repository.deletarCarro(idCarro);

        return String.format("Carro deletado com sucesso. Linhas afetadas %d", quantidadeLinhasDeletadas);
    }

    public Carro buscarCarroPorId(Integer idCarro) {
        return repository.buscarCarroPorId(idCarro)
                .orElseThrow(() -> new ValorNaoExistenteNaBaseDeDadosException(idCarro.toString()));
    }

    private void checarSeExisteCarro(Integer idCarro) {
        boolean bool = repository.checarExistenciaCarroPorId(idCarro);

        if (!bool) {
            throw new ValorNaoExistenteNaBaseDeDadosException(idCarro.toString());
        }
    }

    private void validarCarro(Carro carro) {
        carro.setPlaca(carro.getPlaca().toUpperCase());
        carro.setCor(carro.getCor().toUpperCase());
    }
}