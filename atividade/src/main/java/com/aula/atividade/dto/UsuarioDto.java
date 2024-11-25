package com.aula.atividade.dto;

import com.aula.atividade.model.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioDto(
        String id,
        @NotBlank String nome,
        @Email String email,
        @Size(max=11) String telefone
) {
    public UsuarioDto(Usuario usuario){
        this(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getTelefone());
    }
}
