package br.com.aroma.aroma_delivery.controller;

import br.com.aroma.aroma_delivery.dto.ItemCarrinhoDto;
import br.com.aroma.aroma_delivery.service.CarrinhoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("carrinho")
public class CarrinhoController {

    private final CarrinhoService carrinhoService;

    @PreAuthorize("hasRole('ROLE_CLIENTE')")
    @PostMapping
    public void adicionarItem(@RequestBody ItemCarrinhoDto dto) {
        carrinhoService.adicionarItem(dto);
    }

    @PreAuthorize("hasRole('ROLE_CLIENTE')")
    @DeleteMapping("/{carrinhoId}/itens/{itemId}")
    public void removerItens(@PathVariable Long carrinhoId, @PathVariable Long itemId) {
        carrinhoService.removerItens(carrinhoId, itemId);
    }
}
