package br.com.aroma.aroma_delivery.service;

import br.com.aroma.aroma_delivery.dto.EnderecoBaseDto;
import br.com.aroma.aroma_delivery.dto.EnderecoDto;
import br.com.aroma.aroma_delivery.dto.ViaCepDto;
import br.com.aroma.aroma_delivery.dto.command.SalvarEnderecoCommand;
import br.com.aroma.aroma_delivery.exceptions.NotFoundException;
import br.com.aroma.aroma_delivery.feign.ViaCepClient;
import br.com.aroma.aroma_delivery.mapper.EnderecoMapper;
import br.com.aroma.aroma_delivery.model.Endereco;
import br.com.aroma.aroma_delivery.model.EnderecoBase;
import br.com.aroma.aroma_delivery.model.Usuario;
import br.com.aroma.aroma_delivery.repository.EnderecoBaseRepository;
import br.com.aroma.aroma_delivery.repository.EnderecoRepository;
import br.com.aroma.aroma_delivery.repository.UsuarioRepository;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnderecoService {

    private final EnderecoMapper mapper;
    private final EnderecoRepository repository;
    private final EnderecoBaseRepository enderecoBaseRepository;
    private final SecurityService securityService;
    private final UsuarioRepository usuarioRepository;
    private final ViaCepClient viaCepClient;

    public EnderecoDto salvar(SalvarEnderecoCommand command) {
        Endereco endereco = mapper.toEntity(command);

        String email = securityService.getAuthenticatedUser().getUsername();
        Usuario usuario = usuarioRepository.findByLogin(email)
            .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        if (command.getPrincipal() && Objects.isNull(command.getId())) {
            repository.findByUsuarioAndPrincipal(usuario, true).ifPresent((it) -> {
                throw new IllegalArgumentException("Endereço principal já cadastrado.");
            });
        }

        endereco.setUsuario(usuario);

        repository.save(endereco);
        return mapper.toDto(endereco);
    }

    public EnderecoDto alterar(@Valid SalvarEnderecoCommand command) {
        repository.findById(command.getId())
                .orElseThrow(() -> new NotFoundException("Endereço não encontrado"));

        return salvar(command);
    }

    public EnderecoDto obterPorId(Long id) {
        Endereco endereco = repository.findById(id)
            .orElseThrow(() -> new NotFoundException("Endereço não encontrado"));
        return mapper.toDto(endereco);
    }

    public void deletar(Long id) {
        Endereco endereco = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Endereço não encontrado."));
        if (endereco.getPrincipal()) {
            throw new IllegalArgumentException("Não é possível excluir o endereço principal");
        }
        repository.delete(endereco);
    }

    public EnderecoBaseDto consultarPorCep(String cep) {
        return repository.findByCep(cep)
            .map(mapper::toEnderecoDto)
            .orElseGet(() -> {
                ViaCepDto viaCepDto = viaCepClient.findAddressByZipCode(cep)
                    .orElseThrow(() -> new NotFoundException("CEP não encontrado."));
                EnderecoBase enderecoBase = salvarEnderecoBase(viaCepDto);
                return mapper.toEnderecoBaseDto(enderecoBase);
            });
    }

    private EnderecoBase salvarEnderecoBase(ViaCepDto viaCep) {
        EnderecoBase endereco = EnderecoBase.builder()
            .cep(viaCep.getCep())
            .cidade(viaCep.getLocalidade())
            .bairro(viaCep.getBairro())
            .estado(viaCep.getEstado())
            .uf(viaCep.getUf())
            .build();
        return enderecoBaseRepository.save(endereco);
    }

    public EnderecoDto obterEnderecoUsuario() {
        String email = securityService.getAuthenticatedUser().getUsername();
        Usuario usuario = usuarioRepository.findByLogin(email)
            .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        Optional<Endereco> endereco = repository.findByUsuarioAndPrincipal(usuario,true);
        return endereco.map(mapper::toDto).orElse(null);
    }


    public List<EnderecoDto> obterEnderecos() {
        String email = securityService.getAuthenticatedUser().getUsername();
        Usuario usuario = usuarioRepository.findByLogin(email)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        List<Endereco> enderecos = repository.findByUsuario(usuario);
        return mapper.toEnderecosDtoList(enderecos);
    }

    public EnderecoDto marcarEnderecoPrincipal(Long id) {
        String email = securityService.getAuthenticatedUser().getUsername();
        Usuario usuario = usuarioRepository.findByLogin(email)
            .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        Endereco endereco = repository.findByUsuarioAndPrincipal(usuario,true)
            .orElseThrow(() -> new NotFoundException("Endereço não encontrado"));
        endereco.setPrincipal(false);
        repository.save(endereco);

        Endereco novoEnderecoPrincipal = repository.findById(id)
            .orElseThrow(() -> new NotFoundException("Endereço não encontrado"));
        novoEnderecoPrincipal.setPrincipal(true);
        repository.save(novoEnderecoPrincipal);

        return mapper.toDto(novoEnderecoPrincipal);
    }
}
