package com.api.gastos.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.gastos.model.Deputado;
import com.api.gastos.model.Despesa;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long> {
    Page<Despesa> findByDeputado(Deputado deputado, Pageable pageable);
    
    Page<Despesa> findByDeputadoAndDataEmissaoBetween(Deputado deputado, LocalDateTime dataInicio, LocalDateTime dataFim, Pageable pageable);

    @Query(
        "SELECT SUM(d.valorLiquido) FROM Despesa d WHERE d.deputado = :deputado"
    )
    BigDecimal somaValorLiquidoPorDeputado(@Param("deputado") Deputado deputado);

    @Query(
        "SELECT AVG(d.valorLiquido) FROM Despesa d WHERE d.deputado = :deputado"
    )
    BigDecimal mediaValorLiquidoPorDeputado(@Param("deputado") Deputado deputado);

    @Query(
        value = """
                SELECT * 
                FROM despesas d 
                WHERE 
                    d.id_deputado = :deputadoId 
                    AND d.valor_liquido = (
                        SELECT MAX(d2.valor_liquido) 
                        FROM despesas d2 
                        WHERE d2.id_deputado = :deputadoId
                    )
                LIMIT 1
                """,
        nativeQuery = true)
    Optional<Despesa> maximaDespesaPorDeputado(@Param("deputadoId") Long deputadoId);

    @Modifying
    @Query(
        value = """
                TRUNCATE TABLE despesas RESTART IDENTITY CASCADE;
                """,
        nativeQuery = true
    )
    void resetarTabela();

}
