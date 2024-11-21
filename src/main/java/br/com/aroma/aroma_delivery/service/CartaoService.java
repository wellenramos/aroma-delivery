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
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Objects;
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

    if (command.getPrincipal() && Objects.isNull(command.getId())) {
      repository.findByUsuarioAndPrincipal(usuario, true).ifPresent((it) -> {
        throw new IllegalArgumentException("Cartão principal já cadastrado.");
      });
    }

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

  public List<CartaoDto> obterCartoes() {
    String email = securityService.getAuthenticatedUser().getUsername();
    Usuario usuario = usuarioRepository.findByLogin(email)
        .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

    List<Cartao> cartoes = repository.findByUsuario(usuario);
    return mapper.toCartaoDtoList(cartoes);
  }

  @Transactional
  public CartaoDto marcarCartaoPrincipal(Long id) {
    Usuario usuario = getUsuarioAutenticado();
    desmarcarCartaoPrincipal(usuario);
    Cartao novoCartaoPrincipal = definirNovoCartaoPrincipal(id);
    return mapper.toDto(novoCartaoPrincipal);
  }

  private Usuario getUsuarioAutenticado() {
    String email = securityService.getAuthenticatedUser().getUsername();
    return usuarioRepository.findByLogin(email)
        .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
  }

  private void desmarcarCartaoPrincipal(Usuario usuario) {
    repository.findByUsuarioAndPrincipal(usuario, true)
        .ifPresent(cartao -> {
          cartao.setPrincipal(false);
          repository.save(cartao);
        });
  }

  private Cartao definirNovoCartaoPrincipal(Long id) {
    Cartao cartao = repository.findById(id)
        .orElseThrow(() -> new NotFoundException("Cartão não encontrado"));
    cartao.setPrincipal(true);
    return repository.save(cartao);
  }

}
