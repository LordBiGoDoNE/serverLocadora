package br.com.rafaelsoftworks.aula.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Fabricante {

    @Id
    private int id;

    private String nome;
}
