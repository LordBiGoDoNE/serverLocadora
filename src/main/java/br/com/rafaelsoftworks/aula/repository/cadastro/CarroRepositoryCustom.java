package br.com.rafaelsoftworks.aula.repository.cadastro;

import br.com.rafaelsoftworks.aula.filter.CarroFilter;
import br.com.rafaelsoftworks.aula.model.entity.Carro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class CarroRepositoryCustom {

    @Autowired
    JdbcClient jdbcClient;

    RowMapper<Carro> carroMapper = (rs, rowNum) -> {
        Carro carro = new Carro();
        carro.setId(rs.getInt("id"));
        carro.setIdModelo(rs.getInt("id_modelo"));
        carro.setPlaca(rs.getString("placa"));
        carro.setCor(rs.getString("cor"));
        carro.setDisponivel(rs.getBoolean("disponibilidade"));
        carro.setAno(rs.getInt("ano"));

        return carro;
    };

    public List<Carro> buscarTodosCarros(CarroFilter filtro) {
            StringJoiner where = new StringJoiner(" AND ");

//        if (filtro.isVazio()) {
//            return jdbcClient
//                    .sql("SELECT * FROM carro")
//                    .query(carroMapper)
//                    .list();
//        } else {
//
//            if (filtro.getIdModelo() != null) {
//                where.add((String.format(" id_modelo = %d", filtro.getIdModelo())));
//            }
//
//            if (filtro.getPlaca() != null) {
//                where.add((String.format(" placa = %s", filtro.getPlaca())));
//            }
//
//            if (filtro.getCor() != null) {
//                where.add((String.format(" cor = %s", filtro.getCor())));
//            }
//
//            if (filtro.getAno() != null) {
//                where.add((String.format(" ano = %d", filtro.getAno())));
//            }
//
//            if (filtro.getDisponivel() != null) {
//                where.add((String.format(" disponibilidade = %b", filtro.getDisponivel())));
//            }
//
//            String sql = "SELECT * FROM carro WHERE " + where.toString();
//
//            return jdbcClient
//                    .sql(sql)
//                    .query(carroMapper)
//                    .list();
//        }

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
            where.add("disponibilidade = :disponibilidade");
            params.put("disponibilidade", filtro.getDisponivel());
        }

        if (!params.isEmpty()) {
            String sql = "SELECT * FROM carro WHERE " + where;

            return jdbcClient.sql(sql)
                    .params(params)
                    .query(carroMapper).list();
        } else {
            return jdbcClient.sql("SELECT * FROM carro")
                    .query(carroMapper)
                    .list();
        }

    }

    public Optional<Carro> buscarCarroPorId(Integer idCarro) {
        return jdbcClient
                .sql("SELECT * FROM carro WHERE id = :idCarro")
                .param("idCarro", idCarro)
                .query(carroMapper)
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
        String querySql = "INSERT INTO public.carro (id_modelo,placa,cor,disponibilidade,ano) " +
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
                	SET cor=:cor,id_modelo=:idModelo,ano=:ano,placa=:placa,disponibilidade=:disp
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