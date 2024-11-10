package com.api.gastos.rest.dto;

import java.math.BigDecimal;

import org.modelmapper.ModelMapper;

import com.api.gastos.model.Despesa;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DespesaDTO {
    @Schema(description = "Identificador da despesa", example = "1")
    private Long id;

    @Schema(description = "Deputado", example = "DeputadoDTO")
    private DeputadoDTO deputado;

    @Schema(description = "Data de emissão", example = "2021-01-01")
    private String dataEmissao;

    @Schema(description = "Fornecedor", example = "Fornecedor")
    private String fornecedor;

    @Schema(description = "URL para o documento", example = "http://www.camara.leg.br/documento.pdf")
    private String urlDocumento;

    @Schema(description = "Valor líquido", example = "1000.00")
    private BigDecimal valorLiquido;

    public Despesa toModel() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, Despesa.class);
    }
}
