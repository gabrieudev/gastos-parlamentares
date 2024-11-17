package com.api.gastos.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.api.gastos.service.CsvService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@CrossOrigin
@RequestMapping("/csv")
public class CsvController {

    private final CsvService csvService;

    public CsvController(CsvService csvService) {
        this.csvService = csvService;
    }

    @Operation(
        summary = "Upload de arquivo CSV para processamento",
        description = "Este endpoint recebe um arquivo CSV e processa os dados. Um parâmetro opcional `uf` pode ser passado para filtrar os dados de acordo com a unidade federativa.",
        tags = "CSV"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Arquivo CSV processado com sucesso"
        ),
        @ApiResponse(
            responseCode = "500", 
            description = "Erro ao processar arquivo CSV"
        )
    })
    @PostMapping(consumes = { "multipart/form-data" })
    public ResponseEntity<Void> upload(
        @RequestPart(
            name = "arquivo", 
            required = true
        ) 
        @Parameter(
            description = "O arquivo CSV contendo os dados a serem processados.", 
            required = true
        ) 
        MultipartFile file,
        
        @RequestParam(
            name = "uf", 
            required = false
        )
        @Parameter(
            description = "O parâmetro opcional que permite filtrar os dados de acordo com a unidade federativa.", 
            required = false
        ) 
        String uf
    ) throws Exception {
        csvService.processarCsv(file, uf);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(
        summary = "Exclui dados processados",
        description = "Este endpoint exclui todos os dados processados anteriormente.",
        tags = "CSV"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Dados processados excluídos com sucesso"
        ),
    })
    @DeleteMapping
    public ResponseEntity<Void> clean() {
        csvService.limpar();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
