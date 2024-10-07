package br.com.aroma.aroma_delivery.service;

import br.com.aroma.aroma_delivery.dto.PerfilEnum;
import br.com.aroma.aroma_delivery.dto.UsuarioDto;
import br.com.aroma.aroma_delivery.dto.command.SalvarUsuarioCommand;
import br.com.aroma.aroma_delivery.exceptions.NotFoundException;
import br.com.aroma.aroma_delivery.mapper.UsuarioMapper;
import br.com.aroma.aroma_delivery.model.Perfil;
import br.com.aroma.aroma_delivery.model.Usuario;
import br.com.aroma.aroma_delivery.repository.PerfilRepository;
import br.com.aroma.aroma_delivery.repository.UsuarioRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioMapper mapper;
    private final UsuarioRepository repository;
    private final PerfilRepository perfilRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;


    public UsuarioDto salvar(SalvarUsuarioCommand command) {
        Usuario usuario = mapper.toEntity(command);

        Perfil perfil = perfilRepository.findById(PerfilEnum.CLIENTE.getCodigo())
                .orElseThrow(() -> new NotFoundException("Perfil não encontrado."));

        usuario.setPerfil(perfil);
        usuario.setSenha(passwordEncoder.encode(command.getSenha()));

        repository.save(usuario);
        return mapper.toDto(usuario);
    }

    public UsuarioDto alterar(@Valid SalvarUsuarioCommand command) {
        Usuario usuario = usuarioRepository.findById(command.getId())
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        this.salvar(command);
        return mapper.toDto(usuario);
    }

    public UsuarioDto obterPorId(Long id) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado."));
        return mapper.toDto(usuario);
    }

    public void deletar(Long id) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado."));
        repository.delete(usuario);
    }
}
