package br.com.aroma.aroma_delivery.controller;

import br.com.aroma.aroma_delivery.dto.ItemCarrinhoDto;
import br.com.aroma.aroma_delivery.service.CarrinhoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("carrinho")
public class CarrinhoController {

    private final CarrinhoService carrinhoService;

    @PostMapping
    public void adicionarItem(@RequestBody ItemCarrinhoDto dto) {
        carrinhoService.adicionarItem(dto);
    }
}
