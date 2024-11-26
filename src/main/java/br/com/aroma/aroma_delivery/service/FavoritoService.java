package br.com.aroma.aroma_delivery.service;

import br.com.aroma.aroma_delivery.dto.FavoritoDto;
import br.com.aroma.aroma_delivery.exceptions.NotFoundException;
import br.com.aroma.aroma_delivery.model.Favorito;
import br.com.aroma.aroma_delivery.model.Produto;
import br.com.aroma.aroma_delivery.model.Usuario;
import br.com.aroma.aroma_delivery.repository.FavoritoRepository;
import br.com.aroma.aroma_delivery.repository.ProdutoRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FavoritoService {

  private final FavoritoRepository repository;
  private final ProdutoRepository produtoRepository;
  private final SecurityService securityService;

  public void favoritar(Long produtoId, Boolean favorito) {
    Produto produto = produtoRepository.findById(produtoId)
        .orElseThrow(() -> new NotFoundException("Produto não encontrado"));

    Usuario usuario = securityService.obterUsuarioAutenticado();

    if (favorito) {
      salvarFavorito(produto, usuario);
    } else {
      excluirFavorito(produto, usuario);
    }
  }

  private void excluirFavorito(Produto produto, Usuario usuario) {
    repository.findByUsuarioAndProduto(usuario, produto).ifPresent(repository::delete);
  }

  private void salvarFavorito(Produto produto, Usuario usuario) {
    Favorito favorito = Favorito.builder().produto(produto).usuario(usuario).build();
    repository.save(favorito);
  }

  public List<FavoritoDto> obterFavoritos() {
    Usuario usuario = securityService.obterUsuarioAutenticado();
    List<Favorito> favoritos = repository.findByUsuario(usuario);
    return favoritos.stream().map(this::criarFavoritoDto).toList();
  }

  private FavoritoDto criarFavoritoDto(Favorito favorito) {
    return FavoritoDto.builder()
        .id(favorito.getId())
        .nome(favorito.getProduto().getNome())
        .descricao(favorito.getProduto().getDescricao())
        .build();
  }

  public FavoritoDto obterFavorito(Long produtoId) {
    Produto produto = produtoRepository.findById(produtoId)
        .orElseThrow(() -> new NotFoundException("Produto não encontrado"));

    Usuario usuario = securityService.obterUsuarioAutenticado();
    Optional<Favorito> favorito = repository.findByUsuarioAndProduto(usuario, produto);

    return favorito.map(this::criarFavoritoDto).orElse(null);
  }
}
