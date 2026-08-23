package com.rizzatto.borabusao_api.service;

import com.rizzatto.borabusao_api.dto.LinhaDTO;
import com.rizzatto.borabusao_api.dto.SptransItinerarios.DetalhesLinhaItinerarios;
import com.rizzatto.borabusao_api.dto.SptransOlhoVivo.LinhaSptrans;
import com.rizzatto.borabusao_api.dto.SptransOlhoVivo.PosicaoSptrans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;

@Service
public class SptransService {

    @Autowired
    private ItinerariosService itinerariosService;

    private final RestClient restClient;
    private final String token;
    private boolean autenticado;

    public SptransService(
            RestClient sptransRestClient,
            @Value("${sptrans.api.token}") String token) {
        this.restClient = sptransRestClient;
        this.token = token;
    }

    private synchronized void autenticar() {
        if (autenticado) {
            return;
        }

        String sucesso = restClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/Login/Autenticar")
                        .queryParam("token", token)
                        .build())
                .body(" ")
                .retrieve()
                .body(String.class);

        if (!Boolean.parseBoolean(sucesso)) {
            throw new IllegalStateException(
                    "Não foi possível autenticar na API da SPTrans"
            );
        }

        autenticado = true;
    }

    public List<LinhaDTO> buscarLinhas(String termo) {
        autenticar();

        LinhaSptrans[] linhas = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/Linha/Buscar")
                        .queryParam("termosBusca", termo)
                        .build())
                .retrieve()
                .body(LinhaSptrans[].class);

        if(linhas != null && linhas.length > 0) {
            return Arrays.stream(linhas).map(linha ->
                    new LinhaDTO(linha, this.itinerariosService.buscarLinha(linha.lt() + "-" + linha.tl()).AreCodVig())
            ).toList();
        }

        return null;
    }

    public DetalhesLinhaItinerarios buscarDetalhesLinha(String letreiro) {
        return this.itinerariosService.buscarDetalhesLinha(letreiro);
    }

    public PosicaoSptrans buscarPosicoes(Integer codigoLinha) {
        autenticar();

        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/Posicao/Linha")
                        .queryParam("codigoLinha", codigoLinha)
                        .build())
                .retrieve()
                .body(PosicaoSptrans.class);
    }
}
