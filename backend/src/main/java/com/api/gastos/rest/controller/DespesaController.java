package com.api.gastos.rest.controller;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.gastos.rest.dto.DespesaDTO;
import com.api.gastos.rest.dto.MediaDespesasDTO;
import com.api.gastos.rest.dto.SomaDespesasDTO;
import com.api.gastos.service.DespesaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@CrossOrigin
@RequestMapping("/despesas")
public class DespesaController {
    
    private final DespesaService despesaService;

    public DespesaController(DespesaService despesaService) {
        this.despesaService = despesaService;
    }

    @Operation(
        summary = "Obter despesa por ID",
        description = "Este endpoint permite obter uma despesa por ID.",
        tags = "Despesas"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Despesa retornada com sucesso"
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Despesa não encontrada"
        )
    })
    @GetMapping("/{id}")
    public ResponseEntity<DespesaDTO> getById(
        @Parameter(description = "ID da despesa para pesquisa")
        @PathVariable("id") Long idDespesa
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(despesaService.obterPorId(idDespesa));
    }

    @Operation(
        summary = "Obter despesas por data",
        description = "Este endpoint permite obter as despesas por data com paginação.",
        tags = "Despesas"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Lista de despesas retornada com sucesso"
        )
    })
    @GetMapping("/data")
    public ResponseEntity<Page<DespesaDTO>> getByDataEmissaoBetween(
        @Parameter(description = "ID do deputado para pesquisa")
        @RequestParam(required = true) Long idDeputado,

        @Parameter(description = "Data de início para pesquisa", example = "2024-01-01T00:00:00")
        @RequestParam(required = true) LocalDateTime dataInicio,

        @Parameter(description = "Data de fim para pesquisa", example = "2024-10-29T23:59:59")
        @RequestParam(required = true) LocalDateTime dataFim,

        @Parameter(description = "Parâmetros de paginação e ordenação")
        Pageable pageable
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(despesaService.obterPorDataEmissao(idDeputado, dataInicio, dataFim, pageable));
    }

    @Operation(
        summary = "Obter despesas por deputado",
        description = "Este endpoint permite obter as despesas de um deputado com paginação.",
        tags = "Despesas"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Lista de despesas retornada com sucesso"
        )
    })
    @GetMapping("/deputado/{idDeputado}")
    public ResponseEntity<Page<DespesaDTO>> getbyDeputado(
        @Parameter(description = "ID do deputado para pesquisa")
        @RequestParam(required = true) Long idDeputado,

        @Parameter(description = "Parâmetros de paginação e ordenação")
        Pageable pageable
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(despesaService.obterPorDeputado(idDeputado, pageable));
    }

    @Operation(
        summary = "Obter soma de despesas",
        description = "Este endpoint permite obter a soma das despesas de um deputado.",
        tags = "Despesas"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Soma das despesas retornada com sucesso"
        )
    })
    @GetMapping("/soma")
    public ResponseEntity<SomaDespesasDTO> getSumDespesasByDeputado(
        @Parameter(description = "ID do deputado para pesquisa")
        @RequestParam(required = true) Long idDeputado
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(despesaService.somaValorLiquidoPorDeputado(idDeputado));
    }

    @Operation(
        summary = "Obter média de despesas",
        description = "Este endpoint permite obter a média das despesas de um deputado.",
        tags = "Despesas"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Média das despesas retornada com sucesso"
        )
    })
    @GetMapping("/media")
    public ResponseEntity<MediaDespesasDTO> getAvgDespesasByDeputado(
        @Parameter(description = "ID do deputado para pesquisa")
        @RequestParam(required = true) Long idDeputado
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(despesaService.mediaValorLiquidoPorDeputado(idDeputado));
    }

    @Operation(
        summary = "Obter maior despesa",
        description = "Este endpoint permite obter a maior despesa de um deputado.",
        tags = "Despesas"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Lista de despesas retornada com sucesso"
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Deputado não encontrado"
        )
    })
    @GetMapping("/maior")
    public ResponseEntity<DespesaDTO> getMaxDespesaByDeputado(
        @Parameter(description = "ID do deputado para pesquisa")
        @RequestParam(required = true) Long idDeputado
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(despesaService.maximaDespesaPorDeputado(idDeputado));
    }

    @Operation(
        summary = "Listar despesas",
        description = "Este endpoint retorna uma lista paginada de despesas.",
        tags = "Despesas"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Lista de despesas retornada com sucesso"
        )
    })
    @GetMapping
    public ResponseEntity<Page<DespesaDTO>> getAll(
        @Parameter(description = "Parâmetros de paginação e ordenação")
        Pageable pageable
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(despesaService.obterTodas(pageable));
    }

}
