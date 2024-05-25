package br.com.rafaelsoftworks.aula.filter;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.util.Map;

@Data
@NoArgsConstructor
@FieldNameConstants
public class CarroFilter {

    private Integer idModelo;

    private String placa;

    private String cor;

    private Boolean disponivel;

    private Integer ano;

    public CarroFilter(Map<String, String> parametros) {
        parametros.forEach((chave, valor) -> {
            switch (chave) {
                case CarroFilter.Fields.idModelo:
                    idModelo = Integer.valueOf(valor);
                    break;
                case CarroFilter.Fields.cor:
                    cor = valor;
                    break;
                case CarroFilter.Fields.placa:
                    placa = valor;
                    break;
                case CarroFilter.Fields.disponivel:
                    disponivel = Boolean.valueOf(valor);
                    break;
                case CarroFilter.Fields.ano:
                    ano = Integer.valueOf(valor);
                    break;
            }
        });
    }
}
