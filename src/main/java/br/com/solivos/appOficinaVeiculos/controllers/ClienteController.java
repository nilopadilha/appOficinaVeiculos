package br.com.solivos.appOficinaVeiculos.controllers;

import br.com.solivos.appOficinaVeiculos.dtos.ClienteRequestDTO;
import br.com.solivos.appOficinaVeiculos.dtos.ClienteResponseDTO;
import br.com.solivos.appOficinaVeiculos.servicos.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {

    private final ClienteService clientService;

    public ClienteController(ClienteService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> getAll() {
        return ResponseEntity.ok(clientService.listarTodos());
    }

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> create(@RequestBody @Valid ClienteRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.criar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> update(@PathVariable UUID id, @RequestBody @Valid ClienteRequestDTO dto) {
        return ResponseEntity.ok(clientService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        clientService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}