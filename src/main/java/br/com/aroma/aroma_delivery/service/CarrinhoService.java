package br.com.aroma.aroma_delivery.service;

import br.com.aroma.aroma_delivery.dto.ItemCarrinhoDto;
import br.com.aroma.aroma_delivery.exceptions.NotFoundException;
import br.com.aroma.aroma_delivery.mapper.ItemCarrinhoMapper;
import br.com.aroma.aroma_delivery.model.Carrinho;
import br.com.aroma.aroma_delivery.model.ItemCarrinho;
import br.com.aroma.aroma_delivery.model.Produto;
import br.com.aroma.aroma_delivery.model.Usuario;
import br.com.aroma.aroma_delivery.repository.CarrinhoRepository;
import br.com.aroma.aroma_delivery.repository.ProdutoRepository;
import br.com.aroma.aroma_delivery.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarrinhoService {

    private final UsuarioRepository usuarioRepository;
    private final CarrinhoRepository carrinhoRepository;
    private final ItemCarrinhoMapper itemCarrinhoMapper;
    private final ProdutoRepository produtoRepository;

    public void adicionarItem(ItemCarrinhoDto dto) {

        Produto produto = produtoRepository.findById(dto.getProdutoId())
                .orElseThrow(() -> new NotFoundException("Produto não encontrado"));

        if (dto.getCarrinhoId() == null) {
            criarCarrinho(dto, produto);
        } else {
            adicionarItemNoCarrinho(dto, produto);
        }
    }

    private void adicionarItemNoCarrinho(ItemCarrinhoDto dto, Produto produto) {
        Carrinho carrinho = carrinhoRepository.findById(dto.getCarrinhoId())
                .orElseThrow(() -> new NotFoundException("Carrinho não encontrado"));

        ItemCarrinho itemCarrinho = itemCarrinhoMapper.toEntity(dto);
        itemCarrinho.setProduto(produto);
        itemCarrinho.setCarrinho(carrinho);

        carrinho.getItens().add(itemCarrinho);
        carrinho.setItens(carrinho.getItens());
        carrinhoRepository.save(carrinho);
    }

    private void criarCarrinho(ItemCarrinhoDto dto, Produto produto) {
        Usuario usuario = usuarioRepository.findById(1L)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        Carrinho carrinho = new Carrinho();
        carrinho.setUsuario(usuario);

        ItemCarrinho itemCarrinho = itemCarrinhoMapper.toEntity(dto);
        itemCarrinho.setProduto(produto);
        itemCarrinho.addCarrinho(carrinho);

        carrinho.setItens(itemCarrinho.getCarrinho().getItens());

        carrinhoRepository.save(carrinho);
    }

    @Transactional
    public void removerItens(Long carrinhoId, Long itemId) {
        Carrinho carrinho = carrinhoRepository.findById(carrinhoId)
                .orElseThrow(() -> new NotFoundException("Carrinho não encontrado"));

        boolean itemRemovido = carrinho.getItens().removeIf(item -> item.getId().equals(itemId));

        if (!itemRemovido) {
            throw new NotFoundException("Item com ID " + itemId + " não está presente no carrinho.");
        }

        carrinhoRepository.save(carrinho);
    }
}
