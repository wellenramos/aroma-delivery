package br.com.aroma.aroma_delivery.controller;

import br.com.aroma.aroma_delivery.dto.AcompanharDto;
import br.com.aroma.aroma_delivery.dto.AvaliacaoDto;
import br.com.aroma.aroma_delivery.dto.PedidoDto;
import br.com.aroma.aroma_delivery.dto.command.AvaliacaoPedidoCommand;
import br.com.aroma.aroma_delivery.dto.command.SalvarPedidoCommand;
import br.com.aroma.aroma_delivery.service.PedidoService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<List<AcompanharDto>> acompanhar() {
        return ResponseEntity.ok(service.acompanhar());
    }

    @PreAuthorize("hasRole('ROLE_CLIENTE')")
    @GetMapping("/historico")
    public ResponseEntity<List<PedidoDto>> historico() {
        return ResponseEntity.ok(service.historico());
    }

    @PostMapping("/{id}/avaliar")
    public ResponseEntity<AvaliacaoDto> avaliar(@PathVariable Long id,
        @RequestBody @Valid AvaliacaoPedidoCommand command) {
        return ResponseEntity.ok(service.avaliar(id, command));
    }
}
