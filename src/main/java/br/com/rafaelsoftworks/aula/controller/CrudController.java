package br.com.rafaelsoftworks.aula.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Crud ferente a entidade especificada no parametro de classe.
 *
 * @param <ENTITY> Tipo de entidade.
 */
public interface CrudController<ENTITY> {

    /**
     * Consulta a com base na id.
     *
     * @param id Identificador do DTO ou Entidade a ser consultada.
     * @return DTO ou Entidade especificada na regra.
     */
    @GetMapping("/{id}")
    ResponseEntity<?> get(@PathVariable(value = "id") Integer id);

    /**
     * Consulta Lista de DTO ou Entidade especificada pela regra.
     *
     * @param parametros Parametros passados na requisição.
     * @return Lista contendo DTOs ou Entidades.
     */
    @GetMapping
    ResponseEntity<List<?>> getAll(@RequestParam Map<String,String> parametros);

    /**
     * Endpoint de inserção da entidade especificada.
     * @param entity Entidade deserealizada. (Deve ser especificada na requisição via Json na requisição, o Jackson cuida da deserialização)
     * @return Entidade serealizada como resposta sem a necessidade de consulta.
     */
    @PostMapping
    ResponseEntity<?> insert(@RequestBody ENTITY entity);

    /**
     * Endpoint de atualização da entidade especificada.
     * @param entity Entidade deserealizada. (Deve ser especificada na requisição via Json na requisição, o Jackson cuida da deserialização)
     * @return Entidade serealizada como resposta sem a necessidade de consulta.
     */
    @PatchMapping
    ResponseEntity<?> update(@RequestBody ENTITY entity);

    /**
     * Endpoint de exclusão da entidade especificada.
     * @param id Identificador da entidade a ser deletada.
     * @return Mensagem de exclusão com sucesso.
     */
    @DeleteMapping("/{id}")
    ResponseEntity<String> delete(@PathVariable(value = "id") Integer id);
}
