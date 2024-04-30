package br.com.rafaelsoftworks.aula.service;

import br.com.rafaelsoftworks.aula.model.entity.Fabricante;
import br.com.rafaelsoftworks.aula.repository.cadastro.FabricanteRepository;
import br.com.rafaelsoftworks.aula.repository.cadastro.FabricanteRepositoryCustom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.sql.DataSource;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class FabricanteServiceTest {

    @InjectMocks
    FabricanteService service;

    @Mock
    FabricanteRepository fabricanteRepository;

    @Mock
    FabricanteRepositoryCustom fabricanteRepositoryCustom;

    @Test
    void atualizarFabricanteTest() {
        //Mock
//        Mockito.when(fabricanteRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(new Fabricante(1, "Vieira")));

        //Spy
        Mockito.doReturn(Optional.of(new Fabricante(1, "Vieira"))).when(fabricanteRepository).findById(ArgumentMatchers.anyInt());

        Assertions.assertDoesNotThrow(() -> service.atualizarFabricante(new Fabricante(1, "Rafael")));
    }

    @Test
    void atualizarFabricanteExceptionTest() {
        Assertions.assertThrows(RuntimeException.class, () -> service.atualizarFabricante(new Fabricante(1, "Vieira")));
    }
}