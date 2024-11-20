package com.api.gastos.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.gastos.exception.EntityNotFoundException;
import com.api.gastos.model.Deputado;
import com.api.gastos.model.Despesa;
import com.api.gastos.repository.DeputadoRepository;
import com.api.gastos.repository.DespesaRepository;
import com.api.gastos.rest.dto.DespesaDTO;
import com.api.gastos.rest.dto.MediaDespesasDTO;
import com.api.gastos.rest.dto.SomaDespesasDTO;

@Service
public class DespesaService {
    
    private final DespesaRepository despesaRepository;
    private final DeputadoRepository deputadoRepository;

    public DespesaService(DespesaRepository despesaRepository, DeputadoRepository deputadoRepository) {
        this.despesaRepository = despesaRepository;
        this.deputadoRepository = deputadoRepository;
    }

    @Cacheable(value = "DespesasCache", key = "#id")
    @Transactional(readOnly = true)
    public DespesaDTO obterPorId(Long id) {
        return despesaRepository.findById(id)
            .map(Despesa::toDto)
            .orElseThrow(() -> new EntityNotFoundException("Despesa não encontrada"));
    }

    @Cacheable(value = "SomaDespesasCache", key = "#idDeputado")
    @Transactional(readOnly = true)
    public SomaDespesasDTO somaValorLiquidoPorDeputado(Long idDeputado) {
        Deputado deputado = deputadoRepository.findById(idDeputado)
            .orElseThrow(() -> new EntityNotFoundException("Deputado não encontrado"));
        BigDecimal valor = despesaRepository.somaValorLiquidoPorDeputado(deputado);
        return new SomaDespesasDTO(valor.setScale(2, RoundingMode.HALF_UP));
    }

    @Cacheable(value = "MediaDespesasCache", key = "#idDeputado")
    @Transactional(readOnly = true)
    public MediaDespesasDTO mediaValorLiquidoPorDeputado(Long idDeputado) {
        Deputado deputado = deputadoRepository.findById(idDeputado)
            .orElseThrow(() -> new EntityNotFoundException("Deputado não encontrado"));
        BigDecimal valor = despesaRepository.mediaValorLiquidoPorDeputado(deputado);
        return new MediaDespesasDTO(valor.setScale(2, RoundingMode.HALF_UP));
    }

    @Cacheable(value = "MaximaDespesaCache", key = "#idDeputado")
    @Transactional(readOnly = true)
    public DespesaDTO maximaDespesaPorDeputado(Long idDeputado) {
        Deputado deputado = deputadoRepository.findById(idDeputado)
            .orElseThrow(() -> new EntityNotFoundException("Deputado não encontrado"));
        return despesaRepository.maximaDespesaPorDeputado(deputado.getId())
            .map(Despesa::toDto)
            .orElse(null);
    }
 
    @Transactional(readOnly = true)
    public Page<DespesaDTO> obterPorDataEmissao(Long idDeputado, LocalDateTime dataInicio, LocalDateTime dataFim, Pageable pageable) {
        Deputado deputado = deputadoRepository.findById(idDeputado)
            .orElseThrow(() -> new EntityNotFoundException("Deputado não encontrado"));
        return despesaRepository.findByDeputadoAndDataEmissaoBetween(deputado, dataInicio, dataFim, pageable)
            .map(Despesa::toDto);
    }

    @Transactional(readOnly = true)
    public Page<DespesaDTO> obterPorDeputado(Long idDeputado, Pageable pageable) {
        Deputado deputado = deputadoRepository.findById(idDeputado)
            .orElseThrow(() -> new EntityNotFoundException("Deputado não encontrado"));
        return despesaRepository.findByDeputado(deputado, pageable)
            .map(Despesa::toDto);
    }

    @Transactional(readOnly = true)
    public Page<DespesaDTO> obterTodas(Pageable pageable) {
        return despesaRepository.findAll(pageable)
            .map(Despesa::toDto);
    }

}
