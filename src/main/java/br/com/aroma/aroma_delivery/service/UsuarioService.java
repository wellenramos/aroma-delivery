package br.com.aroma.aroma_delivery.service;

import br.com.aroma.aroma_delivery.dto.UsuarioDto;
import br.com.aroma.aroma_delivery.exceptions.NotFoundException;
import br.com.aroma.aroma_delivery.mapper.UsuarioMapper;
import br.com.aroma.aroma_delivery.model.Usuario;
import br.com.aroma.aroma_delivery.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioMapper mapper;
    private final UsuarioRepository repository;


    public UsuarioDto salvar(UsuarioDto dto) {
        Usuario usuario = mapper.toEntity(dto);
        usuario.setSenha(UUID.randomUUID().toString());
        repository.save(usuario);
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
