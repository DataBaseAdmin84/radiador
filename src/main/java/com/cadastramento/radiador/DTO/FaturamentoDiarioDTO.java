package com.cadastramento.radiador.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO to carry revenue data grouped by day.
 */
public class FaturamentoDiarioDTO {
    private final LocalDate data;
    private final BigDecimal total;

    public FaturamentoDiarioDTO(LocalDate data, BigDecimal total) {
        this.data = data;
        // Ensures that if the sum is null (no services on a day), the total is 0.
        this.total = (total != null) ? total : BigDecimal.ZERO;
    }

    public LocalDate getData() {
        return data;
    }

    public BigDecimal getTotal() {
        return total;
    }
}