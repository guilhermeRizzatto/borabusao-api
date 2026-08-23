package com.rizzatto.borabusao_api.controller;

import com.rizzatto.borabusao_api.dto.LinhaDTO;
import com.rizzatto.borabusao_api.dto.SptransItinerarios.DetalhesLinhaItinerarios;
import com.rizzatto.borabusao_api.dto.SptransOlhoVivo.LinhaSptrans;
import com.rizzatto.borabusao_api.dto.SptransOlhoVivo.PosicaoSptrans;
import com.rizzatto.borabusao_api.service.ItinerariosService;
import com.rizzatto.borabusao_api.service.SptransService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sptrans")
public class SptransController {

    private final SptransService sptransService;

    public SptransController(SptransService sptransService) {
        this.sptransService = sptransService;
    }

    @GetMapping("/linhas")
    public List<LinhaDTO> buscarLinhas(@RequestParam String termo) {
        return sptransService.buscarLinhas(termo);
    }

    @GetMapping("/detalhes-linha")
    public DetalhesLinhaItinerarios buscarDetalhesLinha(@RequestParam String letreiro) {
        return sptransService.buscarDetalhesLinha(letreiro);
    }

    @GetMapping("/linhas/{codigoLinha}/posicoes")
    public PosicaoSptrans buscarPosicoes(
            @PathVariable Integer codigoLinha) {
        return sptransService.buscarPosicoes(codigoLinha);
    }

}
