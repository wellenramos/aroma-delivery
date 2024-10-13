package br.com.aroma.aroma_delivery.service;

import br.com.aroma.aroma_delivery.dto.ProdutoDto;
import br.com.aroma.aroma_delivery.dto.SituacaoProdutoEnum;
import br.com.aroma.aroma_delivery.dto.command.SalvarProdutoCommand;
import br.com.aroma.aroma_delivery.exceptions.NotFoundException;
import br.com.aroma.aroma_delivery.mapper.ProdutoMapper;
import br.com.aroma.aroma_delivery.model.Categoria;
import br.com.aroma.aroma_delivery.model.Produto;
import br.com.aroma.aroma_delivery.repository.CategoriaRepository;
import br.com.aroma.aroma_delivery.repository.ProdutoRepository;
import jakarta.validation.Valid;
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

        Categoria categoria = categoriaRepository.findById(command.getCategoriaId())
                .orElseThrow(() -> new NotFoundException("Categoria não encontrada."));

        produto.setCategoria(categoria);
        produto.setSituacao(SituacaoProdutoEnum.CADASTRADO);

        repository.save(produto);
        return mapper.toDto(produto);
    }

    public ProdutoDto alterar(@Valid SalvarProdutoCommand command) {
        Produto produto = produtoRepository.findById(command.getId())
                .orElseThrow(() -> new NotFoundException("Produto não encontrado"));

        this.salvar(command);
        return mapper.toDto(produto);
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
        List<Produto> produtos = repository.buscarPorNome(categoria.getId(), nome);
        return mapper.toDtoList(produtos);
    }
}
