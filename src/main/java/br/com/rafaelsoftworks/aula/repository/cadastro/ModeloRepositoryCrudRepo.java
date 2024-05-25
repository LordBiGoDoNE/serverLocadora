package br.com.rafaelsoftworks.aula.repository.cadastro;

import br.com.rafaelsoftworks.aula.model.entity.Fabricante;
import br.com.rafaelsoftworks.aula.model.entity.Modelo;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModeloRepositoryCrudRepo extends ListCrudRepository<Modelo, Integer> {
}
