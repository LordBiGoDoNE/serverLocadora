package br.com.rafaelsoftworks.aula.repository.cadastro;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class FabricanteRepositoryCustomTest {

    @Autowired
    FabricanteRepositoryCustom fabricanteRepositoryCustom;

    @Test
    void buscarTodosFabricantes() {
        Assertions.assertDoesNotThrow(() -> fabricanteRepositoryCustom.buscarTodosFabricantes());
    }
}