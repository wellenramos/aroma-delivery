package br.com.aroma.aroma_delivery.controller;

import br.com.aroma.aroma_delivery.dto.ProdutoDto;
import br.com.aroma.aroma_delivery.dto.command.SalvarProdutoCommand;
import br.com.aroma.aroma_delivery.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<ProdutoDto> salvar(@RequestBody @Valid SalvarProdutoCommand command) {
        return ResponseEntity.ok(produtoService.salvar(command));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping
    public ResponseEntity<ProdutoDto> alterar(
            @RequestBody @Valid SalvarProdutoCommand command) {
        return ResponseEntity.ok(produtoService.alterar(command));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(produtoService.obterPorId(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        produtoService.deletar(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENTE')")
    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<ProdutoDto>> buscarPorCategoria(@PathVariable Long categoriaId) {
        return ResponseEntity.ok(produtoService.buscarPorCategoria(categoriaId));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENTE')")
    @GetMapping("/categoria/{categoriaId}/todos")
    public ResponseEntity<List<ProdutoDto>> buscarPorNome(@PathVariable Long categoriaId,
                                                          @RequestParam String nome) {
        return ResponseEntity.ok(produtoService.buscarPorNome(categoriaId, nome));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}/publicar")
    public ResponseEntity<ProdutoDto> publicar(@PathVariable Long id) {
        return ResponseEntity.ok(produtoService.publicar(id));
    }

}

