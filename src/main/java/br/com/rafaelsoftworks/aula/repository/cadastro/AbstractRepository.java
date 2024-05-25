package br.com.rafaelsoftworks.aula.repository.cadastro;

import br.com.rafaelsoftworks.aula.filter.CarroFilter;
import br.com.rafaelsoftworks.aula.model.entity.Carro;
import br.com.rafaelsoftworks.aula.model.entity.Fabricante;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public abstract class AbstractRepository<ENTITY> {

    JdbcClient jdbcClient;

    @Getter
    List<String> filtros;

    public AbstractRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public abstract List<ENTITY> getAll(Object filtro);

    public abstract Optional<ENTITY> getByID(Integer id);

    public abstract boolean existsByID(Integer id);

    public abstract Integer create(ENTITY entity);

    public abstract Integer update(ENTITY entity);

    public abstract Integer delete(Integer id);
}
