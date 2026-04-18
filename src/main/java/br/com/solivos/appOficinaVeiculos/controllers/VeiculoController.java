package br.com.solivos.appOficinaVeiculos.controllers;


import br.com.solivos.appOficinaVeiculos.dtos.VeiculoRequestDTO;
import br.com.solivos.appOficinaVeiculos.dtos.VeiculoResponseDTO;

import br.com.solivos.appOficinaVeiculos.servicos.VeiculoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/veiculos")
public class VeiculoController {

    private final VeiculoService service;

    public VeiculoController(VeiculoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<VeiculoResponseDTO>> getAll() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @PostMapping
    public ResponseEntity<VeiculoResponseDTO> create(@RequestBody @Valid VeiculoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VeiculoResponseDTO> update(@PathVariable UUID id, @RequestBody @Valid VeiculoRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }
}