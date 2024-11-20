package com.api.gastos.rest.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SomaDespesasDTO implements Serializable {
    @Schema(description = "Soma das despesas de um deputado", example = "10538.21")
    private BigDecimal soma;
}
