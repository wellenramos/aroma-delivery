package br.com.aroma.aroma_delivery.controller;

import br.com.aroma.aroma_delivery.dto.CarrinhoDto;
import br.com.aroma.aroma_delivery.dto.command.SalvarItemCarrinhoCommand;
import br.com.aroma.aroma_delivery.service.CarrinhoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("carrinho")
public class CarrinhoController {

    private final CarrinhoService carrinhoService;

    @PreAuthorize("hasRole('ROLE_CLIENTE')")
    @PostMapping
    public ResponseEntity<CarrinhoDto> adicionarItem(@RequestBody @Valid SalvarItemCarrinhoCommand command) {
        return ResponseEntity.ok(carrinhoService.adicionarItem(command));
    }

    @PreAuthorize("hasRole('ROLE_CLIENTE')")
    @PutMapping
    public ResponseEntity<CarrinhoDto> alterarItem(@RequestBody @Valid SalvarItemCarrinhoCommand command) {
        return ResponseEntity.ok(carrinhoService.alterarItem(command));
    }

    @PreAuthorize("hasRole('ROLE_CLIENTE')")
    @DeleteMapping("/{carrinhoId}/itens/{itemId}")
    public void removerItens(@PathVariable Long carrinhoId, @PathVariable Long itemId) {
        carrinhoService.removerItens(carrinhoId, itemId);
    }
}
