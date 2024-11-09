package br.com.aroma.aroma_delivery.service;

import br.com.aroma.aroma_delivery.dto.AvaliacaoDto;
import br.com.aroma.aroma_delivery.dto.command.AvaliacaoPedidoCommand;
import br.com.aroma.aroma_delivery.mapper.AvaliacaoMapper;
import br.com.aroma.aroma_delivery.model.Avaliacao;
import br.com.aroma.aroma_delivery.model.Pedido;
import br.com.aroma.aroma_delivery.model.Usuario;
import br.com.aroma.aroma_delivery.repository.AvaliacaoRepository;
import br.com.aroma.aroma_delivery.repository.PedidoRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AvaliacaoService {

    private final AvaliacaoRepository repository;
    private final PedidoRepository pedidoRepository;
    private final AvaliacaoMapper mapper;
    private final SecurityService securityService;

    public AvaliacaoDto avaliar(Long pedidoId, AvaliacaoPedidoCommand command) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
            .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        Avaliacao avaliacao = mapper.toAvaliacao(command);
        avaliacao.setPedido(pedido);
        repository.save(avaliacao);

        return mapper.toDto(avaliacao);
    }

    public List<AvaliacaoDto> listar() {
        Usuario usuario = securityService.obterUsuarioAutenticado();
        List<Avaliacao> avaliacoes = repository.findByPedido_Usuario(usuario);
        return mapper.toAvaliacaoList(avaliacoes);
    }
}
