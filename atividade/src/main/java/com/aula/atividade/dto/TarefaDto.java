package com.aula.atividade.dto;

import com.aula.atividade.model.Tarefa;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record TarefaDto(
        String id,
        @Size(max = 50)
        String titulo,
        @Size(max = 100)
        String descricao,

        LocalDate dataCriacao,
        Boolean excluida,
        UsuarioDto usuario
) {
    public TarefaDto(Tarefa tarefa) {
        this(
                tarefa.getId(),
                tarefa.getTitulo(),
                tarefa.getDescricao(),
                tarefa.getDataCriacao(),
                tarefa.getExcluida(),
                new UsuarioDto(tarefa.getUsuario()) // Convertendo para UsuarioDto
        );
    }


}
