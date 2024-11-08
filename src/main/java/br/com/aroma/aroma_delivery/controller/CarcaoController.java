package br.com.aroma.aroma_delivery.controller;

import br.com.aroma.aroma_delivery.dto.CartaoDto;
import br.com.aroma.aroma_delivery.dto.EnderecoDto;
import br.com.aroma.aroma_delivery.dto.command.SalvarCartaoCommand;
import br.com.aroma.aroma_delivery.dto.command.SalvarEnderecoCommand;
import br.com.aroma.aroma_delivery.service.CartaoService;
import br.com.aroma.aroma_delivery.service.EnderecoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cartoes")
public class CarcaoController {

    private final CartaoService service;

    @PreAuthorize("hasRole('ROLE_CLIENTE')")
    @PostMapping
    public ResponseEntity<CartaoDto> salvar(
            @RequestBody @Valid SalvarCartaoCommand command) {
        return ResponseEntity.ok(service.salvar(command));
    }

    @PreAuthorize("hasRole('ROLE_CLIENTE')")
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}
