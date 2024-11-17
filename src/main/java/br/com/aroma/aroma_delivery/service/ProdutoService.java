package br.com.aroma.aroma_delivery.service;

import br.com.aroma.aroma_delivery.dto.ProdutoDto;
import br.com.aroma.aroma_delivery.dto.enums.SituacaoProdutoEnum;
import br.com.aroma.aroma_delivery.dto.command.SalvarProdutoCommand;
import br.com.aroma.aroma_delivery.exceptions.NotFoundException;
import br.com.aroma.aroma_delivery.mapper.ProdutoMapper;
import br.com.aroma.aroma_delivery.model.Categoria;
import br.com.aroma.aroma_delivery.model.ItemAdicional;
import br.com.aroma.aroma_delivery.model.Produto;
import br.com.aroma.aroma_delivery.repository.CategoriaRepository;
import br.com.aroma.aroma_delivery.repository.ProdutoRepository;
import jakarta.validation.Valid;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoMapper mapper;
    private final ProdutoRepository repository;
    private final CategoriaRepository categoriaRepository;
    private final ProdutoRepository produtoRepository;

    public ProdutoDto salvar(SalvarProdutoCommand command) {
        Produto produto = mapper.toEntity(command);

        produto.setCategoria(obterCategoriaPorId(command.getCategoriaId()));
        produto.setSituacao(SituacaoProdutoEnum.CADASTRADO);
        configurarItensAdicionais(command.getAdicionais(), produto);

        repository.save(produto);
        return mapper.toDto(produto);
    }

    public ProdutoDto alterar(@Valid SalvarProdutoCommand command) {
        Produto produto = buscarProdutoPorId(command.getId());
        Categoria categoria = obterCategoriaPorId(command.getCategoriaId());

        produto.setCategoria(categoria);
        atualizarItensAdicionais(command.getAdicionais(), produto);;

        repository.save(produto);
        return mapper.toDto(produto);
    }

    private Categoria obterCategoriaPorId(Long categoriaId) {
        return categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new NotFoundException("Categoria com ID " + categoriaId + " não encontrada."));
    }

    private Produto buscarProdutoPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Produto com ID " + id + " não encontrado."));
    }

    private void configurarItensAdicionais(List<Long> adicionaisIds, Produto produto) {
        if (adicionaisIds == null || adicionaisIds.isEmpty()) return;

        List<ItemAdicional> itensAdicionais = adicionaisIds.stream()
                .map((it) -> criarItemAdicional(it, produto))
                .toList();

        produto.setAdicionais(itensAdicionais);
    }

    private void atualizarItensAdicionais(List<Long> adicionaisIds, Produto produto) {
        produto.getAdicionais().removeIf(item -> !adicionaisIds.contains(item.getAdicional().getId()));

        adicionaisIds.stream()
                .filter(id -> produto.getAdicionais().stream()
                        .noneMatch(item -> item.getAdicional().getId().equals(id)))
                .map((it) -> criarItemAdicional(it, produto))
                .forEach(produto.getAdicionais()::add);
    }

    private ItemAdicional criarItemAdicional(Long adicionalId, Produto produto) {
        Produto adicionalProduto = repository.findById(adicionalId)
                .orElseThrow(() -> new NotFoundException("Produto adicional com ID " + adicionalId + " não encontrado."));

        ItemAdicional itemAdicional = new ItemAdicional();
        itemAdicional.setProduto(produto);
        itemAdicional.setAdicional(adicionalProduto);
        return itemAdicional;
    }

    public ProdutoDto obterPorId(Long id) {
        Produto produto = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Produto não encontrado."));
        return mapper.toDto(produto);
    }

    public void deletar(Long id) {
        Produto produto = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Produto não encontrado."));
        repository.delete(produto);
    }

    public List<ProdutoDto> buscarPorCategoria(Long categoriaId) {
        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new NotFoundException("Categoria não encontrada"));
        List<Produto> produtos = repository.findByCategoria(categoria);
        return mapper.toDtoList(produtos);
    }

    public List<ProdutoDto> buscarPorNome(Long categoriaId, String nome) {
        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new NotFoundException("Categoria não encontrada"));

        if (Objects.nonNull(nome) && !nome.isEmpty())
            return mapper.toDtoList(repository.buscarPorNome(categoria.getId(), nome));
        return mapper.toDtoList(repository.findByCategoria(categoria));
    }

    public ProdutoDto publicar(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Produto não encontrado"));
        produto.setSituacao(SituacaoProdutoEnum.PUBLICADO);
        repository.save(produto);
        return mapper.toDto(produto);
    }
}
