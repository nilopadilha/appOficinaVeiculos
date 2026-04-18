package br.com.solivos.appOficinaVeiculos.controllers;


import br.com.solivos.appOficinaVeiculos.dtos.PagamentoRequestDTO;
import br.com.solivos.appOficinaVeiculos.dtos.PagamentoResponseDTO;
import br.com.solivos.appOficinaVeiculos.enumerated.StatusPagamento;
import br.com.solivos.appOficinaVeiculos.servicos.PagamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/pagamentos")
public class PagamentoController {

    private final PagamentoService service;

    public PagamentoController(PagamentoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PagamentoResponseDTO> criarPagamento(@RequestBody @Valid PagamentoRequestDTO dto) {
        return ResponseEntity.ok(service.gerarPagamento(dto));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<PagamentoResponseDTO>> listarPorStatus(@PathVariable StatusPagamento status) {
        return ResponseEntity.ok(service.listarPorStatus(status));
    }

    @PatchMapping("/{id}/confirmar")
    public ResponseEntity<PagamentoResponseDTO> confirmar(@PathVariable UUID id) {
        return ResponseEntity.ok(service.confirmarPagamento(id));
    }
}