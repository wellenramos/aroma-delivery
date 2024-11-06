package br.com.aroma.aroma_delivery.controller;

import br.com.aroma.aroma_delivery.dto.UsuarioDto;
import br.com.aroma.aroma_delivery.dto.command.SalvarUsuarioCommand;
import br.com.aroma.aroma_delivery.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping("/salvar")
    public ResponseEntity<UsuarioDto> salvar(
            @RequestBody @Valid SalvarUsuarioCommand command) {
        return ResponseEntity.ok(usuarioService.salvar(command));
    }

    @PreAuthorize("hasRole('ROLE_CLIENTE')")
    @PutMapping
    public ResponseEntity<UsuarioDto> alterar(
            @RequestBody @Valid SalvarUsuarioCommand command) {
        return ResponseEntity.ok(usuarioService.alterar(command));
    }

    @PreAuthorize("hasRole('ROLE_CLIENTE')")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.obterPorId(id));
    }

    @PreAuthorize("hasRole('ROLE_CLIENTE')")
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        usuarioService.deletar(id);
    }

    @PreAuthorize("hasRole('ROLE_CLIENTE')")
    @GetMapping("/dados/login/{login}")
    public ResponseEntity<UsuarioDto> buscarPorId(@PathVariable String login) {
        return ResponseEntity.ok(usuarioService.findByLogin(login));
    }
}
