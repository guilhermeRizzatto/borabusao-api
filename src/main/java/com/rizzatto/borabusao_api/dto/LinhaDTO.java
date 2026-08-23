package com.rizzatto.borabusao_api.dto;

import com.rizzatto.borabusao_api.dto.SptransOlhoVivo.LinhaSptrans;

public record LinhaDTO(
        Integer id,
        Boolean circular,
        String letreiro,
        Integer sentido,
        Integer letreiroSecundario,
        String terminalPrincipal,
        String terminalSecundario,
        String area
) {
    public LinhaDTO(LinhaSptrans linhaSptrans, String area) {
        this(linhaSptrans.cl(), linhaSptrans.lc(), linhaSptrans.lt(), linhaSptrans.sl(), linhaSptrans.tl(), linhaSptrans.tp(), linhaSptrans.ts(), area);
    }
}
