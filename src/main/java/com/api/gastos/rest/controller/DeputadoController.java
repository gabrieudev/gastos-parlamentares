package com.api.gastos.rest.controller;

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

import com.api.gastos.rest.dto.DeputadoDTO;
import com.api.gastos.service.DeputadoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@CrossOrigin
@RequestMapping("/deputados")
public class DeputadoController {
    
    private final DeputadoService deputadoService;

    public DeputadoController(DeputadoService deputadoService) {
        this.deputadoService = deputadoService;
    }

    @Operation(
        summary = "Obter deputado por ID",
        description = "Este endpoint permite obter um deputado por ID.",
        tags = "Deputados"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Deputado retornado com sucesso"
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Deputado não encontrado"
        )
    })
    @GetMapping("/{id}")
    public ResponseEntity<DeputadoDTO> getById(
        @Parameter(description = "ID do deputado para pesquisa")
        @PathVariable("id") Long idDeputado
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(deputadoService.obterPorId(idDeputado));
    }

    @Operation(
        summary = "Pesquisar deputados",
        description = "Este endpoint permite pesquisar deputados por nome, UF ou partido com paginação.",
        tags = "Deputados"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de deputados retornada com sucesso")
    })
    @GetMapping("/buscar")
    public ResponseEntity<Page<DeputadoDTO>> search(
        @Parameter(description = "Nome do deputado para pesquisa")
        @RequestParam(required = false) String nome,

        @Parameter(description = "UF do deputado para pesquisa")
        @RequestParam(required = false) String uf,

        @Parameter(description = "Partido do deputado para pesquisa")
        @RequestParam(required = false) String partido,

        @Parameter(description = "Parâmetros de paginação e ordenação")
        Pageable pageable
    ) {
        Page<DeputadoDTO> deputados = deputadoService.pesquisar(nome, uf, partido, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(deputados);
    }

    @Operation(
        summary = "Listar deputados",
        description = "Este endpoint retorna uma lista paginada de deputados.",
        tags = "Deputados"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Lista de deputados retornada com sucesso"
        )
    })
    @GetMapping
    public ResponseEntity<Page<DeputadoDTO>> getAll(
        @Parameter(description = "Parâmetros de paginação e ordenação")
        Pageable pageable
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(deputadoService.obterTodos(pageable));
    }

}
