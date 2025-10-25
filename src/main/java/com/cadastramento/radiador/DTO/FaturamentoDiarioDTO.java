package com.cadastramento.radiador.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FaturamentoDiarioDTO {
    private final LocalDate data;
    private final BigDecimal total;

    public FaturamentoDiarioDTO(LocalDate data, BigDecimal total) {
        this.data = data;

        this.total = (total != null) ? total : BigDecimal.ZERO;
    }

    public LocalDate getData() {
        return data;
    }

    public BigDecimal getTotal() {
        return total;
    }
}