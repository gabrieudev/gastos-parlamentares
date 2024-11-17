package com.api.gastos.representation;

import java.math.BigDecimal;

import com.opencsv.bean.CsvBindByName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeputadoDespesaCsvRepresentation {
    @CsvBindByName(column = "txNomeParlamentar")
    private String nome;

    @CsvBindByName(column = "sgUF")
    private String uf;

    @CsvBindByName(column = "cpf")
    private String cpf;

    @CsvBindByName(column = "sgPartido")
    private String partido;

    @CsvBindByName(column = "datEmissao")
    private String dataEmissao;

    @CsvBindByName(column = "txtFornecedor")
    private String fornecedor;

    @CsvBindByName(column = "urlDocumento")
    private String urlDocumento;

    @CsvBindByName(column = "vlrLiquido")
    private BigDecimal valorLiquido;
}
