package com.aula.atividade.dto;

import com.aula.atividade.model.Usuario;

public record UsuarioDto(
        String id,
        String nome,
        String email,
        String telefone
) {
    public UsuarioDto(Usuario usuario){
        this(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getTelefone());
    }
}
