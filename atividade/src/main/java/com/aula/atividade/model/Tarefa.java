package com.aula.atividade.model;

import com.aula.atividade.dto.TarefaDto;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Document(collection = "tarefas")
public class Tarefa {

    @Id
    private String id;
    private String titulo;
    private String descricao;

    @Column (name = "data_criacao")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataCriacao;
    private Boolean excluida;

    @DBRef // Relacionamento com o usuário
    private Usuario usuario;


    public static Tarefa fromDto(TarefaDto tarefaDto) {
        return new Tarefa(
                null, // id será gerado pelo MongoDB
                tarefaDto.titulo(),
                tarefaDto.descricao(),
                tarefaDto.dataCriacao(),
                tarefaDto.excluida(),
                tarefaDto.usuario() != null ? Usuario.fromDto(tarefaDto.usuario()) : null
        );
    }

}
