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
public class CarroRepositoryJdbcClientComAbstracao extends AbstractRepository<Carro> {

    @Autowired
    public CarroRepositoryJdbcClientComAbstracao(JdbcClient jdbcClient) {
        super(jdbcClient);
    }

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

    @Override
    public List<Carro> getAll(Object filtro) {
        CarroFilter carroFilter = (CarroFilter) filtro;
        
        StringJoiner where = new StringJoiner(" AND ");

        Map<String, Object> params = new HashMap<>();

        if (carroFilter.getIdModelo() != null) {
            where.add("id_modelo = :idModelo");
            params.put("idModelo", carroFilter.getIdModelo());
        }

        if (carroFilter.getPlaca() != null) {
            where.add("placa = :placa");
            params.put("placa", carroFilter.getPlaca());
        }

        if (carroFilter.getCor() != null) {
            where.add("cor = :cor");
            params.put("cor", carroFilter.getCor());
        }

        if (carroFilter.getAno() != null) {
            where.add("ano = :ano");
            params.put("ano", carroFilter.getAno());
        }

        if (carroFilter.getDisponivel() != null) {
            where.add("disponibilidade = :disponibilidade");
            params.put("disponibilidade", carroFilter.getDisponivel());
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

    @Override
    public Optional<Carro> getByID(Integer id) {
        return jdbcClient
                .sql("SELECT * FROM carro WHERE id = :idCarro")
                .param("idCarro", id)
                .query(carroMapper)
                .optional();
    }

    @Override
    public boolean existsByID(Integer id) {
        return jdbcClient
                .sql("SELECT EXISTS(SELECT 1 FROM carro WHERE id = :idCarro)")
                .param("idCarro", id)
                .query(Boolean.class)
                .single();
    }

    @Override
    public Integer create(Carro carro) {
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

    @Override
    public Integer update(Carro carro) {
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

    @Override
    public Integer delete(Integer id) {
        String querySql = "DELETE FROM public.carro WHERE id=:idCarro;";

        return jdbcClient.sql(querySql)
                .param("idCarro", id)
                .update();
    }
}