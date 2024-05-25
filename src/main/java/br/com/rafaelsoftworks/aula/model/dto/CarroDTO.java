package br.com.rafaelsoftworks.aula.model.dto;

import br.com.rafaelsoftworks.aula.model.entity.Fabricante;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarroDTO {
    private int id;

    private ModeloDTO modelo;

    private String placa;

    private String cor;

    private boolean disponivel;

    private int ano;
}
