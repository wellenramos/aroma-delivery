package br.com.aroma.aroma_delivery.service;

import br.com.aroma.aroma_delivery.dto.ProdutoDto;
import br.com.aroma.aroma_delivery.dto.command.SalvarProdutoCommand;
import br.com.aroma.aroma_delivery.dto.enums.SituacaoProdutoEnum;
import br.com.aroma.aroma_delivery.exceptions.NotFoundException;
import br.com.aroma.aroma_delivery.mapper.ProdutoMapper;
import br.com.aroma.aroma_delivery.model.Categoria;
import br.com.aroma.aroma_delivery.model.Produto;
import br.com.aroma.aroma_delivery.repository.CategoriaRepository;
import br.com.aroma.aroma_delivery.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

        adicionaisIds.forEach((it) -> {
            Produto adicional = repository.findById(it)
                .orElseThrow(() -> new NotFoundException("Produto adicional com ID " + it + " não encontrado."));
                    produto.getAdicionais().add(adicional);
                });
    }

    private void atualizarItensAdicionais(List<Long> adicionaisIds, Produto produto) {
        produto.getAdicionais().removeIf(item -> !adicionaisIds.contains(item.getId()));

        adicionaisIds.stream()
                .filter(id -> produto.getAdicionais().stream().noneMatch(item -> item.getId().equals(id)))
                .forEach((it) -> {
                    Produto adicional = repository.findById(it)
                        .orElseThrow(() -> new NotFoundException("Produto adicional com ID " + it + " não encontrado."));
                    produto.getAdicionais().add(adicional);
                });
    }

    public ProdutoDto obterPorId(Long id) {
        Produto produto = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Produto não encontrado."));
        return mapper.toDto(produto);
    }

    @Transactional
    public void deletar(Long id) {
        Produto produto = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Produto não encontrado."));

        if (SituacaoProdutoEnum.PUBLICADO.equals(produto.getSituacao())) {
            throw new IllegalArgumentException("Não é possível excluir um produto já publicado");
        }

        if (repository.verificarAdicionaisByProdutoId(produto.getId())) {
            repository.deleteAdicionaisByProdutoId(produto.getId());
        }

        repository.delete(produto);
    }

    public List<ProdutoDto> buscarPorCategoria(Long categoriaId) {
        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new NotFoundException("Categoria não encontrada"));
        List<Produto> produtos = repository.findByCategoriaAndSituacao(categoria, SituacaoProdutoEnum.PUBLICADO);
        return mapper.toDtoList(produtos);
    }

    public List<ProdutoDto> buscarPorNomeECategoria(Long categoriaId, String nome) {
        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new NotFoundException("Categoria não encontrada"));

        if (Objects.nonNull(nome) && !nome.isEmpty())
            return mapper.toDtoList(repository.buscarPorNomeECategoria(categoria.getId(), nome, SituacaoProdutoEnum.PUBLICADO));
        return mapper.toDtoList(repository.findByCategoriaAndSituacao(categoria, SituacaoProdutoEnum.PUBLICADO));
    }

    public ProdutoDto publicar(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Produto não encontrado"));
        produto.setSituacao(SituacaoProdutoEnum.PUBLICADO);
        repository.save(produto);
        return mapper.toDto(produto);
    }

    public List<ProdutoDto> buscarTodosProdutos() {
        List<Produto> produtos = repository.findAll();
        return produtos.stream().map(it ->
            ProdutoDto.builder()
                .id(it.getId())
                .nome(it.getNome())
                .descricao(it.getDescricao())
                .preco(it.getPreco())
                .situacao(it.getSituacao())
                .build()
        ).toList();
    }

    public List<ProdutoDto> buscarTodosPorNome(String nome) {
        if (Objects.nonNull(nome) && !nome.isEmpty())
            return mapper.toDtoList(repository.buscarPorNome(nome));
        return mapper.toDtoList(repository.findAll());
    }

    public List<ProdutoDto> buscarAdicionais() {
        Categoria categoria = categoriaRepository.findById(4L)
            .orElseThrow(() -> new NotFoundException("Categoria não encontrada"));
        return mapper.toDtoList(repository.findByCategoriaAndSituacao(categoria, SituacaoProdutoEnum.PUBLICADO));
    }
}
