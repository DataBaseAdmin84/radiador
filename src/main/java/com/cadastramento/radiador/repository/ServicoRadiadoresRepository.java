package com.cadastramento.radiador.repository;

import com.cadastramento.radiador.DTO.FaturamentoDiarioDTO;
import com.cadastramento.radiador.DTO.RadiadorDTO;
import com.cadastramento.radiador.model.Servicoradiadores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
     * Pesquisa por um termo e/ou data. A pesquisa por termo é case-insensitive.
     * Se o termo for nulo ou vazio, ele é ignorado.
     * Se a data for nula, ela é ignorada.
     */
    @Query("SELECT s FROM Servicoradiadores s WHERE " +
           "(:termo IS NULL OR :termo = '' OR LOWER(s.modelo) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
           "LOWER(s.tipo) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
           "LOWER(s.cliente) LIKE LOWER(CONCAT('%', :termo, '%'))) " +
           "AND (:data IS NULL OR s.data = :data)")
    Page<Servicoradiadores> searchByTermAndDate(@Param("termo") String termo, @Param("data") LocalDate data, Pageable pageable);

    @Query("SELECT COALESCE(SUM(s.preco), 0) FROM Servicoradiadores s WHERE s.data BETWEEN :dataInicio AND :dataFim")
    BigDecimal sumPrecoByDataBetween(@Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);

    /**
     * Fetches the total revenue grouped by day for a given period.
     * This is the most efficient way to get data for charts.
     */
    @Query("SELECT new com.cadastramento.radiador.DTO.FaturamentoDiarioDTO(s.data, SUM(s.preco)) " +
           "FROM Servicoradiadores s WHERE s.data BETWEEN :dataInicio AND :dataFim " +
           "GROUP BY s.data ORDER BY s.data ASC")
    List<FaturamentoDiarioDTO> findFaturamentoDiarioBetween(@Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);
}
