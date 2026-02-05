package com.seplag.senior.service;

import com.seplag.senior.model.Regional;
import com.seplag.senior.repository.RegionalRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SyncService {

    private final RegionalRepository repository;
    private final RestTemplate restTemplate = new RestTemplate();
    private final String API_URL = "https://integrador-argus-api.geia.vip/v1/regionais";

    // Executa a sincronizacao automaticamente (exemplo: a cada 1 hora)
    @Scheduled(fixedRate = 3600000)
    @Transactional
    public void syncRegionais() {
        // 1. Procura dados na API externa
        RegionalDTO[] externos = restTemplate.getForObject(API_URL, RegionalDTO[].class);
        if (externos == null) return;

        // Transforma a lista externa num Mapa para busca rapida O(1)
        Map<Integer, RegionalDTO> mapExterno = Arrays.stream(externos)
            .collect(Collectors.toMap(RegionalDTO::getId, Function.identity()));

        // 2. Procura dados no banco local
        List<Regional> locais = repository.findAll();

        // 3. Logica de Sincronizacao Eficiente
        for (Regional local : locais) {
            if (!mapExterno.containsKey(local.getId())) {
                // Se nao existe mais na API externa, desativamos localmente
                local.setAtivo(false);
                repository.save(local);
            } else {
                RegionalDTO ext = mapExterno.get(local.getId());
                // Se o nome mudou, atualizamos conforme a regra de negocio
                if (!ext.getNome().equals(local.getNome())) {
                    local.setNome(ext.getNome());
                    repository.save(local);
                }
                // Removemos do mapa para sobrarem apenas os novos no final
                mapExterno.remove(local.getId());
            }
        }

        // 4. Insere os novos registos que nao existiam no banco local
        for (RegionalDTO novoExt : mapExterno.values()) {
            Regional novo = new Regional();
            novo.setId(novoExt.getId());
            novo.setNome(novoExt.getNome());
            novo.setAtivo(true);
            repository.save(novo);
        }
    }
}

// Classe auxiliar para receber os dados da API
@Data
class RegionalDTO {
    private Integer id;
    private String nome;
}