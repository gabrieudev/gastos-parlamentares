package com.api.gastos.rest.dto;

import org.modelmapper.ModelMapper;

import com.api.gastos.model.Deputado;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeputadoDTO {
    @Schema(description = "Identificador do deputado", example = "1")
    private Long id;

    @Schema(description = "Nome do deputado", example = "João da Silva")
    private String nome;

    @Schema(description = "Estado do deputado", example = "SP")
    private String uf;

    @Schema(description = "CPF/CNPJ do deputado", example = "12345678901")
    private String cpf;

    @Schema(description = "Partido do deputado", example = "PT")
    private String partido;

    public Deputado toModel() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, Deputado.class);
    }
}
