package br.com.aroma.aroma_delivery.controller;

import br.com.aroma.aroma_delivery.dto.PedidoAdminDetalheDto;
import br.com.aroma.aroma_delivery.dto.PedidoDto;
import br.com.aroma.aroma_delivery.dto.PedidoResumoAdminDto;
import br.com.aroma.aroma_delivery.dto.enums.StatusPedidoEnum;
import br.com.aroma.aroma_delivery.service.PedidoAdminService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("pedidos/admin")
public class PedidoAdminController {

    private final PedidoAdminService service;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<PedidoResumoAdminDto>> listarPedidosPorSituacao(@RequestParam StatusPedidoEnum status) {
        return ResponseEntity.ok(service.listarPedidosPorSituacao(status));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PedidoDto> atualizarStatus(@PathVariable Long id, @RequestParam StatusPedidoEnum status) {
        return ResponseEntity.ok(service.atualizarStatus(id, status));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<PedidoAdminDetalheDto> obterPedidoResumo(@PathVariable Long id) {
        return ResponseEntity.ok(service.obterPedidoResumo(id));
    }
}
