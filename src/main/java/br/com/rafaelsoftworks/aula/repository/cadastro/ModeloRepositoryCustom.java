package br.com.rafaelsoftworks.aula.repository.cadastro;

import br.com.rafaelsoftworks.aula.model.entity.Modelo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ModeloRepositoryCustom {

    @Autowired
    JdbcClient jdbcClient;

    RowMapper<Modelo> modeloMapper = (rs, rowNum) -> {
        Modelo modelo = new Modelo();
        modelo.setId(rs.getInt("id"));
        modelo.setDescricao(rs.getString("descricao"));
        modelo.setIdFabricante(rs.getInt("id_fabricante"));

        return modelo;
    };

    public List<Modelo> buscarTodosModelos(Integer idFabricante) {
        if (idFabricante <= 0) {
            return jdbcClient
                    .sql("SELECT * FROM modelo")
                    .query(modeloMapper)
                    .list();
        } else {
            return jdbcClient
                    .sql("SELECT * FROM modelo WHERE id_fabricante = :idFabricante")
                    .param("idFabricante", idFabricante)
                    .query(modeloMapper)
                    .list();
        }
    }

    public Optional<Modelo> buscarModeloPorId(Integer idModelo) {
            return jdbcClient
                    .sql("SELECT * FROM modelo WHERE id = :idModelo")
                    .param("idModelo", idModelo)
                    .query(modeloMapper)
                    .optional();
    }

    public boolean checarExistenciaModeloPorId(Integer idModelo) {
            return jdbcClient
                    .sql("SELECT EXISTS(SELECT 1 FROM modelo WHERE id = :idModelo)")
                    .param("idModelo", idModelo)
                    .query(Boolean.class)
                    .single();
    }

    public Integer criarModelo(Modelo modelo) {
        String querySql = "INSERT INTO public.modelo (descricao,id_fabricante) VALUES (:descricao, :idFabricante);";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcClient.sql(querySql)
                .param("descricao", modelo.getDescricao())
                .param("idFabricante", modelo.getIdFabricante())
                .update(keyHolder, "id");

        return keyHolder.getKeyAs(Integer.class);
    }

    public Integer atualizarModelo(Modelo modelo) {
        String querySql = "UPDATE public.modelo SET descricao=:descricao,id_fabricante=:idFabricante WHERE id=:idModelo;";

        return jdbcClient.sql(querySql)
                .param("descricao", modelo.getDescricao())
                .param("idFabricante", modelo.getIdFabricante())
                .param("idModelo", modelo.getId())
                .update();
    }

    public Integer deletarModelo(Integer idModelo) {
        String querySql = "DELETE FROM public.modelo WHERE id=:idModelo;";

        return jdbcClient.sql(querySql)
                .param("idModelo", idModelo)
                .update();
    }
}
