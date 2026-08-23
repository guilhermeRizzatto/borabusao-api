package com.rizzatto.borabusao_api.dto.SptransItinerarios;

import java.util.List;
import java.util.Map;

public record DetalhesLinhaItinerarios(
        String codigoLinha,
        String codigo,
        String nomeLinha,
        Integer area,
        String consorcio,
        String empresa,
        String letreiroIda,
        String letreiroVolta,
        List<HorarioOperacao> horarios,
        List<Partida> partidasIda,
        List<Partida> partidasVolta,
        List<TempoEstimado> temposEstimados,
        List<ViaItinerario> itinerariosIda,
        List<ViaItinerario> itinerariosVolta
) {

    public record HorarioOperacao(
            Integer tipoDia,
            String horarioPontoInicial,
            String horarioPontoFinal
    ) {
    }

    public record Partida(
            Integer tipoDia,
            Integer faixaHoraria,
            Integer totalPartidas,
            List<HorarioProgramado> horariosProgramados
    ) {
    }

    public record HorarioProgramado(
            String horario,
            Boolean veiculoAcessivel
    ) {
    }

    public record TempoEstimado(
            Integer tipoDia,
            Integer inicialManha,
            Integer inicialEntrePico,
            Integer inicialTarde,
            Integer finalManha,
            Integer finalEntrePico,
            Integer finalTarde
    ) {
    }

    public record ViaItinerario(
            Integer tipoDia,
            String nomeVia,
            String numVia,
            List<Evento> eventos
    ) {
    }

    public record Evento(
            Integer id,
            String evento,
            String descricao,
            String tipoEvento,
            String icone,
            Integer periodicidade,
            Integer diaSemana,
            String horarioInicio,
            String horarioFinal,
            String itinerario
    ) {
    }
}
