package br.com.rafaelsoftworks.aula.repository.cadastro;

import br.com.rafaelsoftworks.aula.filter.CarroFilter;
import br.com.rafaelsoftworks.aula.model.dto.CarroDTO;
import br.com.rafaelsoftworks.aula.model.dto.ModeloDTO;
import br.com.rafaelsoftworks.aula.model.entity.Carro;
import br.com.rafaelsoftworks.aula.model.entity.Fabricante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class CarroRepositoryJdbcClient {

    @Autowired
    JdbcClient jdbcClient;

    RowMapper<CarroDTO> carroDTOMapper = (rs, rowNum) -> {
        CarroDTO carroDTO = new CarroDTO();
        carroDTO.setId(rs.getInt("id"));
        carroDTO.setPlaca(rs.getString("placa"));
        carroDTO.setCor(rs.getString("cor"));
        carroDTO.setDisponivel(rs.getBoolean("disponivel"));
        carroDTO.setAno(rs.getInt("ano"));

        Fabricante fabricante = new Fabricante();
        fabricante.setId(rs.getInt("idFabricante"));
        fabricante.setNome(rs.getString("nome"));

        ModeloDTO modeloDTO = new ModeloDTO();
        modeloDTO.setId(rs.getInt("idModelo"));
        modeloDTO.setDescricao(rs.getString("descricao"));
        modeloDTO.setFabricante(fabricante);

        carroDTO.setModelo(modeloDTO);

        return carroDTO;
    };

    public List<CarroDTO> buscarTodosCarros(CarroFilter filtro) {
        StringJoiner where = new StringJoiner(" AND ");

        Map<String, Object> params = new HashMap<>();

        if (filtro.getIdModelo() != null) {
            where.add("id_modelo = :idModelo");
            params.put("idModelo", filtro.getIdModelo());
        }

        if (filtro.getPlaca() != null) {
            where.add("placa = :placa");
            params.put("placa", filtro.getPlaca());
        }

        if (filtro.getCor() != null) {
            where.add("cor = :cor");
            params.put("cor", filtro.getCor());
        }

        if (filtro.getAno() != null) {
            where.add("ano = :ano");
            params.put("ano", filtro.getAno());
        }

        if (filtro.getDisponivel() != null) {
            where.add("disponivel = :disponivel");
            params.put("disponivel", filtro.getDisponivel());
        }

        if (!params.isEmpty()) {
            String sql = """
                            SELECT c.*, m.id as idModelo, m.descricao, f.id as idFabricante, f.nome
                            FROM carro c
                            INNER JOIN modelo m ON
                                c.id_modelo = m.id
                            INNER JOIN fabricante f ON
                                m.id_fabricante = f.id
                            WHERE
                         """ + where;

            return jdbcClient.sql(sql)
                    .params(params)
                    .query(carroDTOMapper).list();
        } else {
            return jdbcClient.sql("""
                            SELECT c.*, m.id as idModelo, m.descricao, f.id as idFabricante, f.nome
                            FROM carro c
                            INNER JOIN modelo m ON
                                c.id_modelo = m.id
                            INNER JOIN fabricante f ON
                                m.id_fabricante = f.id
                         """)
                    .query(carroDTOMapper)
                    .list();
        }
    }

    public Optional<CarroDTO> buscarCarroPorId(Integer idCarro) {
        return jdbcClient
                .sql("""
                        SELECT c.*, m.id as idModelo, m.descricao, f.id as idFabricante, f.nome
                        FROM carro c
                        INNER JOIN modelo m ON
                            c.id_modelo = m.id
                        INNER JOIN fabricante f ON
                            m.id_fabricante = f.id
                        WHERE c.id = :idCarro
                """)
                .param("idCarro", idCarro)
                .query(carroDTOMapper)
                .optional();
    }

    public boolean checarExistenciaCarroPorId(Integer idCarro) {
        return jdbcClient
                .sql("SELECT EXISTS(SELECT 1 FROM carro WHERE id = :idCarro)")
                .param("idCarro", idCarro)
                .query(Boolean.class)
                .single();
    }

    public Integer criarCarro(Carro carro) {
        String querySql = "INSERT INTO public.carro (id_modelo,placa,cor,disponivel,ano) " +
                " VALUES (:idModelo,:placa,:cor,:disponivel,:ano);";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcClient.sql(querySql)
                .param("idModelo", carro.getIdModelo())
                .param("placa", carro.getPlaca())
                .param("cor", carro.getCor())
                .param("disponivel", carro.isDisponivel())
                .param("ano", carro.getAno())
                .update(keyHolder, "id");

        return keyHolder.getKeyAs(Integer.class);
    }

    public Integer atualizarCarro(Carro carro) {
        String querySql = """
                UPDATE public.carro
                	SET cor=:cor,id_modelo=:idModelo,ano=:ano,placa=:placa,disponivel=:disp
                	WHERE id=:idCarro;
                """;

        return jdbcClient.sql(querySql)
                .param("idCarro", carro.getId())
                .param("idModelo", carro.getIdModelo())
                .param("placa", carro.getPlaca())
                .param("cor", carro.getCor())
                .param("disp", carro.isDisponivel())
                .param("ano", carro.getAno())
                .update();
    }

    public Integer deletarCarro(Integer idCarro) {
        String querySql = "DELETE FROM public.carro WHERE id=:idCarro;";

        return jdbcClient.sql(querySql)
                .param("idCarro", idCarro)
                .update();
    }
}