package br.com.rafaelsoftworks.aula.model.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Fabricante {

    @Id
    private int id;

    private String nome;
}
