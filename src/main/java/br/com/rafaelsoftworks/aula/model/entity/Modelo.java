package br.com.rafaelsoftworks.aula.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
public class Modelo {

    @Id
    private int id;

    private String nome;

    private Fabricante fabricante;
}
