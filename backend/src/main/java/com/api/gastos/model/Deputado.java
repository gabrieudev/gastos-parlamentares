package com.api.gastos.model;

import org.modelmapper.ModelMapper;

import com.api.gastos.rest.dto.DeputadoDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "Deputados")
public class Deputado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String uf;

    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(nullable = false)
    private String partido;

    public DeputadoDTO toDto() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, DeputadoDTO.class);
    }
}
