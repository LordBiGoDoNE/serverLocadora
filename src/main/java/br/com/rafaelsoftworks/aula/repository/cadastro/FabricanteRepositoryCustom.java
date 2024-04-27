package br.com.rafaelsoftworks.aula.repository.cadastro;

import br.com.rafaelsoftworks.aula.model.entity.Fabricante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FabricanteRepositoryCustom {

    @Autowired
    JdbcClient jdbcClient;

    public List<Fabricante> buscarTodosFabricantes() {
        List<Fabricante> fabricantes = jdbcClient
                .sql("SELECT * FROM fabricante")
                .query(Fabricante.class)
                .list();

        RowMapper<Fabricante> mapperFabricante = (rs, rowNum) -> {
            Fabricante fabricante = new Fabricante();
            fabricante.setId(rs.getInt("id"));
            fabricante.setNome(rs.getString("nome"));

            return fabricante;
        };

        List<Fabricante> fabricantes2 = jdbcClient
                .sql("SELECT * FROM fabricante")
                .query(mapperFabricante)
                .list();

        return fabricantes;
    }
}
