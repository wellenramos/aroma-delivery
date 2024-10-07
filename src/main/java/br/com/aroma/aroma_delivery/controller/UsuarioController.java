package br.com.aroma.aroma_delivery.controller;

import br.com.aroma.aroma_delivery.dto.UsuarioDto;
import br.com.aroma.aroma_delivery.dto.command.SalvarUsuarioCommand;
import br.com.aroma.aroma_delivery.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioDto> criar(
            @RequestBody @Valid SalvarUsuarioCommand command) {
        return ResponseEntity.ok(usuarioService.salvar(command));
    }

    @PutMapping
    public ResponseEntity<UsuarioDto> alterar(
            @RequestBody @Valid SalvarUsuarioCommand command) {
        return ResponseEntity.ok(usuarioService.alterar(command));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.obterPorId(id));
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        usuarioService.deletar(id);
    }
}
