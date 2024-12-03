package br.com.aroma.aroma_delivery.controller;

import br.com.aroma.aroma_delivery.dto.PedidoDto;
import br.com.aroma.aroma_delivery.dto.PedidoResumoDto;
import br.com.aroma.aroma_delivery.dto.command.SalvarPedidoCommand;
import br.com.aroma.aroma_delivery.service.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("pedidos")
public class PedidoController {

    private final PedidoService service;

    @PreAuthorize("hasRole('ROLE_CLIENTE')")
    @PostMapping
    public ResponseEntity<PedidoDto> salvar(@RequestBody @Valid SalvarPedidoCommand command) {
        return ResponseEntity.ok(service.salvar(command));
    }

    @PreAuthorize("hasRole('ROLE_CLIENTE')")
    @GetMapping("/acompanhar")
    public ResponseEntity<PedidoResumoDto> acompanhar() {
        return ResponseEntity.ok(service.acompanharMeusPedidos());
    }

    @PreAuthorize("hasRole('ROLE_CLIENTE')")
    @PutMapping("/{id}/avaliar")
    public ResponseEntity<Void> avaliar(@PathVariable Long id, @RequestParam Integer nota) {
        service.avaliar(id, nota);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_CLIENTE')")
    @PutMapping("/{id}/confirmar-recebimento")
    public ResponseEntity<PedidoDto> confimarRecebimento(@PathVariable Long id) {
        return ResponseEntity.ok(service.confimarRecebimento(id));
    }
}
