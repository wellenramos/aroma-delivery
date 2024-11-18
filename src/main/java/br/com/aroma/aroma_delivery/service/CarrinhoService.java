package br.com.aroma.aroma_delivery.service;

import br.com.aroma.aroma_delivery.dto.CarrinhoDto;
import br.com.aroma.aroma_delivery.dto.CarrinhoResumoDto;
import br.com.aroma.aroma_delivery.dto.CarrinhoResumoDto.ItemCarrinhoResumoDto;
import br.com.aroma.aroma_delivery.dto.command.SalvarItemCarrinhoCommand;
import br.com.aroma.aroma_delivery.dto.enums.SituacaoProdutoEnum;
import br.com.aroma.aroma_delivery.exceptions.NotFoundException;
import br.com.aroma.aroma_delivery.mapper.CarrinhoMapper;
import br.com.aroma.aroma_delivery.mapper.ItemCarrinhoMapper;
import br.com.aroma.aroma_delivery.model.Carrinho;
import br.com.aroma.aroma_delivery.model.ItemCarrinho;
import br.com.aroma.aroma_delivery.model.Produto;
import br.com.aroma.aroma_delivery.model.Usuario;
import br.com.aroma.aroma_delivery.repository.CarrinhoRepository;
import br.com.aroma.aroma_delivery.repository.ProdutoRepository;
import br.com.aroma.aroma_delivery.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarrinhoService {

    private final UsuarioRepository usuarioRepository;
    private final CarrinhoRepository carrinhoRepository;
    private final ItemCarrinhoMapper itemCarrinhoMapper;
    private final CarrinhoMapper carrinhoMapper;
    private final ProdutoRepository produtoRepository;
    private final SecurityService securityService;

    public CarrinhoDto adicionarItem(SalvarItemCarrinhoCommand command) {
        Produto produto = produtoRepository.findById(command.getProdutoId())
                .orElseThrow(() -> new NotFoundException("Produto não encontrado"));

        if (!produto.getSituacao().equals(SituacaoProdutoEnum.PUBLICADO)) {
            throw new IllegalStateException("Este produto ainda não foi publicado.");
        }

        if (command.getCarrinhoId() == null)
            return criarCarrinho(command, produto);
        return adicionarItemNoCarrinho(command, produto);
    }

    private CarrinhoDto adicionarItemNoCarrinho(SalvarItemCarrinhoCommand command, Produto produto) {
        Carrinho carrinho = carrinhoRepository.findById(command.getCarrinhoId())
                .orElseThrow(() -> new NotFoundException("Carrinho não encontrado"));

        ItemCarrinho itemCarrinho = itemCarrinhoMapper.toEntity(command);
        itemCarrinho.setProduto(produto);
        itemCarrinho.setCarrinho(carrinho);

        carrinho.getItens().add(itemCarrinho);
        carrinho.setItens(carrinho.getItens());
        atualizarItensAdicionais(command.getAdicionais(), itemCarrinho);

        Carrinho saved = carrinhoRepository.save(carrinho);
        return carrinhoMapper.toDto(saved);
    }

    private CarrinhoDto criarCarrinho(SalvarItemCarrinhoCommand command, Produto produto) {
        String email = securityService.getAuthenticatedUser().getUsername();
        Usuario usuario = usuarioRepository.findByLogin(email)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        Carrinho carrinho = new Carrinho();
        carrinho.setUsuario(usuario);

        ItemCarrinho itemCarrinho = itemCarrinhoMapper.toEntity(command);
        itemCarrinho.setProduto(produto);
        itemCarrinho.addCarrinho(carrinho);

        configurarItensAdicionais(command.getAdicionais(), itemCarrinho);
        carrinho.setItens(itemCarrinho.getCarrinho().getItens());

        Carrinho saved = carrinhoRepository.save(carrinho);
        return carrinhoMapper.toDto(saved);
    }

    private void configurarItensAdicionais(List<Long> adicionaisIds, ItemCarrinho itemCarrinho) {
        if (adicionaisIds == null || adicionaisIds.isEmpty()) return;

        adicionaisIds.forEach((it) -> {
            Produto adicional = produtoRepository.findById(it)
                .orElseThrow(() -> new NotFoundException("Produto adicional com ID " + it + " não encontrado."));
            itemCarrinho.getAdicionais().add(adicional);
        });
    }

    private void atualizarItensAdicionais(List<Long> adicionaisIds, ItemCarrinho itemCarrinho) {
        itemCarrinho.getAdicionais().removeIf(item -> !adicionaisIds.contains(item.getId()));

        adicionaisIds.stream()
            .filter(id -> itemCarrinho.getAdicionais().stream().noneMatch(item -> item.getId().equals(id)))
            .forEach((it) -> {
                Produto adicional = produtoRepository.findById(it)
                    .orElseThrow(() -> new NotFoundException("Produto adicional com ID " + it + " não encontrado."));
                itemCarrinho.getAdicionais().add(adicional);
            });
    }

    public CarrinhoDto alterarItem(@Valid SalvarItemCarrinhoCommand command) {
        Carrinho carrinho = carrinhoRepository.findById(command.getCarrinhoId())
                .orElseThrow(() -> new NotFoundException("Carrinho não encontrado"));

        carrinho.getItens().stream()
                .filter(it -> it.getId().equals(command.getId()))
                .findFirst().orElseThrow(() -> new NotFoundException("Item não encontrado"));

        return this.adicionarItem(command);
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

    public void atualizarQuantidadeItens(Long carrinhoId, Long itemId, Integer quantidade) {
        Carrinho carrinho = carrinhoRepository.findById(carrinhoId)
                .orElseThrow(() -> new NotFoundException("Carrinho não encontrado"));

        carrinho.getItens().stream()
            .filter(it -> it.getId().equals(itemId))
            .forEach(item -> item.setQuantidade(quantidade));

        carrinhoRepository.save(carrinho);
    }

    public CarrinhoResumoDto resumo(Long carrinhoId) {
        Carrinho carrinho = carrinhoRepository.findById(carrinhoId)
            .orElseThrow(() -> new NotFoundException("Carrinho não encontrado"));

        List<ItemCarrinhoResumoDto> itens = carrinho.getItens().stream()
            .map(it -> ItemCarrinhoResumoDto.builder()
                .id(it.getId())
                .nomeProduto(it.getProduto().getNome())
                .descricaoProduto(it.getProduto().getDescricao())
                .quantidade(it.getQuantidade())
                .valorTotal(it.calcularValorTotal())
                .build()).toList();

        return CarrinhoResumoDto.builder()
            .itens(itens)
            .build();
    }
}
