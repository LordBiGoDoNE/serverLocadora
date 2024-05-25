package br.com.rafaelsoftworks.aula.filter;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.util.Map;

@Data
@NoArgsConstructor
@FieldNameConstants
public class ModeloFilter {

    private String descricao;

    private Integer idFabricante;

    public ModeloFilter(Map<String, String> parametros) {
        parametros.forEach((chave, valor) -> {
            switch (chave) {
                case ModeloFilter.Fields.descricao:
                    descricao = valor;
                    break;
                case ModeloFilter.Fields.idFabricante:
                    idFabricante = Integer.valueOf(valor);
                    break;
            }
        });
    }
}
