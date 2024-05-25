package br.com.rafaelsoftworks.aula.filter;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.util.Map;

@Data
@NoArgsConstructor
@FieldNameConstants
public class FabricanteFilter {

    private String nome;

    public FabricanteFilter(Map<String, String> parametros) {
        parametros.forEach((chave, valor) -> {
            switch (chave) {
                case FabricanteFilter.Fields.nome:
                    nome = valor;
                    break;
            }
        });
    }
}
