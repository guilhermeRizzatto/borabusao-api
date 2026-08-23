package com.rizzatto.borabusao_api.service;

import com.rizzatto.borabusao_api.dto.SptransItinerarios.DetalhesLinhaItinerarios;
import com.rizzatto.borabusao_api.dto.SptransItinerarios.LinhasItinerarios;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class ItinerariosService {

    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    private final Map<String, LinhasItinerarios> linhas = new HashMap<>();

    public ItinerariosService(
            @Qualifier("itinerariosRestClient") RestClient itinerariosRestClient,
            ObjectMapper objectMapper) {
        this.restClient = itinerariosRestClient;
        this.objectMapper = objectMapper;

        mapearLinhas();
    }

    public LinhasItinerarios buscarLinha(String termo) {
        return this.linhas.get(termo);
    }

    public DetalhesLinhaItinerarios buscarDetalhesLinha(String letreiro) {
        LinhasItinerarios linha = this.linhas.get(letreiro);

        if (linha == null) {
            throw new IllegalArgumentException(
                    "Linha não encontrada: " + letreiro
            );
        }

        String resposta =  restClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/BuscarDetalheLinha")
                        .build())
                .body("{\"codPlanejamento\": " + linha.CdPjOID() + "}")
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(String.class);

        return objectMapper.readValue(resposta, DetalhesLinhaItinerarios.class);
    }

    private void mapearLinhas() {
        Arrays.stream(retornarLinhas())
                .forEach(linha -> linhas.put(
                        extrairNumeroLinha(linha.letreiro()),
                        linha
                ));

    }

    private LinhasItinerarios[] retornarLinhas() {
        String resposta = restClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/RetornarLinhas")
                        .build())
                .body("")
                .retrieve()
                .body(String.class);

        try {
            return objectMapper.readValue(resposta, LinhasItinerarios[].class);
        } catch (Exception exception) {
            throw new IllegalStateException(
                    "A API de itinerários da SPTrans retornou uma resposta inválida",
                    exception
            );
        }
    }

    private String extrairNumeroLinha(String letreiro) {
        int primeiroEspaco = letreiro.indexOf(' ');

        return primeiroEspaco > 0
                ? letreiro.substring(0, primeiroEspaco).trim()
                : letreiro.trim();
    }

}
