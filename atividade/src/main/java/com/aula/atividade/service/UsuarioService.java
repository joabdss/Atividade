package com.aula.atividade.service;

import com.aula.atividade.dto.UsuarioDto;
import com.aula.atividade.model.Usuario;
import com.aula.atividade.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service

public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public Page<UsuarioDto> findALL(Pageable pageable) {
        return usuarioRepository.findAll(pageable).map(UsuarioDto::new);
    }

    public UsuarioDto save(UsuarioDto usuarioDto) {
        return new UsuarioDto(usuarioRepository.save(Usuario.fromDto(usuarioDto)));
    }

    public UsuarioDto update(String id, UsuarioDto usuarioDto) {
        Usuario usuario = Usuario.fromDto(usuarioDto);
        usuario.setId(id);
        return new UsuarioDto(usuarioRepository.save(usuario));
    }
}
