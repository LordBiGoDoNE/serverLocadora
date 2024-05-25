package br.com.rafaelsoftworks.aula.repository.cadastro;

import br.com.rafaelsoftworks.aula.model.entity.Fabricante;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FabricanteRepository extends ListCrudRepository<Fabricante, Integer> {
}
