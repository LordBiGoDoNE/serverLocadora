package br.com.rafaelsoftworks.aula.repository.cadastro;

import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

class FabricanteRepositoryCustomTest {

    @Autowired
    FabricanteRepositoryJdbcClient fabricanteRepositoryJdbcClient;

    void buscarTodosFabricantes() {
        Assertions.assertDoesNotThrow(() -> fabricanteRepositoryJdbcClient.buscarTodosFabricantes());
    }
}