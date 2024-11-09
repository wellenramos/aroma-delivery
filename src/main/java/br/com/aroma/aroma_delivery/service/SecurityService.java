package br.com.aroma.aroma_delivery.service;

import br.com.aroma.aroma_delivery.exceptions.NotFoundException;
import br.com.aroma.aroma_delivery.model.Usuario;
import br.com.aroma.aroma_delivery.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityService {

    private final UsuarioRepository usuarioRepository;

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof User) {
                return (User) principal;
            }
        }

       throw new NotFoundException("Usuario nao encontrado");
    }

    public Usuario obterUsuarioAutenticado() {
        String email = getAuthenticatedUser().getUsername();
        return usuarioRepository.findByLogin(email)
            .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
    }
}
