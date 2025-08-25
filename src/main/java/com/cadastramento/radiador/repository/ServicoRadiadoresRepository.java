package com.cadastramento.radiador.repository;

import com.cadastramento.radiador.DTO.RadiadorDTO;
import com.cadastramento.radiador.model.Servicoradiadores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ServicoRadiadoresRepository extends JpaRepository<Servicoradiadores, Long> {

    @Query("SELECT new com.cadastramento.radiador.DTO.RadiadorDTO(s.modelo, s.tipo, s.servicoExecutado, s.cliente, s.data, s.preco) " +
           "FROM Servicoradiadores s WHERE s.data BETWEEN :dataInicio AND :dataFim ORDER BY s.data")
    List<RadiadorDTO> findServicosAsDTOByDataBetween(@Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);

    @Query("SELECT new com.cadastramento.radiador.DTO.RadiadorDTO(s.modelo, s.tipo, s.servicoExecutado, s.cliente, s.data, s.preco) " +
           "FROM Servicoradiadores s WHERE s.data = :data ORDER BY s.id")
    List<RadiadorDTO> findServicosAsDTOByData(@Param("data") LocalDate data);

    /**
     * Pesquisa por um termo em várias colunas da entidade Servicoradiadores.
     * A pesquisa é case-insensitive (ignora maiúsculas/minúsculas).
     */
    @Query("SELECT s FROM Servicoradiadores s WHERE " +
           "LOWER(s.modelo) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
           "LOWER(s.tipo) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
           "LOWER(s.cliente) LIKE LOWER(CONCAT('%', :termo, '%'))")
    List<Servicoradiadores> searchByTerm(@Param("termo") String termo);

    @Query("SELECT COALESCE(SUM(s.preco), 0) FROM Servicoradiadores s WHERE s.data BETWEEN :dataInicio AND :dataFim")
    BigDecimal sumPrecoByDataBetween(@Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);
}
