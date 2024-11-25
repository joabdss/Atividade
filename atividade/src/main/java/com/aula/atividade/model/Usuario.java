package com.aula.atividade.model;

import com.aula.atividade.dto.UsuarioDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "usuarios")
public class Usuario {
    @Id
    private String id;
    private String nome;
    private String email;
    private String telefone;
    private Boolean excluido = false;

    @DBRef // Referências para as tarefas (relacionamento 1:N)
    @JsonIgnore
    private List<Tarefa> tarefas;


    public static Usuario fromDto(UsuarioDto usuarioDto){
        return new Usuario(null, usuarioDto.nome(), usuarioDto.email(), usuarioDto.telefone(), usuarioDto.excluido(), usuarioDto.tarefas());
    }
}
