package br.com.rafaelsoftworks.aula.service;

import br.com.rafaelsoftworks.aula.model.entity.Fabricante;
import br.com.rafaelsoftworks.aula.repository.cadastro.FabricanteRepository;
import br.com.rafaelsoftworks.aula.repository.cadastro.FabricanteRepositoryJdbcClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
class FabricanteServiceTest {

    @InjectMocks
    FabricanteService service;

    @Mock
    FabricanteRepository fabricanteRepository;

    @Mock
    FabricanteRepositoryJdbcClient fabricanteRepositoryJdbcClient;

    @ParameterizedTest
    @MethodSource("parametrosTestAtualizarFabricante")
    void atualizarFabricanteTestParametrizado(Fabricante retornoDoMock, Fabricante chamadaMetodo) {
        //Spy
        Mockito.doReturn(Optional.of(retornoDoMock)).when(fabricanteRepository).findById(ArgumentMatchers.anyInt());

        Assertions.assertDoesNotThrow(() -> service.atualizarFabricante(chamadaMetodo));
    }

    private static Stream<Arguments> parametrosTestAtualizarFabricante() {
        Fabricante retornoMockTest1 = new Fabricante(1, "Jeruso");
        Fabricante retornoMockTest2 = new Fabricante(2, "Rodolfo");
        Fabricante retornoMockTest3 = new Fabricante(2, "Caique");

        Fabricante parametroChamadaMetodo1 = new Fabricante(1, "Rafael");
        Fabricante parametroChamadaMetodo2 = new Fabricante(2, "Vieira");
        Fabricante parametroChamadaMetodo3 = new Fabricante(3, "Silva");

        return Stream.of(
                Arguments.of(retornoMockTest1, parametroChamadaMetodo1),
                Arguments.of(retornoMockTest2, parametroChamadaMetodo2),
                Arguments.of(retornoMockTest3, parametroChamadaMetodo3),
                Arguments.of(retornoMockTest1, parametroChamadaMetodo3),
                Arguments.of(retornoMockTest2, parametroChamadaMetodo1),
                Arguments.of(retornoMockTest2, parametroChamadaMetodo3),
                Arguments.of(retornoMockTest3, parametroChamadaMetodo1),
                Arguments.of(retornoMockTest3, parametroChamadaMetodo2)
        );
    }

    @ParameterizedTest
    @MethodSource("parametrosAtualizarFabricanteException")
    void atualizarFabricanteExceptionTest(Class<? extends Exception> exceptionEsperadaClass, Exception exceptionRetornoMock) {
        Mockito.when(fabricanteRepository.findById(ArgumentMatchers.anyInt())).thenThrow(exceptionRetornoMock);

        Assertions.assertThrows(exceptionEsperadaClass, () -> service.atualizarFabricante(new Fabricante(1, "Vieira")));
    }

    private static Stream<Arguments> parametrosAtualizarFabricanteException() {
        return Stream.of(
                Arguments.of(Exception.class, new RuntimeException()),
                Arguments.of(RuntimeException.class, new RuntimeException()),
                Arguments.of(NullPointerException.class, new NullPointerException())
        );
    }
}