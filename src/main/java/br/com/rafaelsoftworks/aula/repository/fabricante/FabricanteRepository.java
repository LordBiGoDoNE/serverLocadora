package br.com.rafaelsoftworks.aula.repository.fabricante;

import br.com.rafaelsoftworks.aula.model.entity.Fabricante;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FabricanteRepository extends CrudRepository<Fabricante, Integer> {

    @Override
    List<Fabricante> findAll();
}
