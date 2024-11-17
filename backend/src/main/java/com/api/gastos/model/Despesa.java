package com.api.gastos.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;

import com.api.gastos.rest.dto.DespesaDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@EqualsAndHashCode(of = "id")
@Table(name = "Despesas")
public class Despesa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idDeputado")
    private Deputado deputado;

    private LocalDateTime dataEmissao;

    @Column(nullable = false)
    private String fornecedor;

    @Column(nullable = false)
    private String urlDocumento;

    @Column(nullable = false)
    private BigDecimal valorLiquido;

    public DespesaDTO toDto() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, DespesaDTO.class);
    }
}
