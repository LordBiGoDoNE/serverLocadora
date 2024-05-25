package br.com.rafaelsoftworks.aula.service;

import br.com.rafaelsoftworks.aula.exception.ValorNaoExistenteNaBaseDeDadosException;
import br.com.rafaelsoftworks.aula.filter.CarroFilter;
import br.com.rafaelsoftworks.aula.model.dto.CarroDTO;
import br.com.rafaelsoftworks.aula.model.entity.Carro;
import br.com.rafaelsoftworks.aula.repository.cadastro.CarroRepositoryJdbcClient;
import org.hamcrest.Matchers;
import org.instancio.Instancio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CarroServiceTest {

    @InjectMocks
    CarroService carroService;

    @Mock
    CarroRepositoryJdbcClient carroRepositoryJdbcClient;

    @Test
    void obterTodosCarros() {
        Mockito.when(carroRepositoryJdbcClient.buscarTodosCarros(ArgumentMatchers.any(CarroFilter.class))).thenReturn(List.of());

        Assertions.assertDoesNotThrow(() -> carroService.obterTodosCarros(new CarroFilter()));
    }

    @Test
    void criarCarro() {
        Mockito.when(carroRepositoryJdbcClient.criarCarro(ArgumentMatchers.any(Carro.class))).thenReturn(1);
        Mockito.when(carroRepositoryJdbcClient.buscarCarroPorId(ArgumentMatchers.any(Integer.class))).thenReturn(Optional.of(Instancio.create(CarroDTO.class)));

        Assertions.assertDoesNotThrow(() -> carroService.criarCarro(Instancio.create(Carro.class)));
    }

    @Test
    void atualizarCarro() {
        Mockito.when(carroRepositoryJdbcClient.atualizarCarro(ArgumentMatchers.any(Carro.class))).thenReturn(1);
        Mockito.when(carroRepositoryJdbcClient.buscarCarroPorId(ArgumentMatchers.any(Integer.class))).thenReturn(Optional.of(Instancio.create(CarroDTO.class)));
        Mockito.when(carroRepositoryJdbcClient.checarExistenciaCarroPorId(ArgumentMatchers.any(Integer.class))).thenReturn(true);

        Assertions.assertDoesNotThrow(() -> carroService.atualizarCarro(Instancio.create(Carro.class)));
    }

    @Test
    void deletarCarro() {
        Mockito.when(carroRepositoryJdbcClient.deletarCarro(ArgumentMatchers.any(Integer.class))).thenReturn(1);
        Mockito.when(carroRepositoryJdbcClient.checarExistenciaCarroPorId(ArgumentMatchers.any(Integer.class))).thenReturn(true);

        Assertions.assertDoesNotThrow(() -> carroService.deletarCarro(1));
    }

    @Test
    void exceptionChecarSeExisteCarro() {
        Assertions.assertThrows(ValorNaoExistenteNaBaseDeDadosException.class, () -> carroService.atualizarCarro(Instancio.create(Carro.class)));
    }
}