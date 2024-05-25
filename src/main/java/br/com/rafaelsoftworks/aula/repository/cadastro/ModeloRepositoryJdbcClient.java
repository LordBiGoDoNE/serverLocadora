package br.com.rafaelsoftworks.aula.repository.cadastro;

import br.com.rafaelsoftworks.aula.filter.ModeloFilter;
import br.com.rafaelsoftworks.aula.model.dto.ModeloDTO;
import br.com.rafaelsoftworks.aula.model.entity.Fabricante;
import br.com.rafaelsoftworks.aula.model.entity.Modelo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ModeloRepositoryJdbcClient {

    @Autowired
    JdbcClient jdbcClient;

    RowMapper<ModeloDTO> modeloDTORowMapper = (rs, rowNum) -> {
        ModeloDTO modelo = new ModeloDTO();
        modelo.setId(rs.getInt("id"));
        modelo.setDescricao(rs.getString("descricao"));

        Fabricante fabricante = new Fabricante();
        fabricante.setId(rs.getInt("idFabricante"));
        fabricante.setNome(rs.getString("nome"));

        modelo.setFabricante(fabricante);

        return modelo;
    };

    public List<ModeloDTO> buscarTodosModelosDTO(ModeloFilter modeloFilter) {
        StringJoiner where = new StringJoiner(" AND ");

        Map<String, Object> params = new HashMap<>();

        if (modeloFilter.getDescricao() != null) {
            where.add("id_modelo = :idModelo");
            params.put("idModelo", modeloFilter.getDescricao());
        }

        if (modeloFilter.getIdFabricante() != null) {
            where.add("id_fabricante = :idFabricante");
            params.put("idFabricante", modeloFilter.getIdFabricante());
        }

        String sql = "SELECT m.*, f.id as idFabricante, f.nome FROM modelo m INNER JOIN fabricante f ON m.id_fabricante = f.id";

        if (!params.isEmpty()) {
            String sqlWhere = sql + " WHERE " + where;

            return jdbcClient
                    .sql(sqlWhere)
                    .query(modeloDTORowMapper)
                    .list();
        } else {
            return jdbcClient
                    .sql(sql)
                    .query(modeloDTORowMapper)
                    .list();
        }
    }

    public Optional<ModeloDTO> buscarModeloPorId(Integer idModelo) {
            return jdbcClient
                    .sql("SELECT m.*, f.id as idFabricante, f.nome FROM modelo m INNER JOIN fabricante f ON m.id_fabricante = f.id WHERE m.id = :idModelo")
                    .param("idModelo", idModelo)
                    .query(modeloDTORowMapper)
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
