package br.com.rafaelsoftworks.aula.repository.cadastro;

import br.com.rafaelsoftworks.aula.model.entity.Carro;
import br.com.rafaelsoftworks.aula.model.entity.Fabricante;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarroRepositoryCrudRepo extends ListCrudRepository<Carro, Integer> {
}
