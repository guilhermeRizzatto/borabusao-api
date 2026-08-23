package com.rizzatto.borabusao_api.dto.SptransOlhoVivo;

import java.util.List;

public record PosicaoSptrans(
        String hr,
        List<VeiculoSptrans> vs
) {
}
