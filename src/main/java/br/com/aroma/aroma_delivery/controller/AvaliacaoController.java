package br.com.aroma.aroma_delivery.controller;

import br.com.aroma.aroma_delivery.dto.AvaliacaoDto;
import br.com.aroma.aroma_delivery.service.AvaliacaoService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("avaliacoes")
public class AvaliacaoController {

  private final AvaliacaoService service;

  @PreAuthorize("hasRole('ROLE_CLIENTE')")
  @GetMapping
  public ResponseEntity<List<AvaliacaoDto>> listar() {
    return ResponseEntity.ok(service.listar());
  }
}
