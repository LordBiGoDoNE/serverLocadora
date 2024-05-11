package br.com.rafaelsoftworks.aula.model.dto;

import br.com.rafaelsoftworks.aula.model.entity.Fabricante;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModeloDTO {
    private int id;

    private String descricao;

    private Fabricante fabricante;
}
