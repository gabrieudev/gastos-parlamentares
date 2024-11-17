package com.api.gastos.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.gastos.exception.EntityNotFoundException;
import com.api.gastos.model.Deputado;
import com.api.gastos.repository.DeputadoRepository;
import com.api.gastos.rest.dto.DeputadoDTO;

@Service
public class DeputadoService {
    
    private final DeputadoRepository deputadoRepository;

    public DeputadoService(DeputadoRepository deputadoRepository) {
        this.deputadoRepository = deputadoRepository;
    }

    @Transactional(readOnly = true)
    public DeputadoDTO obterPorId(Long id) {
        return deputadoRepository.findById(id)
            .map(Deputado::toDto)
            .orElseThrow(() -> new EntityNotFoundException("Deputado não encontrado"));
    } 

    @Transactional(readOnly = true)
    public Page<DeputadoDTO> pesquisar(String nome, String uf, String partido, Pageable pageable) {
        return deputadoRepository.pesquisar(nome, uf, partido, pageable)
            .map(Deputado::toDto);
    }

    @Transactional(readOnly = true)
    public Page<DeputadoDTO> obterTodos(Pageable pageable) {
        return deputadoRepository.findAll(pageable)
            .map(Deputado::toDto);
    }

}
