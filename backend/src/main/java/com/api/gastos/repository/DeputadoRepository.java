package com.api.gastos.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.gastos.model.Deputado;

@Repository
public interface DeputadoRepository extends JpaRepository<Deputado, Long>{
    Optional<Deputado> findByCpf(String cpf);

    @Query(
        value = """
                SELECT d
                FROM Deputado d
                WHERE
                    (:nome IS NULL OR d.nome LIKE CONCAT('%', :nome, '%'))
                    AND (:uf IS NULL OR d.uf = :uf)
                    AND (:partido IS NULL OR d.partido = :partido)
                """
    )
    Page<Deputado> pesquisar(
        @Param("nome") String nome,
        @Param("uf") String uf,
        @Param("partido") String partido,
        Pageable pageable
    );

    @Modifying
    @Query(
        value = """
                TRUNCATE TABLE deputados RESTART IDENTITY CASCADE;
                """,
        nativeQuery = true
    )
    void resetarTabela();
}
