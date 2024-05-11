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
                case Fields.idModelo:
                    idModelo = Integer.valueOf(valor);
                    break;
                case Fields.cor:
                    cor = valor;
                    break;
                case Fields.placa:
                    placa = valor;
                    break;
                case Fields.disponivel:
                    disponivel = Boolean.valueOf(valor);
                    break;
                case Fields.ano:
                    ano = Integer.valueOf(valor);
                    break;
            }
        });
    }

    public boolean isVazio() {
        return this.hashCode() == new CarroFilter().hashCode();
    }
}
