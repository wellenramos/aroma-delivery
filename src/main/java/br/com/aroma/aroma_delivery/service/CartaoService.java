package br.com.aroma.aroma_delivery.service;

import br.com.aroma.aroma_delivery.dto.CartaoDto;
import br.com.aroma.aroma_delivery.dto.command.SalvarCartaoCommand;
import br.com.aroma.aroma_delivery.exceptions.NotFoundException;
import br.com.aroma.aroma_delivery.mapper.CartaoMapper;
import br.com.aroma.aroma_delivery.model.Cartao;
import br.com.aroma.aroma_delivery.model.Usuario;
import br.com.aroma.aroma_delivery.repository.CartaoRepository;
import br.com.aroma.aroma_delivery.repository.UsuarioRepository;
import br.com.aroma.aroma_delivery.service.strategy.BandeiraCartaoContext;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartaoService {

  private final CartaoRepository repository;
  private final SecurityService securityService;
  private final UsuarioRepository usuarioRepository;
  private final CartaoMapper mapper;

  public CartaoDto salvar(SalvarCartaoCommand command) {
    Cartao cartao = mapper.toEntity(command);

    String email = securityService.getAuthenticatedUser().getUsername();
    Usuario usuario = usuarioRepository.findByLogin(email)
        .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

    cartao.setUsuario(usuario);
    cartao.setBandeira(new BandeiraCartaoContext().identificarBandeira(command.getNumero()));

    repository.save(cartao);
    return mapper.toDto(cartao);
  }

  public void deletar(Long id) {
    Cartao cartao = repository.findById(id)
        .orElseThrow(() -> new NotFoundException("Cartão não encontrado."));
    repository.delete(cartao);
  }

  public CartaoDto findByUsuarioAndPrincipal(Usuario usuario, boolean principal) {
    Optional<Cartao> cartao = repository.findByUsuarioAndPrincipal(usuario, principal);
    return cartao.map(mapper::toDto).orElse(null);
  }
}
