package br.com.aroma.aroma_delivery.controller;

import br.com.aroma.aroma_delivery.dto.EnderecoBaseDto;
import br.com.aroma.aroma_delivery.dto.EnderecoDto;
import br.com.aroma.aroma_delivery.dto.command.SalvarEnderecoCommand;
import br.com.aroma.aroma_delivery.service.EnderecoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/enderecos")
public class EnderecoController {

    private final EnderecoService service;

    @PreAuthorize("hasRole('ROLE_CLIENTE')")
    @PostMapping
    public ResponseEntity<EnderecoDto> salvar(
            @RequestBody @Valid SalvarEnderecoCommand command) {
        return ResponseEntity.ok(service.salvar(command));
    }

    @PreAuthorize("hasRole('ROLE_CLIENTE')")
    @PutMapping
    public ResponseEntity<EnderecoDto> alterar(
            @RequestBody @Valid SalvarEnderecoCommand command) {
        return ResponseEntity.ok(service.alterar(command));
    }

    @PreAuthorize("hasRole('ROLE_CLIENTE')")
    @GetMapping("/{id}")
    public ResponseEntity<EnderecoDto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obterPorId(id));
    }

    @PreAuthorize("hasRole('ROLE_CLIENTE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_CLIENTE')")
    @GetMapping("/cep/{cep}")
    public ResponseEntity<EnderecoBaseDto> consultarPorCep(@PathVariable String cep) {
        return ResponseEntity.ok(service.consultarPorCep(cep));
    }

    @PreAuthorize("hasRole('ROLE_CLIENTE')")
    @GetMapping
    public ResponseEntity<List<EnderecoDto>> obterEnderecos() {
        return ResponseEntity.ok(service.obterEnderecos());
    }

    @PreAuthorize("hasRole('ROLE_CLIENTE')")
    @PutMapping("/{id}/principal")
    public ResponseEntity<EnderecoDto> marcarEnderecoPrincipal(@PathVariable Long id) {
        return ResponseEntity.ok(service.marcarEnderecoPrincipal(id));
    }
}
