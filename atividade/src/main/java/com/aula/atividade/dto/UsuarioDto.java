package com.aula.atividade.dto;

import com.aula.atividade.model.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record UsuarioDto(
        String id,
        @NotBlank String nome,
        @Email String email,
        @Size(max=11) String telefone,
        Boolean excluido,
        List<String> tarefas

) {
    public UsuarioDto(Usuario usuario){
        this(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getTelefone(),usuario.getExcluido(), usuario.getTarefas());
    }
}
