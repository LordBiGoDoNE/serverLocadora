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
public class ModeloRepositoryJdbcClientComAbstracao extends AbstractRepository<Modelo>{

    @Autowired
    JdbcClient jdbcClient;

    RowMapper<Modelo> modeloMapper = (rs, rowNum) -> {
        Modelo modelo = new Modelo();
        modelo.setId(rs.getInt("id"));
        modelo.setDescricao(rs.getString("descricao"));
        modelo.setIdFabricante(rs.getInt("idFabricante"));

        return modelo;
    };

    RowMapper<ModeloDTO> modeloMapperDTO = (rs, rowNum) -> {
        ModeloDTO modelo = new ModeloDTO();
        modelo.setId(rs.getInt("id"));
        modelo.setDescricao(rs.getString("descricao"));

        Fabricante fabricante = new Fabricante();
        fabricante.setId(rs.getInt("idFabricante"));
        fabricante.setNome(rs.getString("nome"));

        modelo.setFabricante(fabricante);

        return modelo;
    };

    public ModeloRepositoryJdbcClientComAbstracao(JdbcClient jdbcClient) {
        super(jdbcClient);
    }

    public List<ModeloDTO> getAllDTO(ModeloFilter modeloFilter) {
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
                    .query(modeloMapperDTO)
                    .list();
        } else {
            return jdbcClient
                    .sql(sql)
                    .query(modeloMapperDTO)
                    .list();
        }
    }

    public Optional<ModeloDTO> getByIdDTO(Integer idModelo) {
            return jdbcClient
                    .sql("SELECT m.*, f.id as idFabricante, f.nome FROM modelo m INNER JOIN fabricante f ON m.id_fabricante = f.id WHERE id = :idModelo")
                    .param("idModelo", idModelo)
                    .query(modeloMapperDTO)
                    .optional();
    }

    @Override
    public List<Modelo> getAll(Object filtro) {
        StringJoiner where = new StringJoiner(" AND ");

        Map<String, Object> params = new HashMap<>();

        ModeloFilter modeloFilter = (ModeloFilter) filtro;

        if (modeloFilter.getDescricao() != null) {
            where.add("id_modelo = :idModelo");
            params.put("idModelo", modeloFilter.getDescricao());
        }

        if (modeloFilter.getIdFabricante() != null) {
            where.add("id_fabricante = :idFabricante");
            params.put("idFabricante", modeloFilter.getIdFabricante());
        }

        String sql = "SELECT m.*, f.id as idFabricante FROM modelo m INNER JOIN fabricante f ON m.id_fabricante = f.id";

        if (!params.isEmpty()) {
            String sqlWhere = sql + " WHERE " + where;

            return jdbcClient
                    .sql(sqlWhere)
                    .query(modeloMapper)
                    .list();
        } else {
            return jdbcClient
                    .sql(sql)
                    .query(modeloMapper)
                    .list();
        }
    }

    @Override
    public Optional<Modelo> getByID(Integer id) {
        return jdbcClient
                .sql("SELECT m.*, f.id as idFabricante FROM modelo m INNER JOIN fabricante f ON m.id_fabricante = f.id WHERE id = :idModelo")
                .param("idModelo", id)
                .query(modeloMapper)
                .optional();
    }

    @Override
    public boolean existsByID(Integer id) {
        return jdbcClient
                .sql("SELECT EXISTS(SELECT 1 FROM modelo WHERE id = :idModelo)")
                .param("idModelo", id)
                .query(Boolean.class)
                .single();
    }

    @Override
    public Integer create(Modelo modelo) {
        String querySql = "INSERT INTO public.modelo (descricao,id_fabricante) VALUES (:descricao, :idFabricante);";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcClient.sql(querySql)
                .param("descricao", modelo.getDescricao())
                .param("idFabricante", modelo.getIdFabricante())
                .update(keyHolder, "id");

        return keyHolder.getKeyAs(Integer.class);
    }

    @Override
    public Integer update(Modelo modelo) {
        String querySql = "UPDATE public.modelo SET descricao=:descricao,id_fabricante=:idFabricante WHERE id=:idModelo;";

        return jdbcClient.sql(querySql)
                .param("descricao", modelo.getDescricao())
                .param("idFabricante", modelo.getIdFabricante())
                .param("idModelo", modelo.getId())
                .update();
    }

    @Override
    public Integer delete(Integer id) {
        String querySql = "DELETE FROM public.modelo WHERE id=:idModelo;";

        return jdbcClient.sql(querySql)
                .param("idModelo", id)
                .update();
    }
}
