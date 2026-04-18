package br.com.solivos.appOficinaVeiculos.controllers;

import br.com.solivos.appOficinaVeiculos.dtos.OrdemServicoDetalhadaDTO;
import br.com.solivos.appOficinaVeiculos.dtos.OrdemServicoRequestDTO;
import br.com.solivos.appOficinaVeiculos.dtos.OrdemServicoResponseDTO;
import br.com.solivos.appOficinaVeiculos.enumerated.StatusOS;
import br.com.solivos.appOficinaVeiculos.servicos.OrdemServicoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/ordens-servico")
public class OrdemServicoController {

    private final OrdemServicoService service;

    public OrdemServicoController(OrdemServicoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<OrdemServicoResponseDTO> abrirOS(@RequestBody @Valid OrdemServicoRequestDTO dto) {
        return ResponseEntity.ok(service.abrirOrdem(dto));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<OrdemServicoResponseDTO> mudarStatus(
            @PathVariable UUID id,
            @RequestParam StatusOS novoStatus) {
        return ResponseEntity.ok(service.atualizarStatus(id, novoStatus));
    }

    @GetMapping("/numero/{numeroOs}")
    public ResponseEntity<OrdemServicoResponseDTO> getByNumero(@PathVariable Integer numeroOs) {
        return ResponseEntity.ok(service.buscarPorNumero(numeroOs));
    }


    @GetMapping
    public ResponseEntity<List<OrdemServicoResponseDTO>> listarComFiltros(
            @RequestParam(required = false) StatusOS status,
            @RequestParam(required = false) UUID clienteId) {
        return ResponseEntity.ok(service.listarComFiltros(status, clienteId));
    }

    @GetMapping("/{id}/detalhes")
    public ResponseEntity<OrdemServicoDetalhadaDTO> obterDetalhes(@PathVariable UUID id) {
        return ResponseEntity.ok(service.buscarDetalhesCompletos(id));
    }

}