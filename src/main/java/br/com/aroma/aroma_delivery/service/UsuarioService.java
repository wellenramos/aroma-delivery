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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UsuarioService implements UserDetailsService {

    private final UsuarioMapper mapper;
    private final UsuarioRepository repository;
    private final PerfilRepository perfilRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;


    public UsuarioDto salvar(SalvarUsuarioCommand command) {
        Usuario usuario = mapper.toEntity(command);

        validarLogin(command);

        Perfil perfil = perfilRepository.findById(PerfilEnum.CLIENTE.getCodigo())
                .orElseThrow(() -> new NotFoundException("Perfil não encontrado."));

        usuario.setPerfil(perfil);
        usuario.setSenha(passwordEncoder.encode(command.getSenha()));

        repository.save(usuario);
        return mapper.toDto(usuario);
    }

    private void validarLogin(SalvarUsuarioCommand command) {
        if (command.getId() == null) {
            usuarioRepository.findByLogin(command.getLogin())
                    .ifPresent((it) -> {
                        throw new IllegalStateException("Login %s já existente.".formatted(it.getLogin()));
                    } );
        }
    }

    public UsuarioDto alterar(@Valid SalvarUsuarioCommand command) {
        Usuario usuario = usuarioRepository.findById(command.getId())
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        if (!usuario.getLogin().equals(command.getLogin())) {
            throw new IllegalArgumentException("Não é possível alterar nome do login.");
        }

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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario user = usuarioRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        return new org.springframework.security.core.userdetails.User(user.getLogin(),
                user.getSenha(), getAuthorities(user));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Usuario user) {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getPerfil().getNome().toUpperCase()));
    }

    public UsuarioDto findByLogin(String login) {
        Usuario usuario = repository.findByLogin(login)
                .orElseThrow(() -> new NotFoundException("Usuário inexistente."));
        return mapper.toDto(usuario);
    }
}
