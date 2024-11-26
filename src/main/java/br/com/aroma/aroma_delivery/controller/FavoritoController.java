package br.com.aroma.aroma_delivery.controller;

import br.com.aroma.aroma_delivery.dto.FavoritoDto;
import br.com.aroma.aroma_delivery.service.FavoritoService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/favoritos")
public class FavoritoController {

    private final FavoritoService service;

    @PreAuthorize("hasRole('ROLE_CLIENTE')")
    @PostMapping("/produto/{produtoId}")
    public ResponseEntity<Void> favoritar(@PathVariable Long produtoId, @RequestParam Boolean favorito) {
        service.favoritar(produtoId, favorito);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_CLIENTE')")
    @GetMapping
    public ResponseEntity<List<FavoritoDto>> obterFavoritos() {
        return ResponseEntity.ok(service.obterFavoritos());
    }

    @PreAuthorize("hasRole('ROLE_CLIENTE')")
    @GetMapping("/produto/{produtoId}")
    public ResponseEntity<FavoritoDto> obterFavorito(@PathVariable Long produtoId) {
        return ResponseEntity.ok(service.obterFavorito(produtoId));
    }
}
