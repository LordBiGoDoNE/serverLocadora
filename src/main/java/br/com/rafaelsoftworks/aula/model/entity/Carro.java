package br.com.rafaelsoftworks.aula.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Carro {

    @Id
    private int id;

    private int idModelo;

    private String placa;

    private String cor;

    private boolean disponivel;

    private int ano;
}
